package com.epam.ticket.facades.impl;

import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.EpamTicketFacade;
import com.epam.ticket.facades.EpamTicketSearchCriteria;

import de.hybris.platform.ticket.model.CsTicketModel;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Viktor_Peretiatko on 6/12/2015.
 */
public class DefaultEpamTicketFacade implements EpamTicketFacade {
    public static final Logger LOG = Logger.getLogger(DefaultEpamTicketFacade.class);
    private EpamTicketConverter ticketConverter;
    private EpamTicketDAO ticketDao;
    //    private DefaultAgentDao agentDao;

    public DefaultEpamTicketFacade(EpamTicketConverter ticketConverter, EpamTicketDAO ticketDao) {
        this.ticketConverter = checkNotNull(ticketConverter);
        this.ticketDao = checkNotNull(ticketDao);
    }

    @Override
    public List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        LOG.info("Search by criteria: " + searchCriteria);
        List<CsTicketModel> csTicketModels = ticketDao.findTicketsByCriteria(searchCriteria);
        return getEpamTickets(csTicketModels);
    }
    @Override
    public EpamTicket getTicketById(String ticketId){
    	LOG.info("Get ticket by id: " + ticketId);
    	CsTicketModel csTicketModel = ticketDao.getTicketById(ticketId);
    	return ticketConverter.convert(csTicketModel);
    }
    
    private List<EpamTicket> getEpamTickets(List<CsTicketModel> csTicketModels) {
        List<EpamTicket> tickets = new ArrayList<>();
        (csTicketModels).forEach(csTicketModel -> tickets.add(ticketConverter.convert(csTicketModel)));
        return tickets;
    }

}
