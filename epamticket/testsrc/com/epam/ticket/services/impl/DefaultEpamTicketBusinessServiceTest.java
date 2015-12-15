package com.epam.ticket.services.impl;

import com.epam.ticket.services.EpamTicketBusinessService;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;
import de.hybris.platform.ticket.service.impl.DefaultTicketBusinessService;
import de.hybris.platform.ticket.service.impl.DefaultTicketService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.rules.ExpectedException.none;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultEpamTicketBusinessServiceTest {

    @Rule
    public ExpectedException thrown = none();

    private EpamTicketBusinessService epamTicketBusinessService;

    @Mock
    private DefaultTicketBusinessService defaultTicketBusinessService;
    @Mock
    private DefaultTicketService defaultTicketService;

    private static final String TICKET_ID = "ticketId";
    private static final String CLOSED = "CLOSED";
    private static final String COMMENT = "comment";

    @Before
    public void init() {
        epamTicketBusinessService = new DefaultEpamTicketBusinessService(defaultTicketBusinessService, defaultTicketService);
    }

    //TODO: check mandatory parameters for ticket creation and write fail-test
    @Test
    public void shouldAddTicket() {
        //given
        CsTicketModel ticket = new CsTicketModel();
        CsCustomerEventModel creationEvent = new CsCustomerEventModel();

        doReturn(ticket).when(defaultTicketBusinessService).createTicket(ticket.getCustomer(), ticket.getCategory(),
                ticket.getPriority(), ticket.getAssignedAgent(), ticket.getAssignedGroup(), ticket.getHeadline(),
                creationEvent.getInterventionType(), creationEvent.getReason(), creationEvent.getText());
        //when
        epamTicketBusinessService.addTicket(ticket, creationEvent);
        //then
        verify(defaultTicketBusinessService, times(1)).createTicket(ticket.getCustomer(), ticket.getCategory(),
                ticket.getPriority(), ticket.getAssignedAgent(), ticket.getAssignedGroup(), ticket.getHeadline(),
                creationEvent.getInterventionType(), creationEvent.getReason(), creationEvent.getText());
    }

    @Test
    public void shouldCloseTicketWhenMethodInvoke() throws TicketException {
        //given
        CsTicketModel csTicket = new CsTicketModel();
        when(defaultTicketService.getTicketForTicketId(TICKET_ID)).thenReturn(csTicket);
        when(defaultTicketBusinessService.setTicketState(any(), any(), eq(COMMENT))).thenReturn(csTicket);
        //when
        epamTicketBusinessService.setTicketState(TICKET_ID, CLOSED, COMMENT);
        //then
        verify(defaultTicketBusinessService).setTicketState(csTicket, CsTicketState.CLOSED, COMMENT);
    }

    @Test
    public void shouldThrowExceptionWhenTiketIdIsEmpty() throws TicketException {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("TicketId cannot be empty");
        //when
        epamTicketBusinessService.setTicketState(null, CLOSED, COMMENT);
    }

    @Test
    public void shouldThrowExceptionWhenTicketNotFound() throws TicketException {
        //given
        thrown.expect(TicketException.class);
        thrown.expectMessage("Can not find ticket with id = ticketId");
        CsTicketModel csTicket = new CsTicketModel();
        when(defaultTicketService.getTicketForTicketId(TICKET_ID)).thenReturn(null);
        //when
        epamTicketBusinessService.setTicketState(TICKET_ID, CLOSED, COMMENT);
        //then

    }


}