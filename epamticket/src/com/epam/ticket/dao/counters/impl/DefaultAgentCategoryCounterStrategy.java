package com.epam.ticket.dao.counters.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.epam.ticket.dao.counters.CategoryCounterStrategy;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class DefaultAgentCategoryCounterStrategy implements CategoryCounterStrategy {
    private final static Logger log = Logger.getLogger(DefaultAgentCategoryCounterStrategy.class);
    private FlexibleSearchService flexibleSearchService;

    @Override
    public Map<String, Integer> countCategory(String categoryName) {

        final String queryAssignedToCurrentAgentString = "SELECT count({c:PK}) "
                + "FROM {CsTicket AS c JOIN User AS u ON {c.assignedAgent} = {u.PK}} WHERE {u:PK} = (?session.user)";

        final String queryUnassignedString = "SELECT count({c:PK}) "
                + "FROM {CsTicket AS c} WHERE {c.assignedAgent} IS NULL";
        
        final String queryAssignedToOthersString = "SELECT count({c:PK}) "
                + "FROM {CsTicket AS c JOIN User AS u ON {c.assignedAgent} = {u.PK}} WHERE {u:PK} <> (?session.user)";

        final Map<String, Integer> category = new HashMap<>();
        Integer count = 0;

        count = searchForCount(queryAssignedToCurrentAgentString);
        // TODO: Must not be hardcoded
        category.put("Assigned to me", count);

        count = 0;
        count = searchForCount(queryUnassignedString);
        // TODO: Must not be hardcoded
        category.put("Unassigned", count);

        count = 0;
        count = searchForCount(queryAssignedToOthersString);
        // TODO: Must not be hardcoded
        category.put("All Group Users", count);
        
        return category;
    }

    private Integer searchForCount(final String queryString) {
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

    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

}
