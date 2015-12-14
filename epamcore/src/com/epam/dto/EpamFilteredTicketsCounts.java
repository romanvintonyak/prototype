package com.epam.dto;

import java.util.HashMap;
import java.util.Map;

public class EpamFilteredTicketsCounts {
    private final Map<String, Map<String, Integer>> filterCategories = new HashMap<>();
    private final Set<EpamTicketFrontFilter> filters = new HashSet<>();

    public void addFilerCategoryCounters(final String filterCategory, final Map<String, Integer> categoryStates) {
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
        final StringBuilder builder = new StringBuilder();
        builder.append("TicketCountsResult [filterCategories=\n");
        for (final String category : filterCategories.keySet()) {
            builder.append("\t" + category + ": ");
            final Map<String, Integer> states = filterCategories.get(category);
            for (final String state : states.keySet()) {
                builder.append(state + "-" + states.get(state) + ", ");
            }
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }
}