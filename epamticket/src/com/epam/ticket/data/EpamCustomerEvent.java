package com.epam.ticket.data;

import java.io.Serializable;

public class EpamCustomerEvent extends EpamTicketEvent implements Serializable {

    private String text;
    private String interventionType;
    private String reason;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInterventionType() {
        return interventionType;
    }

    public void setInterventionType(String interventionType) {
        this.interventionType = interventionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
