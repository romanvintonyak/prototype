package com.epam.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EpamFilteredTicketsCounts implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, Map<String, Integer>> filterCategories = new HashMap<>();
    public void addFilerCategoryCounters(final String filterCategory, final Map<String, Integer> categoryStates) {
        filterCategories.put(filterCategory, categoryStates);
    }
    
    public Map<String, Map<String, Integer>> getFilterCategories() {
        return filterCategories;
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