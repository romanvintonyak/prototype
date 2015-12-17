package com.epam.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class EpamTicketEvent {

    private Date startDateTime;
    private Date endDateTime;
    private List<EpamTicketEmail> emails;
    private String text;
    private Set<EpamTicketChangeEventEntry> ticketChangeEventEntries;

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(final Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<EpamTicketEmail> getEmails() {
        return emails;
    }

    public void setEmails(final List<EpamTicketEmail> emails) {
        this.emails = emails;
    }

    public Set<EpamTicketChangeEventEntry> getTicketChangeEventEntries() {
        return ticketChangeEventEntries;
    }

    public void setTicketChangeEventEntries(final Set<EpamTicketChangeEventEntry> ticketChangeEventEntries) {
        this.ticketChangeEventEntries = ticketChangeEventEntries;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
