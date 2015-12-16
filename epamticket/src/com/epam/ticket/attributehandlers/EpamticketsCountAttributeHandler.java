package com.epam.ticket.attributehandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.ticket.model.EpamCsTicketModel;
import com.epam.ticket.services.EpamTicketService;

import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;

@Component
public class EpamticketsCountAttributeHandler extends AbstractDynamicAttributeHandler<Integer, EpamCsTicketModel> {
    
    @Autowired
    private EpamTicketService epamTicketService;

    @Override
    public Integer get(EpamCsTicketModel model) {
        return epamTicketService.getTotalTicketCount();
    }
    
    public void setEpamTicketService(final EpamTicketService epamTicketService) {
        this.epamTicketService = epamTicketService;
    }

}
