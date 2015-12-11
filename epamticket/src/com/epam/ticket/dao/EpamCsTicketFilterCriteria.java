package com.epam.ticket.dao;

public class EpamCsTicketFilterCriteria {
    private String name;
    private String displayName;
    private String filterQuery;
    private String filterCountQuery;
    
    public EpamCsTicketFilterCriteria(String name, String displayName, String filterQuery, String filterCountQuery) {
        this.name = name;
        this.displayName = displayName;
        this.filterQuery = filterQuery;
        this.filterCountQuery = filterCountQuery;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getFilterQuery() {
        return filterQuery;
    }
    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }
    public String getFilterCountQuery() {
        return filterCountQuery;
    }
    public void setFilterCountQuery(String filterCountQuery) {
        this.filterCountQuery = filterCountQuery;
    }

    
}
