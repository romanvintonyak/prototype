package com.epam.ticket.data;

import java.io.Serializable;

public class EpamTicketEmail implements Serializable {

    private static final long serialVersionUID = 5155325739428603890L;

    private String messageId;
    private String from;
    private String to;
    private String subject;
    private String body;
    private long ticketEventId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTicketEventId() {
        return ticketEventId;
    }

    public void setTicketEventId(long ticketEventId) {
        this.ticketEventId = ticketEventId;
    }
}
