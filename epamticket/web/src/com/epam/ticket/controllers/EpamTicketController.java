package com.epam.ticket.controllers;

import com.epam.dto.*;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import de.hybris.platform.ticket.service.TicketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/tickets")
public class EpamTicketController {

    private static final Logger LOG = LoggerFactory.getLogger(EpamTicketController.class);

    @Autowired
    private DefaultEpamTicketFacade defaultEpamTicketFacade;

    @RequestMapping(method = GET)
    public Collection<EpamTicket> getTicketsByCriteria(final HttpServletRequest request) {
        return defaultEpamTicketFacade.getTicketsByCriteria(request.getParameterMap());
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public EpamTicket addTicket(@RequestBody EpamNewTicket ticket) {
        return defaultEpamTicketFacade.addTicket(ticket.getNewTicket(), ticket.getCreationEvent());
    }

    @RequestMapping(value = "/{ticketId}", method = GET)
    public EpamTicket getTicket(@PathVariable("ticketId") String ticketId) {
        return defaultEpamTicketFacade.getTicketById(ticketId);
    }

    @RequestMapping(value = "/ticketCount", method = GET)
    public TicketCounterHolder getTicketCount() {
        TicketCounterHolder ticketCounterHolder = new TicketCounterHolder();
        ticketCounterHolder.setTotal(defaultEpamTicketFacade.getTotalTicketCount());
        return ticketCounterHolder;
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public EpamFrontConfig getConfig() {
        return defaultEpamTicketFacade.getFrontConfigWithCounters();
    }

    @RequestMapping(value = "/{ticketId}/changestate", method = RequestMethod.PUT)
    public EpamTicket changeTicketState(@PathVariable("ticketId") String ticketId, @RequestBody EpamTicketStateHolder stateHolder) {
        LOG.info(String.format("Invoke the changestate with ticketId=%s.", ticketId));
        EpamTicket ticket;
        try {
            ticket = defaultEpamTicketFacade.changeTicketState(ticketId, stateHolder.getNewState(), stateHolder.getComment());
        } catch (TicketException e) {
            LOG.error("Ticket change state exception:" + e.getMessage());
            throw new TicketNotFoundException(e, "Ticket change state exception:");
        }
        return ticket;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cannot change ticket state")
    public class TicketNotFoundException extends RuntimeException {
        //TODO replace Global controller error handling. Use @ControllerAdvice approach
        public TicketNotFoundException(Throwable exception, String message) {
            LOG.debug("sonar", exception);
            LOG.debug("sonar", message);
        }
    }
}
