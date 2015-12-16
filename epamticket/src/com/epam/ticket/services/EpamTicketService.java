package com.epam.ticket.services;

import com.epam.dto.EpamFilteredTicketsCounts;
import com.epam.dto.EpamTicketSearchCriteria;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.util.List;

public interface EpamTicketService {

    List<CsTicketModel> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);

    CsTicketModel getTicketById(String ticketId);

    Integer getTotalTicketCount();
    
    EpamFilteredTicketsCounts getFilteredTicketsCounts();
}
