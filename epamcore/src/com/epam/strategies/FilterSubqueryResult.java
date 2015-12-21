package com.epam.strategies;

import java.util.Map;

public class FilterSubqueryResult {
    private String Query;
    private Map<? extends String, ? extends Object> queryParams;

    public String getQuery() {
        return Query;
    }

    public void setQuery(final String query) {
        Query = query;
    }

    public Map<? extends String, ? extends Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(final Map<? extends String, ? extends Object> queryParams) {
        this.queryParams = queryParams;
    }

}
