package com.epam.ticket.dao;

/**
 * Created by gee on 2015-12-05.
 */
public class EpamCsSort {
    private String name;
    private String jsonField;
    private String flexField;

    public EpamCsSort(String field) {
        this.name = this.jsonField = this.flexField = field;
    }

    public EpamCsSort(String name, String jsonField, String flexField) {
        this.name = name;
        this.jsonField = jsonField;
        this.flexField = flexField;
    }

    public String getName() {
        return name;
    }

    public String getJsonField() {
        return jsonField;
    }

    public String getFlexField() {
        return flexField;
    }
}
