package com.epam.ticket.facades;

import com.epam.ticket.converter.CsCustomerEventConverter;
import com.epam.ticket.converter.CsTicketConverter;
import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.data.EpamCustomerEvent;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import com.epam.ticket.services.EpamTicketBusinessService;
import com.epam.ticket.services.EpamTicketService;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultEpamTicketFacadeTest {

    private static final String TICKET_ID = "ticketId";
    private static final String CLOSED = "Closed";
    private static final String COMMENT = "comment";
    private DefaultEpamTicketFacade defaultEpamTicketFacade;

    @Mock
    private EpamTicketConverter mockTicketConverter;

    @Mock
    private CsTicketConverter mockCsTicketConverter;

    @Mock
    private EpamTicketService mockTicketService;

    @Mock
    private EpamTicketBusinessService mockTicketBusinessService;

    @Mock
    private CsCustomerEventConverter mockCsCustomerEventConverter;

    @Mock
    private SessionService mockSessionService;

    @Mock
    private UserService mockUserService;

    @Rule
    public ExpectedException thrown = none();

    @Before
    public void setUp() {
        defaultEpamTicketFacade = new DefaultEpamTicketFacade(mockTicketConverter, mockCsTicketConverter,
                mockCsCustomerEventConverter, mockTicketService, mockTicketBusinessService, mockSessionService, mockUserService);
    }

    @Test
    public void shouldAddTicket() {
        //given
        EpamTicket dummyTicketDto = new EpamTicket();
        EpamCustomerEvent dummyEventDto = new EpamCustomerEvent();
        CsTicketModel dummyTicket = new CsTicketModel();
        CsCustomerEventModel dummyEvent = new CsCustomerEventModel();

        doNothing().when(mockTicketBusinessService).addTicket(dummyTicket, dummyEvent);
        doReturn(dummyTicket).when(mockCsTicketConverter).convert(dummyTicketDto);
        doReturn(dummyEvent).when(mockCsCustomerEventConverter).convert(dummyEventDto);
        //when
        defaultEpamTicketFacade.addTicket(dummyTicketDto, dummyEventDto);
        //then
        verify(mockTicketBusinessService, times(1)).addTicket(dummyTicket, dummyEvent);
        verify(mockCsTicketConverter, times(1)).convert(dummyTicketDto);
        verify(mockCsCustomerEventConverter, times(1)).convert(dummyEventDto);
    }

    @Test
    public void shouldCloseTicketWhenItPossible() throws TicketException {
        CsTicketModel csTicket = new CsTicketModel();
        when(mockTicketBusinessService.setTicketState(TICKET_ID, CLOSED, COMMENT)).thenReturn(csTicket);
        //when
        defaultEpamTicketFacade.changeTicketState(TICKET_ID, CLOSED, COMMENT);
        //then
        verify(mockTicketBusinessService, times(1)).setTicketState(TICKET_ID, CLOSED, COMMENT);
        verify(mockTicketConverter, times(1)).convert(csTicket);

    }

    @Test
    public void shouldThrowExceptionWhenTicketIdIsEmpty() throws TicketException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("TicketId cannot be empty");
        //when
        defaultEpamTicketFacade.changeTicketState(null, CLOSED, COMMENT);
    }

}
