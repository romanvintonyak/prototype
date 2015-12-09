package com.epam.ticket.facades;

import com.epam.ticket.data.EpamTicket;
import de.hybris.platform.ticket.service.TicketException;

import java.util.List;

/**
 * Created by Viktor_Peretiatko on 6/12/2015.
 */
public interface EpamTicketFacade {
    List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria);
    EpamTicket getTicketById(String ticketId);

    Integer getTotalTicketCount();
    EpamTicket changeTicketState(String ticketId, String newState, String comment) throws TicketException;
}
