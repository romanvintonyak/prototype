package com.epam.ticket.facades;

import com.epam.dto.EpamTicket;
import com.epam.dto.EpamTicketSearchCriteria;
import com.epam.dto.EpamFilteredTicketsCounts;
import com.epam.ticket.data.EpamCustomerEvent;
import com.epam.ticket.dao.EpamTicketDAO.TicketCountsResult;
import com.epam.ticket.data.EpamCustomerEvent;
import de.hybris.platform.ticket.service.TicketException;

import java.util.List;

public interface EpamTicketFacade {

    EpamTicket addTicket(EpamTicket ticket, EpamCustomerEvent event);

    List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);

    EpamTicket getTicketById(String ticketId);

    Integer getTotalTicketCount();

    EpamTicket changeTicketState(String ticketId, String newState, String comment) throws TicketException;
    
    EpamFilteredTicketsCounts getFilteredTicketsCounts();

}
