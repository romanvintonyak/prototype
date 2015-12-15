package com.epam.ticket.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TicketCountsResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Set<EpamTicketFrontFilter> filters = new HashSet<>();

    public void addFilter(EpamTicketFrontFilter filter) {
        filters.add(filter);
    }
    
    public Set<EpamTicketFrontFilter> getFilters() {
        return filters;
    }

/*    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TicketCountsResult [filterCategories=\n");
        for (String category : filters) {
            builder.append("\t" + category.get + ": ");
            Map<String, Integer> states = filterCategories.get(category);
            for (String state : states.keySet()) {
                builder.append(state + "-" + states.get(state) + ", ");
            }
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }
*/
}