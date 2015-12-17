package com.epam.ticket.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamTicketSearchCriteria;
import com.epam.dto.EpamTicketsFilterConfig;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.services.EpamTicketService;

import de.hybris.platform.ticket.model.CsTicketModel;

public class DefaultEpamTicketService implements EpamTicketService {

    private EpamTicketDAO ticketDao;
    private Set<EpamTicketsFilterConfig> availableFilters;


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
    public EpamFrontConfig getFrontConfigWithCounters() {
        EpamFrontConfig config = new EpamFrontConfig();
        
        config.setAvailableFilters(getAvailableFiltersWithCounters());
        return config;
    }

    private Set<EpamTicketsFilterConfig> getAvailableFiltersWithCounters() {
        Set<EpamTicketsFilterConfig> filters = new HashSet<>();
        
        for (EpamTicketsFilterConfig configFilter: getAvailableFilters()) {
            EpamTicketsFilterConfig filter = new EpamTicketsFilterConfig(configFilter.getName(), configFilter.getDisplayName());
            Set<EpamTicketsFilterCriteria> criterias = new HashSet<>();
            
            for (EpamTicketsFilterCriteria configCriteria : configFilter.getCriterias()) {
                List<Integer> counts = ticketDao.getTicketCountWithCriteria(configCriteria);
                Integer count = counts.isEmpty() ? Integer.valueOf(0) : counts.get(0);
                
                EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria(
                        configCriteria.getName(), configCriteria.getDisplayName(), null, null);
                criteria.setCount(count);
                criterias.add(criteria);
            }
            filter.setCriterias(criterias);
            filters.add(filter);
        }
        return filters;
    }

    public Set<EpamTicketsFilterConfig> getAvailableFilters() {
        return availableFilters;
    }

    public void setAvailableFilters(Set<EpamTicketsFilterConfig> availableFilters) {
        this.availableFilters = availableFilters;
    }
}
