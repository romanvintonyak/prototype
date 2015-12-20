package com.epam.strategies;

import java.util.Set;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;

public interface FilterStrategy {

    String getType();

    Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCounts(EpamTicketsFilter configFilter);

    String buildFilterSubquery(String categoryName);

    Set<?> getParams();
}
