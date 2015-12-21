package com.epam.ticket.strategies;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.strategies.FilterStrategy;
import com.epam.strategies.FilterSubqueryResult;
import com.epam.ticket.dao.EpamTicketDAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class DefaultInNotInFilterStrategy implements FilterStrategy {

    @Override
    public Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCounts(EpamTicketsFilter configFilter) {
        Set<EpamTicketsFilterCriteria> filterCriteriaCounts = new HashSet<>();

        for (EpamTicketsFilterCriteria configCriteria : configFilter.getCriterias()) {
           Map<String, Set<? extends Object>> params = new HashMap<>();
           StringBuilder query = new StringBuilder("SELECT count({PK}) FROM {CsTicket} WHERE ");
            query.append(getQueryWhereParam());
            query.append(" " + configCriteria.getFilterQuery() + " ");
            if (!configCriteria.getFilterQuery().equals("IS NULL")) {
                params.put(configCriteria.getName(), getParams());
                query.append("(?" + configCriteria.getName() + ")");
            }
            
            List<Integer> counts = getTicketDao().getTicketCountsWithQueryParams(query.toString(), params);
            Integer count = counts.isEmpty() ? Integer.valueOf(0) : counts.get(0);

            EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria(configCriteria.getName(), configCriteria.getDisplayName(), null, null);
            criteria.setCount(count);
            filterCriteriaCounts.add(criteria);
        }
        return filterCriteriaCounts;
    }

    @Override
    public FilterSubqueryResult buildFilterSubquery(EpamTicketsFilter filter, List<String> criterias) {
        FilterSubqueryResult result = new FilterSubqueryResult();
        StringBuilder query = new StringBuilder();
        Map<String, Set<? extends Object>> params = new HashMap<>();

        for (EpamTicketsFilterCriteria filterCriteria : filter.getCriterias()) {
            if (criterias.contains(filterCriteria.getName())) {
                if (query.length() > 0)
                    query.append(" OR ");
                query.append(getQueryWhereParam());
                query.append(" " + filterCriteria.getFilterQuery() + " ");

                if (!filterCriteria.getFilterQuery().equals("IS NULL")) {
                    params.put(filterCriteria.getName(), getParams());
                    query.append("(?" + filterCriteria.getName() + ")");
                    result.setQueryParams(params);
                }
            }
        }
        if (query.length() != 0) {
            result.setQuery("(" + query.toString() + ")");
        }
        return result;
    }

    protected abstract String getQueryWhereParam();

    protected abstract Set<? extends Object> getParams();

    public abstract EpamTicketDAO getTicketDao();

}
