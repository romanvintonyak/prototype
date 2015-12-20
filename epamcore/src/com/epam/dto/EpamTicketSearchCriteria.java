package com.epam.dto;

import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.enums.CsTicketState;

import java.util.List;

public class EpamTicketSearchCriteria {

    // Better would be priorities and states, but there's no simple way to rename it and handle ?priority=. ?state=. requests
    private List<CsTicketPriority> priority;
    private List<CsTicketState> state;
    private List<CsTicketCategory> category;
    private List<String> group;
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
        return "EpamTicketSearchCriteria{" + "priorities=" + priority + "states=" + state + "groups=" + group + ", agentId='" + agentId + '\'' + '}';
    }

    public List<String> getGroup() {
        return group;
    }

    public void setGroup(final List<String> group) {
        this.group = group;
    }

}
