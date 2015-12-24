package com.epam.ticket.strategies;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.strategies.FilterStrategy;
import com.epam.strategies.FilterSubqueryResult;
import com.epam.ticket.dao.EpamTicketDAO;

import org.apache.commons.lang.StringUtils;

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
    public Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCounts(final EpamTicketsFilter configFilter) {
        Map<String, Integer> counts = getCounts(configFilter);
        return setFilterCounters(configFilter, counts);
    }

    @Override
    public FilterSubqueryResult buildFilterSubquery(final EpamTicketsFilter filter, final List<String> criterias) {
        String query = "{%s} IN "
                + "({{SELECT {e.pk} " 
                + "FROM {CsTicket AS t1 JOIN enumerationvalue AS e ON {t1.%s}={e.pk}} " 
                + "WHERE {e.code} IN (?%s)}})";
        
        FilterSubqueryResult result = new FilterSubqueryResult();
        Map<String, List<String>> params = new HashMap<>();
        
        params.put(filter.getName(), criterias);
        result.setQuery(StringUtils.replace(query, "%s", filter.getName()));
        result.setQueryParams(params);
        
        return result;
    }

    private Map<String, Integer> getCounts(final EpamTicketsFilter filter) {
        final String queryString = 
                "SELECT {e.code}, count({%s}) " 
              + "FROM {CsTicket AS c JOIN enumerationvalue AS e ON {c.%s}={e.pk} } "
              + "GROUP BY {%s}";

        final FlexibleSearchQuery query = new FlexibleSearchQuery(StringUtils.replace(queryString, "%s", filter.getName()));
        query.setResultClassList( Arrays.asList(String.class, Integer.class) );
        
        return getTicketDao().getFilterCounts(query);
    }

    private static Set<EpamTicketsFilterCriteria> setFilterCounters(final EpamTicketsFilter configFilter, final Map<String, Integer> category) {
        Set<EpamTicketsFilterCriteria> filterCriteriaCounts = new HashSet<>();

        for (EpamTicketsFilterCriteria configCriteria : configFilter.getCriterias()) {
            EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria(configCriteria.getName(), configCriteria.getDisplayName(), null, false);
            Integer count = category.containsKey(configCriteria.getName()) ? category.get(configCriteria.getName()) : Integer.valueOf(0);
            criteria.setCount(count);
            filterCriteriaCounts.add(criteria);
        }
        return filterCriteriaCounts;
    }

    public EpamTicketDAO getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

}
