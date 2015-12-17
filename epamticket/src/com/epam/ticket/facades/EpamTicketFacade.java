package com.epam.ticket.facades;

import com.epam.dto.EpamCustomerEvent;
import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamTicket;
import com.epam.dto.EpamTicketSearchCriteria;
import de.hybris.platform.ticket.service.TicketException;

import java.util.List;

public interface EpamTicketFacade {

    EpamTicket addTicket(EpamTicket ticket, EpamCustomerEvent event);

    List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);

    EpamTicket getTicketById(String ticketId);

    Integer getTotalTicketCount();

    EpamTicket changeTicketState(String ticketId, String newState, String comment) throws TicketException;
    
    EpamFrontConfig getFrontConfigWithCounters();
}
