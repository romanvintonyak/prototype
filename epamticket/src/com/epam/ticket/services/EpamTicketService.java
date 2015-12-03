package com.epam.ticket.services;

import java.util.List;

import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.facades.EpamTicketSearchCriteria;

import de.hybris.platform.ticket.model.CsTicketModel;

public interface EpamTicketService {

    List<CsTicketModel> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);

    CsTicketModel getTicketById(String ticketId);

    Integer getTotalTicketCount();
    
    EpamTicketDAO.TicketCountsResult getTicketCounts();
}
