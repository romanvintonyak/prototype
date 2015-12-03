package com.epam.ticket.data;

import java.io.Serializable;

public class EpamTicketChangeEventEntry implements Serializable {

    private static final long serialVersionUID = -990667506345628920L;

    private String alteredAttribute; //temporary, should be replaced with some enum
    private String oldStringValue;
    private String newStringValue;
    private Object oldBinaryValue;
    private Object newBinaryValue;

    public String getAlteredAttribute() {
        return alteredAttribute;
    }

    public void setAlteredAttribute(String alteredAttribute) {
        this.alteredAttribute = alteredAttribute;
    }

    public String getOldStringValue() {
        return oldStringValue;
    }

    public void setOldStringValue(String oldStringValue) {
        this.oldStringValue = oldStringValue;
    }

    public String getNewStringValue() {
        return newStringValue;
    }

    public void setNewStringValue(String newStringValue) {
        this.newStringValue = newStringValue;
    }

    public Object getOldBinaryValue() {
        return oldBinaryValue;
    }

    public void setOldBinaryValue(Object oldBinaryValue) {
        this.oldBinaryValue = oldBinaryValue;
    }

    public Object getNewBinaryValue() {
        return newBinaryValue;
    }

    public void setNewBinaryValue(Object newBinaryValue) {
        this.newBinaryValue = newBinaryValue;
    }

}
