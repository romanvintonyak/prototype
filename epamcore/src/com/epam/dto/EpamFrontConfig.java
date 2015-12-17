package com.epam.dto;

import java.util.Set;

public class EpamFrontConfig {

    private Set<EpamTicketsFilterConfig> availableFilters;

    public EpamFrontConfig() {
    }

    public Set<EpamTicketsFilterConfig> getAvailableFilters() {
        return availableFilters;
    }

    public void setAvailableFilters(final Set<EpamTicketsFilterConfig> availableFilters) {
        this.availableFilters = availableFilters;
    }

}
