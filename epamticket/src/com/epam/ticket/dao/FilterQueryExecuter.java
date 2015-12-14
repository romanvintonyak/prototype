package com.epam.ticket.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.epam.ticket.dao.counters.impl.DefaultAgentCategoryCounterStrategy;
import com.epam.ticket.data.EpamTicketFrontFilter;
import com.epam.ticket.data.EpamTicketFrontFilterCriteria;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class FilterQueryExecuter {
    @SuppressWarnings("unused")
    private final static Logger log = Logger.getLogger(DefaultAgentCategoryCounterStrategy.class);

    public static Map<String, Integer> execute(FlexibleSearchService flexibleSearchService, Set<EpamCsTicketFilterCriteria> criterias) {
        final Map<String, Integer> result = new HashMap<>();

        for (EpamCsTicketFilterCriteria criteria : criterias) {
            result.put(criteria.getDisplayName(), searchForCount(flexibleSearchService, criteria.getFilterCountQuery()));
        }

        return result;
    }

    public static EpamTicketFrontFilter executeFilter(FlexibleSearchService flexibleSearchService, EpamCsTicketFilter filter) {
        final EpamTicketFrontFilter result = new EpamTicketFrontFilter();

        Set<EpamTicketFrontFilterCriteria> frontCriterias = new HashSet<>();
        for (EpamCsTicketFilterCriteria criteria : filter.getFilterCriterias()) {
            EpamTicketFrontFilterCriteria frontCriteria = new EpamTicketFrontFilterCriteria();
            frontCriteria.setName(criteria.getName());
            frontCriteria.setDisplayName(criteria.getDisplayName());
            frontCriteria.setCount(searchForCount(flexibleSearchService, criteria.getFilterCountQuery()));
            frontCriterias.add(frontCriteria);
        }
        result.setCriterias(frontCriterias);
        result.setName(filter.getName());
        result.setDisplayName(filter.getDisplayName());
        return result;
    }

    private static Integer searchForCount(FlexibleSearchService flexibleSearchService, final String queryString) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        Integer count = 0;
        query.setResultClassList(Collections.singletonList(Integer.class));
        final SearchResult<Integer> searchResult = flexibleSearchService.search(query);
        final List<Integer> categoryStates = searchResult.getResult();

        if (categoryStates != null && !categoryStates.isEmpty()) {
            count = categoryStates.get(0);
        }
        return count;
    }

}
