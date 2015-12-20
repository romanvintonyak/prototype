package com.epam.ticket.services;

import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamTicketSearchCriteria;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.util.List;
import java.util.Map;

public interface EpamTicketService {

    List<CsTicketModel> getTicketsByCriteria(/*EpamTicketSearchCriteria searchCriteria*/ Map<String,String[]> searchCriteria);

    CsTicketModel getTicketById(String ticketId);

    Integer getTotalTicketCount();
    
    EpamFrontConfig getFrontConfigWithCounters();
    
}
