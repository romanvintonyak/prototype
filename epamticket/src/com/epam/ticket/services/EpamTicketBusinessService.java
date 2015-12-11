package com.epam.ticket.services;

import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;

public interface EpamTicketBusinessService {

    CsTicketModel addTicket(CsTicketModel ticket, CsCustomerEventModel creationEvent);

    CsTicketModel setTicketState(String ticketId, String state, String comment) throws TicketException;
}
