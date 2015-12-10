package com.epam.ticket.services.impl;

import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import com.epam.ticket.services.EpamTicketService;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketBusinessService;

import java.util.List;

public class DefaultEpamTicketService implements EpamTicketService {

    private TicketBusinessService ticketBusinessService;
    private EpamTicketDAO ticketDao;

    @Override
    public void addTicket(CsTicketModel ticket, CsCustomerEventModel creationEvent) {
        ticketBusinessService.createTicket(ticket.getCustomer(), ticket.getCategory(), ticket.getPriority(),
                ticket.getAssignedAgent(), ticket.getAssignedGroup(), ticket.getHeadline(),
                creationEvent.getInterventionType(), creationEvent.getReason(), creationEvent.getText());
    }

    @Override
    public List<CsTicketModel> getTicketsByCriteria(EpamTicketSearchCriteria searchCriteria) {
        return ticketDao.findTicketsByCriteria(searchCriteria);
    }

    @Override
    public CsTicketModel getTicketById(String ticketId) {
        return ticketDao.getTicketById(ticketId);
    }

    @Override
    public Integer getTotalTicketCount() {
        return ticketDao.getTotalTicketCount();
    }

    public void setTicketBusinessService(TicketBusinessService ticketBusinessService) {
        this.ticketBusinessService = ticketBusinessService;
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }
}
