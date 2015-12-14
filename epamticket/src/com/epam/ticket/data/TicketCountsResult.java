package com.epam.ticket.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TicketCountsResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, Map<String, Integer>> filterCategories = new HashMap<>();
    private final Set<EpamTicketFrontFilter> filters = new HashSet<>();

    public void addFilterCategoryCounters(final String filterCategory, final Map<String, Integer> categoryStates) {
        filterCategories.put(filterCategory, categoryStates);
    }
    
    public void addFilter(EpamTicketFrontFilter filter) {
        filters.add(filter);
    }
    
    public Map<String, Map<String, Integer>> getFilterCategories() {
        return filterCategories;
    }

    public Set<EpamTicketFrontFilter> getFilters() {
        return filters;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TicketCountsResult [filterCategories=\n");
        for (String category : filterCategories.keySet()) {
            builder.append("\t" + category + ": ");
            Map<String, Integer> states = filterCategories.get(category);
            for (String state : states.keySet()) {
                builder.append(state + "-" + states.get(state) + ", ");
            }
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }

}