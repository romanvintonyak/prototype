package com.epam.ticket.data;

import java.io.Serializable;

public class EpamTicket  implements Serializable{
    private String ticketId;
    private String customer;
    private String order;
    private String category;
    private String priority;
    private String state;
    private String assightedEmployee;
    private String assightedgroup;
    private String headline;
    private String creationTime;

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    private String modifyTime;


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAssightedEmployee() {
        return assightedEmployee;
    }

    public void setAssightedEmployee(String assightedEmployee) {
        this.assightedEmployee = assightedEmployee;
    }

    public String getAssightedgroup() {
        return assightedgroup;
    }

    public void setAssightedgroup(String assightedgroup) {
        this.assightedgroup = assightedgroup;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }



}
