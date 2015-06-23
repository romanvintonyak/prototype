package com.epam.ticket.controllers;

import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;



@Controller
@RequestMapping
public class EpamTicketController {

    @Autowired
    private DefaultEpamTicketFacade defaultEpamTicketFacade;

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    @ResponseBody
    public Collection<EpamTicket> getTicketsByCriteria( EpamTicketSearchCriteria searchCriteria){
        return defaultEpamTicketFacade.getTicketsByCriteria(searchCriteria);
    }


}
