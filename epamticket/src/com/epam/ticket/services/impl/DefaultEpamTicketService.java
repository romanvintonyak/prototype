package com.epam.ticket.services.impl;

import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.services.EpamTicketService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.ticket.model.CsTicketModel;

public class DefaultEpamTicketService implements EpamTicketService {

    private EpamTicketDAO ticketDao;
    private Set<EpamTicketsFilter> availableFilters;

    @Override
    public List<CsTicketModel> getTicketsByCriteria(Map<String,String[]> searchCriteria) {
        return ticketDao.findTicketsByCriteria(searchCriteria, getAvailableFilters());
    }

    @Override
    public CsTicketModel getTicketById(String ticketId) {
        List<CsTicketModel> tickets = ticketDao.findTicketsById(ticketId);
        if (tickets.size() > 1) {
            throw new AmbiguousIdentifierException("CsTicket with ticketId'" + ticketId + "' is not unique, " + tickets.size() + " results!");
        }
        return tickets.size() == 1 ? tickets.get(0) : null;
    }

    @Override
    public Integer getTotalTicketCount() {
        return ticketDao.getTotalTicketCount();
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public EpamFrontConfig getFrontConfigWithCounters() {
        EpamFrontConfig config = new EpamFrontConfig();

        config.setAvailableFilters(getAvailableFiltersWithCounters());
        return config;
    }

    private Set<EpamTicketsFilter> getAvailableFiltersWithCounters() {
        Set<EpamTicketsFilter> filters = new HashSet<>();

        for (EpamTicketsFilter configFilter : getAvailableFilters()) {
            EpamTicketsFilter filter = new EpamTicketsFilter(configFilter.getName(), configFilter.getDisplayName());

            Set<EpamTicketsFilterCriteria> criterias = getFilterCriteriasWithCount(configFilter);

            filter.setCriterias(criterias);
            filters.add(filter);
        }
        return filters;
    }

    private static Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCount(EpamTicketsFilter configFilter) {
        return configFilter.getFilterStrategy().getFilterCriteriasWithCounts(configFilter);
    }

    public Set<EpamTicketsFilter> getAvailableFilters() {
        return availableFilters;
    }

    public void setAvailableFilters(Set<EpamTicketsFilter> availableFilters) {
        this.availableFilters = availableFilters;
    }
}
