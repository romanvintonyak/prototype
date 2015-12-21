package com.epam.ticket.strategies;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.strategies.FilterStrategy;
import com.epam.strategies.FilterSubqueryResult;
import com.epam.ticket.dao.EpamTicketDAO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

public class DefaultEnumFilterStrategy implements FilterStrategy {
    
    private EpamTicketDAO ticketDao;

    @Override
    public Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCounts(EpamTicketsFilter configFilter) {
        Map<String, Integer> counts = getCounts(configFilter);
        Set<EpamTicketsFilterCriteria> filterCriteriaCounts = setFilterCounters(configFilter, counts);
        return filterCriteriaCounts;
    }

    @Override
    public FilterSubqueryResult buildFilterSubquery(EpamTicketsFilter filter, List<String> criterias) {
        String QUERY = "{%s} IN "
                + "({{SELECT {e.pk} FROM {CsTicket AS t1 JOIN enumerationvalue AS e ON {t1.%s}={e.pk}} " 
                + "WHERE {e.code} IN (?%s)}})";
        FilterSubqueryResult result = new FilterSubqueryResult();
        Map<String, List<String>> params = new HashMap<>();
        params.put(filter.getName(), criterias);
        result.setQuery(String.format(QUERY, filter.getName(), filter.getName(), filter.getName()));
        result.setQueryParams(params);
        return result;
    }

    private Set<EpamTicketsFilterCriteria> setFilterCounters(EpamTicketsFilter configFilter, Map<String, Integer> category) {
        Set<EpamTicketsFilterCriteria> filterCriteriaCounts = new HashSet<>();

        for (EpamTicketsFilterCriteria configCriteria : configFilter.getCriterias()) {
            EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria(configCriteria.getName(), configCriteria.getDisplayName(), null, null);
            Integer count = category.containsKey(configCriteria.getName()) ? category.get(configCriteria.getName()) : Integer.valueOf(0);
            criteria.setCount(count);
            filterCriteriaCounts.add(criteria);
        }
        return filterCriteriaCounts;
    }

    private Map<String, Integer> getCounts(EpamTicketsFilter configFilter) {
        final String queryString = 
                "SELECT {e.code}, count({" + configFilter.getName() + "}) " 
              + "FROM {CsTicket AS c JOIN enumerationvalue AS e ON {c."
                       + configFilter.getName() + "}={e.pk} } "
              + "GROUP BY {" + configFilter.getName() + "}";

        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.setResultClassList( Arrays.asList(String.class, Integer.class) );
        
        Map<String, Integer> category = getTicketDao().getFilterCounts(query);
        return category;
    }

    public EpamTicketDAO getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

}
