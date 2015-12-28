package com.epam.dto;

import java.util.Set;

import com.epam.strategies.FilterStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EpamTicketsFilter {
    private String name;
    private String displayName;
    private Set<EpamTicketsFilterCriteria> criterias;
    @JsonIgnore
    private FilterStrategy filterStrategy;

    public EpamTicketsFilter() {
    }

    public EpamTicketsFilter(final String name, final String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public Set<EpamTicketsFilterCriteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(final Set<EpamTicketsFilterCriteria> criterias) {
        this.criterias = criterias;
    }

    public FilterStrategy getFilterStrategy() {
        return filterStrategy;
    }

    public void setFilterStrategy(final FilterStrategy filterStrategy) {
        this.filterStrategy = filterStrategy;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nEpamTicketsFilterConfig [name=");
        builder.append(name);
        builder.append(", displayName=");
        builder.append(displayName);
        builder.append(", criterias=");
        builder.append(criterias);
        builder.append("]");
        return builder.toString();
    }

}
