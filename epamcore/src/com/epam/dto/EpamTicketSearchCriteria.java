package com.epam.dto;

import java.io.Serializable;
import java.util.List;

import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.enums.CsTicketState;

public class EpamTicketSearchCriteria implements Serializable {

    private List<CsTicketPriority> priority;
    private List<CsTicketState> state;
    private List<CsTicketCategory> category;
    private String agentId;
    private String sortName;
    private Boolean sortReverse = Boolean.FALSE;

    public void setPriority(final List<CsTicketPriority> priority) {
        this.priority = priority;
    }

    public List<CsTicketPriority> getPriority() {
        return priority;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(final String agentId) {
        this.agentId = agentId;
    }

    public List<CsTicketState> getState() {
        return state;
    }

    public void setState(final List<CsTicketState> state) {
        this.state = state;
    }
    
    public List<CsTicketCategory> getCategories() {
        return category;
    }

    public void setCategories(final List<CsTicketCategory> categories) {
        this.category = categories;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(final String sortName) {
        this.sortName = sortName;
    }

    public Boolean getSortReverse() {
        return sortReverse;
    }

    public void setSortReverse(final Boolean sortReverse) {
        this.sortReverse = sortReverse;
    }

    @Override
    public String toString() {
        return "EpamTicketSearchCriteria{" + "priorities=" + priority + "states=" + state + ", agentId='" + agentId + '\'' + '}';
    }

}
