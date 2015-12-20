package com.epam.dto;

import java.util.Set;

public class EpamFrontConfig {

    private Set<EpamTicketsFilter> availableFilters;

    public EpamFrontConfig() {
    }

    public Set<EpamTicketsFilter> getAvailableFilters() {
        return availableFilters;
    }

    public void setAvailableFilters(final Set<EpamTicketsFilter> availableFilters) {
        this.availableFilters = availableFilters;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EpamFrontConfig [availableFilters=");
        builder.append(availableFilters);
        builder.append("]");
        return builder.toString();
    }

}
