package com.epam.ticket.data;

import java.util.Set;

public class EpamTicketFrontFilter {

    private String name;
    private String displayName;
    private Set<EpamTicketFrontFilterCriteria> criterias;
    
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
    public Set<EpamTicketFrontFilterCriteria> getCriterias() {
        return criterias;
    }
    public void setCriterias(Set<EpamTicketFrontFilterCriteria> criterias) {
        this.criterias = criterias;
    }
    
    
}
