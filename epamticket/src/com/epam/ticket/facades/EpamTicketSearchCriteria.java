package com.epam.ticket.facades;

import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.enums.CsTicketState;

import java.io.Serializable;
import java.util.List;

public class EpamTicketSearchCriteria implements Serializable{

    private List<CsTicketPriority> priorities;
    private List<CsTicketState> states;
    private List<CsTicketCategory> categories;
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

	public List<CsTicketState> getStates() {
		return states;
	}

	public void setStates(List<CsTicketState> states) {
		this.states = states;
	}
	
    public List<CsTicketCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<CsTicketCategory> categories) {
		this.categories = categories;
	}

	@Override
    public String toString() {
        return "EpamTicketSearchCriteria{" +
                "priorities=" + priorities +
                "states=" + states +
                ", agentId='" + agentId + '\'' +
                '}';
    }

}
