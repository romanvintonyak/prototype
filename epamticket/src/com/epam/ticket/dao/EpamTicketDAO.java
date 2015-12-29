package com.epam.ticket.dao;

import com.epam.dto.EpamTicketsFilter;
import com.epam.strategies.FilterSubqueryResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.ticket.dao.impl.DefaultTicketDao;
import de.hybris.platform.ticket.model.CsTicketModel;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.google.common.base.Strings.isNullOrEmpty;

public class EpamTicketDAO extends DefaultTicketDao {

    public static final String SORT_REVERSE = "sortReverse";
    public static final String SORT_NAME = "sortName";
    public static final Logger LOG = Logger.getLogger(EpamTicketDAO.class);
    public static final String QUERY_STRING = "SELECT {t:pk} FROM {CsTicket AS t} ";
    protected Map<String, EpamCsSort> sorts = new HashMap<>();

    public List<CsTicketModel> findTicketsByCriteria(Map<String, String[]> searchCriteria, Set<EpamTicketsFilter> filters) {
        StringBuilder query;
        query = new StringBuilder(QUERY_STRING);
        Map<String, Object> paramMap = new TreeMap<>();

        filters.stream().forEach(filter -> {
            List<String> criterias = getCriterias(searchCriteria, filter.getName());
            if (!criterias.isEmpty()) {
                FilterSubqueryResult subQueryResult = filter.getFilterStrategy().buildFilterSubquery(filter, criterias);
                query.append(getFilterSubqueryOrEpmptyString(query.length(), subQueryResult));
                paramMap.putAll(subQueryResult.getQueryParams());
            }
        });

        String sortName = getFirstParamOrNullByName(searchCriteria, SORT_NAME);
        if (!isNullOrEmpty(sortName)) {
            EpamCsSort sort = sorts.get(sortName);
            Assert.notNull(sort, "Sort " + sortName + " not found");
            query.append("ORDER BY {t.").append(sort.getFlexField()).append("} "); // danger, may cause FlexSearch manipulation
            Boolean sortReverse = Boolean.valueOf(getFirstParamOrNullByName(searchCriteria, SORT_REVERSE));
            query.append(sortReverse ? "DESC" : "ASC");
        }

        SearchResult<CsTicketModel> resultTickets = getFlexibleSearchService().search(query.toString(), paramMap);

        return resultTickets.getResult();

    }

    public Integer getTotalTicketCount() {
        return getFlexibleSearchService().search("SELECT {pk} FROM {CsTicket}").getTotalCount();
    }

    public <K> List<Integer> getTicketCountsWithQueryParams(String query, Map<String, Set<K>> params) {
        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        flexibleSearchQuery.addQueryParameters(params);
        flexibleSearchQuery.setResultClassList(Collections.singletonList(Integer.class));
        final SearchResult<Integer> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);
        return searchResult.getResult();
    }

    @SuppressWarnings("rawtypes")
    public Map<String, Integer> getFilterCounts(FlexibleSearchQuery query) {
        SearchResult<List> searchResult = getFlexibleSearchService().search(query);
        List<List> queryResult = searchResult.getResult();

        Map<String, Integer> filterCounts = new HashMap<>();
        queryResult.stream().forEach(row -> filterCounts.put((String) row.get(0), (Integer) row.get(1)));
        return filterCounts;
    }

    public Collection<EpamCsSort> getAvailableSorts() {
        return sorts.values();
    }

    public void setAvailableSorts(Collection<EpamCsSort> sorts) {
        Map<String, EpamCsSort> res = new HashMap<>();
        sorts.stream().forEach(sort -> res.put(sort.getName(), sort));
        this.sorts = res;
    }

    private static List<String> getCriterias(Map<String, String[]> searchCriteria, String name) {
        if (searchCriteria.containsKey(name) && searchCriteria.get(name) != null) {
            return Arrays.asList(searchCriteria.get(name));
        }
        return Collections.emptyList();
    }

    private static String getFilterSubqueryOrEpmptyString(int queryLength, FilterSubqueryResult subQueryResult) {
        StringBuilder query = new StringBuilder();
        if (!subQueryResult.isEmpty()) {
            query.append(getJoiningString(queryLength)).append(subQueryResult.getQuery());
        }
        return query.toString();
    }

    private static String getFirstParamOrNullByName(Map<String, String[]> paramMap, String name) {
        String firstParam = null;
        String[] params = paramMap.get(name);
        if (params != null && params.length != 0) {
            firstParam = params[0];
        }
        return firstParam;
    }

    private static String getJoiningString(int queryLength) {
        return queryLength == QUERY_STRING.length() ? " WHERE " : " AND ";
    }

}
