package com.epam.ticket.attributehandlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.ticket.model.EpamCsTicketModel;
import com.epam.ticket.services.EpamTicketService;

import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;

@Component
public class EpamticketsCountAttributeHandler extends AbstractDynamicAttributeHandler<Integer, EpamCsTicketModel> {
    public static final Logger LOG = Logger.getLogger(EpamticketsCountAttributeHandler.class);
    
    @Autowired
    private EpamTicketService epamTicketService;

    @Override
    public Integer get(EpamCsTicketModel model) {
        LOG.info("In countHandler");
        return epamTicketService.getTotalTicketCount();
    }
    
    public void setEpamTicketService(final EpamTicketService epamTicketService) {
        this.epamTicketService = epamTicketService;
    }

}
