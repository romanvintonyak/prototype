package com.epam.ticket.data;

import java.io.Serializable;

public class EpamNewTicket implements Serializable {

    private EpamTicket newTicket;
    private EpamCustomerEvent creationEvent;

    public EpamTicket getNewTicket() {
        return newTicket;
    }

    public void setNewTicket(EpamTicket newTicket) {
        this.newTicket = newTicket;
    }

    public EpamCustomerEvent getCreationEvent() {
        return creationEvent;
    }

    public void setCreationEvent(EpamCustomerEvent creationEvent) {
        this.creationEvent = creationEvent;
    }
}
