package com.epam.dto;

import java.util.Set;

public class EpamTicketsFilterConfig {
    private String name;
    private String displayName;
    private Set<EpamTicketsFilterCriteria> criterias;

    public EpamTicketsFilterConfig() {
    }

    public EpamTicketsFilterConfig(final String name, final String displayName) {
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

}
