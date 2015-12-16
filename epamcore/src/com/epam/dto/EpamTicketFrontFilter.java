package com.epam.dto;

import java.util.Set;

public class EpamTicketFrontFilter {

    private String name;
    private String displayName;
    private Set<EpamTicketFrontFilterCriteria> criterias;

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

    public Set<EpamTicketFrontFilterCriteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(final Set<EpamTicketFrontFilterCriteria> criterias) {
        this.criterias = criterias;
    }

}
