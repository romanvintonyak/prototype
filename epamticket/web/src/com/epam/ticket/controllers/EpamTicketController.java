package com.epam.ticket.controllers;

import com.epam.ticket.data.EpamCustomerEvent;
import com.epam.ticket.data.EpamNewTicket;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/v1/tickets")
public class EpamTicketController {

    @Autowired
    private DefaultEpamTicketFacade defaultEpamTicketFacade;

    @RequestMapping(method = GET)
    @ResponseBody
    public Collection<EpamTicket> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        return defaultEpamTicketFacade.getTicketsByCriteria(searchCriteria);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public EpamTicket addTicket(@RequestBody EpamNewTicket ticket) {
        return defaultEpamTicketFacade.addTicket(ticket.getNewTicket(), ticket.getCreationEvent());
    }

    @RequestMapping(value = "/{ticketId}", method = GET)
    @ResponseBody
    public EpamTicket getTicket(@PathVariable("ticketId") String ticketId) {
        return defaultEpamTicketFacade.getTicketById(ticketId);
    }

    @RequestMapping(value = "/ticketCount", method = GET)
    @ResponseBody
    public TicketCounterHolder getTicketCount() {
        TicketCounterHolder ticketCounterHolder = new TicketCounterHolder();
        ticketCounterHolder.setTotal(defaultEpamTicketFacade.getTotalTicketCount());
        return ticketCounterHolder;
    }

    private class TicketCounterHolder  implements Serializable{
        private Integer total;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }
    }
}
