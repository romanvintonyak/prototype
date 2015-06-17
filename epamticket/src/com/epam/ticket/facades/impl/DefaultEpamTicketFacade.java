package com.epam.ticket.facades.impl;

import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.EpamTicketFacade;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor_Peretiatko on 6/12/2015.
 */
public class DefaultEpamTicketFacade implements EpamTicketFacade {

    private EpamTicketConverter ticketConverter;
    private EpamTicketDAO ticketDao;
//    private DefaultAgentDao agentDao;

    @Override
    public List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        List<CsTicketModel> csTicketModels = ticketDao.findTicketsByCriteria(searchCriteria);

        return getEpamTickets(csTicketModels);
    }


    public void setTicketConverter(EpamTicketConverter ticketConverter) {
        this.ticketConverter = ticketConverter;
    }

    private List<EpamTicket> getEpamTickets(List<CsTicketModel> csTicketModels) {
        List<EpamTicket> tickets = new ArrayList<>();
        (csTicketModels).forEach(csTicketModel -> tickets.add(ticketConverter.getTicketbyModel(csTicketModel)));
        return tickets;
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }
}
