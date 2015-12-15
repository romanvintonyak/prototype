package com.epam.ticket.services.impl;

import com.epam.dto.EpamTicket;
import com.epam.ticket.converter.EpamTicketConverter;
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
import static org.mockito.Mockito.*;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultEpamTicketBusinessServiceTest {

    @Rule
    public ExpectedException thrown = none();

    private EpamTicketBusinessService epamTicketBusinessService;

    @Mock
    private DefaultTicketBusinessService defaultTicketBusinessService;
    @Mock
    private EpamTicketConverter ticketConverter;
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
    public void shouldCloseTicket() throws TicketException {
        //given
        CsTicketModel csTicket = new CsTicketModel();
        EpamTicket epamTicket = new EpamTicket();
        when(ticketConverter.convert(csTicket)).thenReturn(epamTicket);
        when(defaultTicketService.getTicketForTicketId(TICKET_ID)).thenReturn(csTicket);
        when(defaultTicketBusinessService.setTicketState(any(), any(), eq(COMMENT))).thenReturn(csTicket);
        //when
        CsTicketModel resultTicket = epamTicketBusinessService.setTicketState(TICKET_ID, CLOSED, COMMENT);
        //then
        verify(defaultTicketBusinessService).setTicketState(csTicket, CsTicketState.CLOSED, COMMENT);
    }

    @Test
    public void shouldThrowException() throws TicketException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("TicketId cannot be empty");
        //when
        epamTicketBusinessService.setTicketState(null, CLOSED, COMMENT);
        //then

    }

}