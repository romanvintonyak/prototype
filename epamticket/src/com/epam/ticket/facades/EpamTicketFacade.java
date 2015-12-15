package com.epam.ticket.facades;

import com.epam.dto.EpamTicket;
import com.epam.ticket.data.EpamCustomerEvent;
import com.epam.ticket.dao.EpamTicketDAO.TicketCountsResult;

import java.util.List;

/**
 * Created by Viktor_Peretiatko on 6/12/2015.
 */
public interface EpamTicketFacade {

    EpamTicket addTicket(EpamTicket ticket, EpamCustomerEvent event);

    List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);

    EpamTicket getTicketById(String ticketId);

    Integer getTotalTicketCount();
    
    TicketCountsResult getTicketCounts(String userName);

}
