package com.epam.dto;

public class EpamCustomerEvent extends EpamTicketEvent {

    private String text;
    private String interventionType;
    private String reason;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(final String text) {
        this.text = text;
    }

    public String getInterventionType() {
        return interventionType;
    }

    public void setInterventionType(final String interventionType) {
        this.interventionType = interventionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }
}
