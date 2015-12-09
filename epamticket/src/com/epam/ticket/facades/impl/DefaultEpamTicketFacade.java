package com.epam.ticket.facades.impl;

import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.EpamTicketFacade;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import com.epam.ticket.services.EpamTicketBusinessService;
import com.epam.ticket.services.EpamTicketService;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class DefaultEpamTicketFacade implements EpamTicketFacade {

    public static final Logger LOG = Logger.getLogger(DefaultEpamTicketFacade.class);

    private EpamTicketConverter ticketConverter;
    private EpamTicketService ticketService;
    private EpamTicketBusinessService ticketBusinessService;

    public DefaultEpamTicketFacade(EpamTicketConverter ticketConverter, EpamTicketService ticketService,
                                   EpamTicketBusinessService ticketBusinessService) {
        this.ticketConverter = checkNotNull(ticketConverter);
        this.ticketService = checkNotNull(ticketService);
        this.ticketBusinessService = checkNotNull(ticketBusinessService);
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

    @Override
    public EpamTicket changeTicketState(String ticketId, String newState, String comment) throws TicketException {
        LOG.info(String.format("Change TicketState with : ticketId=%s, newState=%s.", ticketId, newState));
        return ticketConverter.convert(ticketBusinessService.setTicketState(ticketId, newState, comment));
    }

    private List<EpamTicket> getEpamTickets(List<CsTicketModel> csTicketModels) {
        List<EpamTicket> tickets = new ArrayList<>();
        (csTicketModels).forEach(csTicketModel -> tickets.add(ticketConverter.convert(csTicketModel)));
        return tickets;
    }

}
