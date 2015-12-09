package com.epam.ticket.services.impl;

import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.services.EpamTicketBusinessService;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.ticket.enums.CsTicketState;
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
    private EpamTicketConverter ticketConverter;
    @Mock
    private DefaultTicketService defaultTicketService;

    private static final String TICKET_ID = "ticketId";
    private static final String CLOSED = "CLOSED";
    private static final String COMMENT = "comment";

    @Before
    public void init() {
        epamTicketBusinessService = new DefaultEpamTicketBusinessService(defaultTicketBusinessService, defaultTicketService, ticketConverter);
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
        EpamTicket resultTicket = epamTicketBusinessService.setTicketState(TICKET_ID, CLOSED, COMMENT);
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