package com.epam.dto;

import java.util.HashMap;
import java.util.Map;

public class EpamFilteredTicketsCounts {
    private final Map<String, Map<String, Integer>> filterCategories = new HashMap<>();

    public void addFilerCategoryCounters(final String filterCategory, final Map<String, Integer> categoryStates) {
        filterCategories.put(filterCategory, categoryStates);
    }

    public Map<String, Map<String, Integer>> getFilterCategories() {
        return filterCategories;
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