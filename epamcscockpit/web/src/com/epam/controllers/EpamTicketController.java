package com.epam.controllers;

import com.epam.dto.EpamTicket;
import com.epam.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/rest/tickets")
@RestController
public class EpamTicketController {

    @Autowired
    private RestHelper restHelper;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<EpamTicket> getAll() {
        return restHelper.call("http://localhost:9001/epamticket/v1/tickets", List.class);
        // TODO: 12/15/2015 need to cover base context url
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    @ResponseBody
    public EpamTicket getTicketById(@PathVariable("ticketId") final String ticketId) {
        return restHelper.call(String.format("http://localhost:9001/epamticket/v1/tickets/%s", ticketId), EpamTicket.class);
        // TODO: 12/15/2015 need to cover base context url

    }
}
