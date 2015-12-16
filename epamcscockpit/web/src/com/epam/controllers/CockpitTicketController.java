package com.epam.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.EpamFilteredTicketsCounts;
import com.epam.dto.EpamTicket;
import com.epam.dto.TicketCounterHolder;
import com.epam.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rest/tickets")
@RestController
public class CockpitTicketController {

    private static final String PATH = "http://localhost:9001/epamticket/v1/tickets/";

    @Autowired
    private RestHelper restHelper;

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<EpamTicket> getAll(final HttpServletRequest request) 
    {
        return restHelper.call(PATH + "?" + request.getQueryString(), List.class);
        // TODO: need to cover base context url
    }
    
    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    @ResponseBody
    public EpamTicket getById(@PathVariable("ticketId") final String ticketId) {
        return restHelper.call(PATH + ticketId, EpamTicket.class);
        // TODO: need to cover base context url
    }

    @RequestMapping(value = "/ticketCount", method = RequestMethod.GET)
    @ResponseBody
    public TicketCounterHolder getCount() {
        return restHelper.call(PATH + "ticketCount", TicketCounterHolder.class);
    }

    @RequestMapping(value = "/filteredTicketsCounts", method = RequestMethod.GET)
    @ResponseBody
    public EpamFilteredTicketsCounts getTicketCounts() {
        return restHelper.call(PATH + "filteredTicketsCounts", EpamFilteredTicketsCounts.class);
    }
}
