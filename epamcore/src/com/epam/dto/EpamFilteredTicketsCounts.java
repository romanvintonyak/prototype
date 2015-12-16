package com.epam.dto;

import java.util.HashSet;
import java.util.Set;

public class EpamFilteredTicketsCounts {
    private final Set<EpamTicketFrontFilter> filters = new HashSet<>();

    public void addFilter(final EpamTicketFrontFilter filter) {
        filters.add(filter);
    }

    public Set<EpamTicketFrontFilter> getFilters() {
        return filters;
    }

}