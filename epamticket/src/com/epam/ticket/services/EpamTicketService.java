package com.epam.ticket.services;

import com.epam.ticket.facades.EpamTicketSearchCriteria;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.util.List;

public interface EpamTicketService {

    void addTicket(CsTicketModel ticket, CsCustomerEventModel creationEvent);

    List<CsTicketModel> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);

    CsTicketModel getTicketById(String ticketId);

    Integer getTotalTicketCount();
}
