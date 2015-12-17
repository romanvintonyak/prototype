package com.epam.controllers;

import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamNewTicket;
import com.epam.dto.EpamTicket;
import com.epam.dto.EpamTicketStateHolder;
import com.epam.dto.TicketCounterHolder;
import com.epam.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/rest/tickets")
@RestController
public class CockpitTicketController {

    private static final String PATH = "http://localhost:9001/epamticket/v1/tickets/";

    @Autowired
    private RestHelper restHelper;

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public EpamTicket create(@RequestBody final EpamNewTicket ticket) {
        return restHelper.call(PATH, EpamTicket.class, ticket, HttpMethod.POST);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
    public List<EpamTicket> getAll(final HttpServletRequest request) {
        return restHelper.call(PATH + "?" + request.getQueryString(), List.class);
        // TODO: need to cover base context url
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    public EpamTicket getById(@PathVariable("ticketId") final String ticketId) {
        return restHelper.call(PATH + ticketId, EpamTicket.class);
        // TODO: need to cover base context url
    }

    @RequestMapping(value = "/ticketCount", method = RequestMethod.GET)
    public TicketCounterHolder getCount() {
        return restHelper.call(PATH + "ticketCount", TicketCounterHolder.class);
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @ResponseBody
    public EpamFrontConfig getConfig() {
        return restHelper.call(PATH + "config", EpamFrontConfig.class);
    }

    @RequestMapping(value = "/{ticketId}/changestate", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public EpamTicket changeTicketState(@PathVariable("ticketId") final String ticketId,
                                        @RequestBody EpamTicketStateHolder stateHolder) {
        return restHelper.call(PATH + ticketId + "/changestate", EpamTicket.class, stateHolder, HttpMethod.PUT);
    }
}
