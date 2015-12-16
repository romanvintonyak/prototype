package com.epam.ticket.controllers;

import com.epam.dto.EpamFilteredTicketsCounts;
import com.epam.dto.EpamTicket;
import com.epam.dto.EpamTicketSearchCriteria;
import com.epam.dto.TicketCounterHolder;
import com.epam.ticket.data.EpamNewTicket;
import com.epam.ticket.data.EpamTicketStateHolder;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import de.hybris.platform.ticket.service.TicketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public Collection<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        return defaultEpamTicketFacade.getTicketsByCriteria(searchCriteria);
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

    @RequestMapping(value = "/filteredTicketsCounts", method = RequestMethod.GET)
    public EpamFilteredTicketsCounts getFilteredTicketsCounts() {
        return defaultEpamTicketFacade.getFilteredTicketsCounts();
    }

    @RequestMapping(value = "/{ticketId}/changestate", method = RequestMethod.PUT)
    public EpamTicket changeTicketState(@PathVariable("ticketId") String ticketId, @RequestBody EpamTicketStateHolder stateHolder) {
        LOG.info(String.format("Invoke the changestate with ticketId=%s.", ticketId));
        EpamTicket ticket;
        try {
            ticket = defaultEpamTicketFacade.changeTicketState(ticketId,
                    stateHolder.getNewState(), stateHolder.getComment());
        } catch (TicketException e) {
            LOG.error("Ticket change state exception:" + e.getMessage());
            throw new TicketNotFoundException("Ticket change state exception:" + e.getMessage());
        }
        return ticket;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cannot change ticket state")
    public class TicketNotFoundException extends RuntimeException {
        //TODO replace Global controller error handling. Use @ControllerAdvice approach
        public TicketNotFoundException(String message) {

        }
    }

}
