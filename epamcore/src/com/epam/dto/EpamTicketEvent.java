package com.epam.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EpamTicketEvent implements Serializable {

    private static final long serialVersionUID = 2602461437712001432L;

    private Date startDateTime;
    private Date endDateTime;
    private List<EpamTicketEmail> emails;
    private String text;
    private Set<EpamTicketChangeEventEntry> ticketChangeEventEntries;

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<EpamTicketEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<EpamTicketEmail> emails) {
        this.emails = emails;
    }

    public Set<EpamTicketChangeEventEntry> getTicketChangeEventEntries() {
        return ticketChangeEventEntries;
    }

    public void setTicketChangeEventEntries(Set<EpamTicketChangeEventEntry> ticketChangeEventEntries) {
        this.ticketChangeEventEntries = ticketChangeEventEntries;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
