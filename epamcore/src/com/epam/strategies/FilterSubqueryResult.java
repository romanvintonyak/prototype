package com.epam.strategies;

import java.util.Map;

public class FilterSubqueryResult {
    private String query;
    private Map<? extends String, ? extends Object> queryParams;

    public String getQuery() {
        return query;
    }

    public void setQuery(final String query) {
        this.query = query;
    }
    
    public boolean isEmpty() {
        return query == null || query.isEmpty();
    }

    /**
     * Returns parameters for query. If parameters are empty returns empty map
     *
     * @return Map
     */
    public Map<? extends String, ? extends Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(final Map<? extends String, ? extends Object> queryParams) {
        this.queryParams = queryParams;
    }

}
