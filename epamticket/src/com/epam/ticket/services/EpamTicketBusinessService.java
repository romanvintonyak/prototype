package com.epam.ticket.services;

import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;

public interface EpamTicketBusinessService {

    CsTicketModel setTicketState(CsTicketModel var1, CsTicketState var2, String var3) throws TicketException;
}
