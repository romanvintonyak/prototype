package com.epam.ticket.facades;

import de.hybris.platform.ticket.enums.CsTicketPriority;

import java.io.Serializable;
import java.util.List;

public class EpamTicketSearchCriteria implements Serializable{

    private List<CsTicketPriority> priorities;
    private String agentId;

    public void setPriorities(List<CsTicketPriority> priorities) {
        this.priorities = priorities;
    }

    public List<CsTicketPriority> getPriorities() {
        return priorities;
    }


    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "EpamTicketSearchCriteria{" +
                "priorities=" + priorities +
                ", agentId='" + agentId + '\'' +
                '}';
    }
}
