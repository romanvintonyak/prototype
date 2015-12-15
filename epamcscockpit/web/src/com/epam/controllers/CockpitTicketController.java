package com.epam.controllers;

import com.epam.dto.EpamTicket;
import com.epam.dto.TicketCounterHolder;
import com.epam.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/rest/tickets")
@RestController
public class CockpitTicketController {

    private static final String ALL_TICKETS = "http://localhost:9001/epamticket/v1/tickets";
    private static final String TICKET_BY_ID = "http://localhost:9001/epamticket/v1/tickets/%s";
    private static final String TICKET_COUNT = "http://localhost:9001/epamticket/v1/tickets/ticketCount";

    @Autowired
    private RestHelper restHelper;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<EpamTicket> getAll() {
        return restHelper.call(ALL_TICKETS, List.class);
        // TODO: need to cover base context url
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    @ResponseBody
    public EpamTicket getById(@PathVariable("ticketId") final String ticketId) {
        return restHelper.call(String.format(TICKET_BY_ID, ticketId), EpamTicket.class);
        // TODO: need to cover base context url

    }
    @RequestMapping(value = "/ticketCount", method = RequestMethod.GET)
    @ResponseBody
    public TicketCounterHolder getCount() {
        return restHelper.call(TICKET_COUNT, TicketCounterHolder.class);
    }
}
