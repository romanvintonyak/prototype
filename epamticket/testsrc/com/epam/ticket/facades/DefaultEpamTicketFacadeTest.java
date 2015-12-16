package com.epam.ticket.facades;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.dto.EpamTicket;
import com.epam.ticket.converter.CsCustomerEventConverter;
import com.epam.ticket.converter.CsTicketConverter;
import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.data.EpamCustomerEvent;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import com.epam.ticket.services.EpamTicketBusinessService;
import com.epam.ticket.services.EpamTicketService;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultEpamTicketFacadeTest {

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

    @Before
    public void setUp() {
        defaultEpamTicketFacade = new DefaultEpamTicketFacade(mockTicketConverter, mockCsTicketConverter,
                mockCsCustomerEventConverter, mockTicketService, mockTicketBusinessService);
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
}
