package com.epam.ticket.services;

import com.epam.ticket.data.EpamTicket;
import de.hybris.platform.ticket.service.TicketException;

public interface EpamTicketBusinessService {

    EpamTicket setTicketState(String ticketId, String state, String comment) throws TicketException;
}
