package com.epam.ticket.services.impl;

import com.epam.ticket.services.EpamTicketBusinessService;
import com.google.common.base.Preconditions;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;
import de.hybris.platform.ticket.service.impl.DefaultTicketBusinessService;
import de.hybris.platform.ticket.service.impl.DefaultTicketService;

import static com.google.common.base.Strings.isNullOrEmpty;

public class DefaultEpamTicketBusinessService implements EpamTicketBusinessService {

    private DefaultTicketBusinessService defaultTicketBusinessService;
    private DefaultTicketService defaultTicketService;

    public DefaultEpamTicketBusinessService(DefaultTicketBusinessService defaultTicketBusinessService,
                                            DefaultTicketService defaultTicketService) {
        this.defaultTicketBusinessService = defaultTicketBusinessService;
        this.defaultTicketService = defaultTicketService;
    }

    @Override
    public void addTicket(CsTicketModel ticket, CsCustomerEventModel creationEvent) {
        defaultTicketBusinessService.createTicket(ticket.getCustomer(), ticket.getCategory(), ticket.getPriority(),
                ticket.getAssignedAgent(), ticket.getAssignedGroup(), ticket.getHeadline(),
                creationEvent.getInterventionType(), creationEvent.getReason(), creationEvent.getText());
    }

    @Override
    public CsTicketModel setTicketState(final String ticketId, final String newState, final String comment) throws TicketException {
        Preconditions.checkArgument(!isNullOrEmpty(ticketId), "TicketId cannot be empty");
        CsTicketModel ticket = defaultTicketService.getTicketForTicketId(ticketId);
        ticket = defaultTicketBusinessService.setTicketState(ticket, CsTicketState.valueOf(newState), comment);
        return ticket;
    }

}
