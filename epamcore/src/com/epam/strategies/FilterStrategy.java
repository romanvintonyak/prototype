package com.epam.strategies;

import java.util.List;
import java.util.Set;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;

public interface FilterStrategy {

    Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCounts(EpamTicketsFilter configFilter);

    FilterSubqueryResult buildFilterSubquery(EpamTicketsFilter filter, List<String> criterias);
}
