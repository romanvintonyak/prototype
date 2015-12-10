package com.epam.ticket.dao;

import java.util.Set;

public class EpamCsTicketFilter {
    private String name;
    private String displayName;
    private Set<EpamCsTicketFilterCriteria> filterCriterias;
    
    public EpamCsTicketFilter(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
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
    public Set<EpamCsTicketFilterCriteria> getFilterCriterias() {
        return filterCriterias;
    }
    public void setFilterCriterias(Set<EpamCsTicketFilterCriteria> filterCriterias) {
        this.filterCriterias = filterCriterias;
    }

    
}
