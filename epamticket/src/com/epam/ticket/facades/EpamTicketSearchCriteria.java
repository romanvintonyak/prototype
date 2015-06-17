package com.epam.ticket.facades;

import de.hybris.platform.ticket.enums.CsTicketPriority;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Viktor_Peretiatko on 6/16/2015.
 */
public class EpamTicketSearchCriteria implements Serializable{


    public void setPriorities(List<CsTicketPriority> priorities) {
        this.priorities = priorities;
    }

    private List<CsTicketPriority> priorities;

    public List<CsTicketPriority> getPriorities() {
        return priorities;
    }


}
