package com.epam.ticket.dao.strategies;

import java.util.Map;

public interface FilterStrategyStrategy {
    Map<String, Integer> countTickets(String categoryName);
    String buildFilterSubquery(String categoryName);
}
