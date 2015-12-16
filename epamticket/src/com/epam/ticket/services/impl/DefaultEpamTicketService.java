package com.epam.ticket.services.impl;

import com.epam.dto.EpamFilteredTicketsCounts;
import com.epam.dto.EpamTicketSearchCriteria;
import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.services.EpamTicketService;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.util.List;

public class DefaultEpamTicketService implements EpamTicketService {

    private EpamTicketDAO ticketDao;

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

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public EpamFilteredTicketsCounts getFilteredTicketsCounts() {
        return ticketDao.getFilteredTicketsCounts();
    }
}
