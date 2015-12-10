package com.epam.ticket.facades.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.dao.EpamTicketDAO.TicketCountsResult;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.EpamTicketFacade;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import com.epam.ticket.services.EpamTicketService;

import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.ticket.model.CsTicketModel;

public class DefaultEpamTicketFacade implements EpamTicketFacade {

    public static final Logger LOG = Logger.getLogger(DefaultEpamTicketFacade.class);
    
    private EpamTicketConverter ticketConverter;
    private EpamTicketService ticketService;
    private SessionService sessionService;
    private UserService userService;


    public DefaultEpamTicketFacade(EpamTicketConverter ticketConverter, EpamTicketService ticketService, SessionService sessionService, UserService userService) {
        this.ticketConverter = checkNotNull(ticketConverter);
        this.ticketService = checkNotNull(ticketService);
        this.sessionService = checkNotNull(sessionService);
        this.userService = checkNotNull(userService);
    }

    @Override
    public List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        LOG.info("Search by criteria: " + searchCriteria);
        List<CsTicketModel> csTicketModels = ticketService.getTicketsByCriteria(searchCriteria);
        return getEpamTickets(csTicketModels);
    }

    @Override
    public EpamTicket getTicketById(String ticketId) {
        LOG.info("Get ticket by id: " + ticketId);
        CsTicketModel csTicketModel = ticketService.getTicketById(ticketId);
        return ticketConverter.convert(csTicketModel);
    }

    @Override
    public Integer getTotalTicketCount() {
        LOG.info("Get ticket count");
        return ticketService.getTotalTicketCount();
    }

    private List<EpamTicket> getEpamTickets(List<CsTicketModel> csTicketModels) {
        List<EpamTicket> tickets = new ArrayList<>();
        (csTicketModels).forEach(csTicketModel -> tickets.add(ticketConverter.convert(csTicketModel)));
        return tickets;
    }

    @Override
    public TicketCountsResult getTicketCounts(String userName) {
        // TODO: GET RID of userName, when security will be ready!
        userService.setCurrentUser(userService.getUserForUID(userName));
        return ticketService.getTicketCounts();
    }

}
