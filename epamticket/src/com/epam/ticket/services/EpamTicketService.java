package com.epam.ticket.services;

import java.util.List;

import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.facades.EpamTicketSearchCriteria;

import de.hybris.platform.ticket.model.CsTicketModel;

/**
 * @author Dmitry Adonin
 * @since 27-Nov-15
 */
public interface EpamTicketService {

    List<CsTicketModel> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);

    CsTicketModel getTicketById(String ticketId);

    Integer getTotalTicketCount();
    
    EpamTicketDAO.TicketCountsResult getTicketCounts();
}
