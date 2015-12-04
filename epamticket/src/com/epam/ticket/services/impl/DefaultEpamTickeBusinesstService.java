package com.epam.ticket.services.impl;

import com.epam.ticket.services.EpamTicketBusinessService;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;
import de.hybris.platform.ticket.service.impl.DefaultTicketBusinessService;

public class DefaultEpamTickeBusinesstService implements EpamTicketBusinessService {

    private DefaultTicketBusinessService defaultTicketBusinessService;

    public DefaultEpamTickeBusinesstService(DefaultTicketBusinessService defaultTicketBusinessService) {
        this.defaultTicketBusinessService = defaultTicketBusinessService;
    }

    @Override
    public CsTicketModel setTicketState(CsTicketModel ticket, CsTicketState newState, String comment) throws TicketException {
        return defaultTicketBusinessService.setTicketState(ticket,newState,comment);
    }

}
