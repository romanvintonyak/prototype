package com.epam.ticket.dao.counters;

import java.util.Map;

public interface CategoryCounterStrategy {
    Map<String, Integer> countCategory(String categoryName);
}
