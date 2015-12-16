package com.epam.dto;

import java.io.Serializable;

public class TicketCounterHolder implements Serializable {

    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

