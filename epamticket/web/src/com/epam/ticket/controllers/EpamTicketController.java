package com.epam.ticket.controllers;

import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.data.EpamTicketStateHolder;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import de.hybris.platform.ticket.service.TicketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Collection;

@Controller
@RequestMapping("/v1/tickets")
public class EpamTicketController {

    private static final Logger LOG = LoggerFactory.getLogger(EpamTicketController.class);

    @Autowired
    private DefaultEpamTicketFacade defaultEpamTicketFacade;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Collection<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        return defaultEpamTicketFacade.getTicketsByCriteria(searchCriteria);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    @ResponseBody
    public EpamTicket getTicket(@PathVariable("ticketId") String ticketId) {
        return defaultEpamTicketFacade.getTicketById(ticketId);
    }

    @RequestMapping(value = "/ticketCount", method = RequestMethod.GET)
    @ResponseBody
    public TicketCounterHolder getTicketCount() {
        TicketCounterHolder ticketCounterHolder = new TicketCounterHolder();
        ticketCounterHolder.setTotal(defaultEpamTicketFacade.getTotalTicketCount());
        return ticketCounterHolder;
    }

    @RequestMapping(value = "/{ticketId}/changestate", method = RequestMethod.PUT)
    @ResponseBody
    public EpamTicket changeTicketState(@PathVariable("ticketId") String ticketId, @RequestBody EpamTicketStateHolder stateHolder) {
        LOG.info(String.format("Invoke the changestate with ticketId=%s.", ticketId));
        EpamTicket ticket = new EpamTicket();
        try {
            ticket = defaultEpamTicketFacade.changeTicketState(ticketId,
                    stateHolder.getNewState(), stateHolder.getComment());
        } catch (TicketException e) {
            throw new TicketNotFoundException("Ticket change state exception:" + e.getMessage());
        }
        return ticket;
    }

    private class TicketCounterHolder implements Serializable {
        private Integer total;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ticket not Found by Id")  // 404
    public class TicketNotFoundException extends RuntimeException {
        public TicketNotFoundException(String id) {
            super("Ticket not found with id =" + id);
        }

    }

}
