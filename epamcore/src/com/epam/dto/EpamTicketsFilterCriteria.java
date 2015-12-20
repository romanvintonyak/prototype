package com.epam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import org.codehaus.jackson.annotate.JsonIgnore;

public class EpamTicketsFilterCriteria {
    private String name;
    private String displayName;
    private Integer count;
    @JsonIgnore
    private String filterQuery;
    @JsonIgnore
    private String filterCountQuery;

    public EpamTicketsFilterCriteria() {
    }

    public EpamTicketsFilterCriteria(final String name, final String displayName, final String filterQuery, final String filterCountQuery) {
        this.name = name;
        this.displayName = displayName;
        this.filterQuery = filterQuery;
        this.filterCountQuery = filterCountQuery;
        this.count = Integer.valueOf(0);
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

    public String getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(final String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public String getFilterCountQuery() {
        return filterCountQuery;
    }

    public void setFilterCountQuery(final String filterCountQuery) {
        this.filterCountQuery = filterCountQuery;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nEpamTicketsFilterCriteria [name=");
        builder.append(name);
        builder.append(", displayName=");
        builder.append(displayName);
        builder.append(", count=");
        builder.append(count);
        builder.append("]");
        return builder.toString();
    }

}
