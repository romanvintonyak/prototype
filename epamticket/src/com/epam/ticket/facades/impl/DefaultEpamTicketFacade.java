package com.epam.ticket.facades.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.dto.EpamCustomerEvent;
import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamTicket;
import com.epam.dto.EpamTicketSearchCriteria;
import com.epam.ticket.converter.CsCustomerEventConverter;
import com.epam.ticket.converter.CsTicketConverter;
import com.epam.ticket.converter.EpamTicketConverter;
import com.epam.ticket.facades.EpamTicketFacade;
import com.epam.ticket.services.EpamTicketBusinessService;
import com.epam.ticket.services.EpamTicketService;
import com.google.common.base.Preconditions;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static de.hybris.platform.ticket.enums.CsTicketState.OPEN;

public class DefaultEpamTicketFacade implements EpamTicketFacade {

    public static final Logger LOG = Logger.getLogger(DefaultEpamTicketFacade.class);

    private EpamTicketConverter ticketConverter;
    private CsTicketConverter csTicketConverter;
    private EpamTicketService ticketService;
    private EpamTicketBusinessService ticketBusinessService;
    private CsCustomerEventConverter csCustomerEventConverter;

    public DefaultEpamTicketFacade(EpamTicketConverter ticketConverter, CsTicketConverter csTicketConverter,
                                   CsCustomerEventConverter csCustomerEventConverter, EpamTicketService ticketService,
                                   EpamTicketBusinessService ticketBusinessService) {
        this.ticketConverter = checkNotNull(ticketConverter);
        this.csTicketConverter = checkNotNull(csTicketConverter);
        this.csCustomerEventConverter = checkNotNull(csCustomerEventConverter);
        this.ticketService = checkNotNull(ticketService);
        this.ticketBusinessService = checkNotNull(ticketBusinessService);
    }

    @Override
    public EpamTicket addTicket(final EpamTicket ticket, final EpamCustomerEvent event) {
        ticket.setState(OPEN.getCode());
        return ticketConverter.convert(
                ticketBusinessService.addTicket(csTicketConverter.convert(ticket), csCustomerEventConverter.convert(event)));
    }

    @Override
    public List<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        LOG.info("Search by criteria: " + searchCriteria);
        List<CsTicketModel> csTicketModels = ticketService.getTicketsByCriteria(searchCriteria);
        return getEpamTickets(csTicketModels);
    }

    @Override
    public EpamTicket getTicketById(final String ticketId) {
        LOG.info("Get ticket by id: " + ticketId);
        CsTicketModel csTicketModel = ticketService.getTicketById(ticketId);
        return ticketConverter.convert(csTicketModel);
    }

    @Override
    public Integer getTotalTicketCount() {
        return ticketService.getTotalTicketCount();
    }

    @Override
    public EpamTicket changeTicketState(final String ticketId, final String newState,final String comment) throws TicketException {
        LOG.info(String.format("Change TicketState with : ticketId=%s, newState=%s.", ticketId, newState));
        Preconditions.checkArgument(!isNullOrEmpty(ticketId), "TicketId cannot be empty");
        return ticketConverter.convert(ticketBusinessService.setTicketState(ticketId, newState, comment));
    }

    private List<EpamTicket> getEpamTickets(List<CsTicketModel> csTicketModels) {
        List<EpamTicket> tickets = new ArrayList<>();
        (csTicketModels).forEach(csTicketModel -> tickets.add(ticketConverter.convert(csTicketModel)));
        return tickets;
    }

    @Override
    public EpamFrontConfig getFrontConfigWithCounters() {
        return ticketService.getFrontConfigWithCounters();
    }

}
