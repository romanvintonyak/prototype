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

public abstract class DefaultInNotInFilterStrategy<K> implements FilterStrategy {

    @Override
    public Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCounts(EpamTicketsFilter configFilter) {
        Set<EpamTicketsFilterCriteria> filterCriteriaCounts = new HashSet<>();

        for (EpamTicketsFilterCriteria configCriteria : configFilter.getCriterias()) {
            Map<String, Set<K>> params = new HashMap<>();
            StringBuilder query = new StringBuilder("SELECT count({PK}) FROM {CsTicket} WHERE ");
            query.append(getQueryWhereParam());
            query.append(" " + configCriteria.getFilterQuery() + " ");
            if (configCriteria.isRequireParams()) {
                params.put(configCriteria.getName(), getParams());
                query.append("(?" + configCriteria.getName() + ")");
            }

            List<Integer> counts = getTicketDao().getTicketCountsWithQueryParams(query.toString(), params);
            Integer count = counts.isEmpty() ? Integer.valueOf(0) : counts.get(0);

            EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria(configCriteria.getName(), configCriteria.getDisplayName(), null, false);
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
                if (query.length() > 0) {
                    query.append(" OR ");
                }
                query.append(getQueryWhereParam());
                query.append(" " + filterCriteria.getFilterQuery() + " ");

                if (filterCriteria.isRequireParams()) {
                    params.put(filterCriteria.getName(), getParams());
                    query.append("(?" + filterCriteria.getName() + ")");
                }
            }
        }
        if (query.length() != 0) {
            result.setQuery("(" + query.toString() + ")");
        }
        result.setQueryParams(params);
        return result;
    }

    protected abstract String getQueryWhereParam();

    protected abstract Set<K> getParams();

    public abstract EpamTicketDAO getTicketDao();

}
