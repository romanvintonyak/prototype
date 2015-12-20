package com.epam.ticket.dao;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;

import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.ticket.dao.impl.DefaultTicketDao;
import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.model.CsTicketModel;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.google.common.base.Strings.isNullOrEmpty;

public class EpamTicketDAO extends DefaultTicketDao {

    public static final Logger LOG = Logger.getLogger(EpamTicketDAO.class);
    public static final String QUERY_STRING = "SELECT {t:pk} FROM {CsTicket AS t} ";
    private StringBuffer query;
    private UserService userService;
    private EnumerationService enumerationService;

    public CsTicketModel getTicketById(String ticketId) {
        List<CsTicketModel> csTicketModels = this.findTicketsById(ticketId);
        if (csTicketModels.size() > 1) {
            throw new AmbiguousIdentifierException("CsTicket with ticketId'" + ticketId + "' is not unique, " + csTicketModels.size() + " results!");
        }
        return csTicketModels.size() == 1 ? csTicketModels.get(0) : null;

    }

    public List<CsTicketModel> findTicketsByCriteria(Map<String, String[]> searchCriteria,
            Set<EpamTicketsFilter> filters) {
        query = new StringBuffer(QUERY_STRING);
        Map<String, Object> paramMap = new TreeMap<>();

        for (String criteria : searchCriteria.keySet()) {
            LOG.info("searchcriteria: " + criteria);
        }

        for (EpamTicketsFilter filter : filters) {
            if (searchCriteria.containsKey(filter.getName())) {
                List<String> criterias = Arrays.asList(searchCriteria.get(filter.getName()));
                if (criterias != null && criterias.size() != 0) {
                    if (filter.getFilterStrategy().getType().equals("ENUM")) {
                        query.append(getJoiningString());
                        query.append(filter.getFilterStrategy().buildFilterSubquery(filter.getName()));
                        paramMap.put(filter.getName(), criterias);

                    } else {
                        int queryLengthSoFar = query.length();
                        for (EpamTicketsFilterCriteria filterCriteria : filter.getCriterias()) {
                            if (criterias.contains(filterCriteria.getName())) {
                                query.append(getJoiningString(queryLengthSoFar));
                                query.append(filter.getFilterStrategy().buildFilterSubquery(filter.getName()));
                                query.append(" " + filterCriteria.getFilterQuery() + " ");
                                if (!filterCriteria.getFilterQuery().equals("IS NULL")) {
                                    query.append("(?" + filterCriteria.getName() + ")");
                                    paramMap.put(filterCriteria.getName(), filter.getFilterStrategy().getParams());
                                }
                            }
                        }
                        query.append(getEndString(queryLengthSoFar));
                    }
                }
            }

        }


        /*
         * String [] sorts = searchCriteria.get("sortName"); String field = ; //criteria.getSortName(); // todo validate field if (!isNullOrEmpty(field)) {
         * EpamCsSort sort = sorts.get(criteria.getSortName()); if (sort == null) throw new IllegalArgumentException("Sort " + criteria.getSortName() +
         * " not found" ); query.append("ORDER BY {t."); query.append(sort.getFlexField()).append("} "); // danger, may cause FlexSearch manipulation
         * query.append(criteria.getSortReverse() ? "DESC" : "ASC");
         * 
         * }
         */
        LOG.info("Running query: " + query + " with params: " + paramMap);

        SearchResult<CsTicketModel> resultTickets = getFlexibleSearchService().search(query.toString(), paramMap);

        return resultTickets.getResult();

    }

    @SuppressWarnings("rawtypes")
    public Integer getTotalTicketCount() {
        SearchResult result = getFlexibleSearchService().search("SELECT {pk} FROM {CsTicket}");
        int totalCount = result.getTotalCount();
        return totalCount;
    }

    public List<Integer> getTicketCountWithCriteria(EpamTicketsFilterCriteria criteria) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(criteria.getFilterCountQuery());
        query.setResultClassList(Collections.singletonList(Integer.class));
        final SearchResult<Integer> searchResult = getFlexibleSearchService().search(query);
        return searchResult.getResult();
    }

    private String getJoiningString() {
        return query.length() == QUERY_STRING.length() ? " WHERE " : " AND ";
    }

    private String getJoiningString(int currentLength) {
        return query.length() == QUERY_STRING.length() ? " WHERE (" : query.length() == currentLength ? " AND (" : " OR ";
    }
    
    private String getEndString(int length) {
        return query.length() == length ? "" : ")";
    }

    protected Map<String, EpamCsSort> sorts = new HashMap<>();

    public Collection<EpamCsSort> getAvailableSorts() {
        return sorts.values();
    }

    public void setAvailableSorts(Collection<EpamCsSort> sorts) {
        Map<String, EpamCsSort> res = new HashMap<>();
        for (EpamCsSort sort : sorts) {
            res.put(sort.getName(), sort);
        }
        this.sorts = res;
    }

    @SuppressWarnings("rawtypes")
    public Map<String, Integer> getFilterCounts(FlexibleSearchQuery query) {
        SearchResult<List> searchResult = getFlexibleSearchService().search(query);
        List<List> queryResult = searchResult.getResult();

        Map<String, Integer> filterCounts = new HashMap<>();
        for (Iterator<List> iter = queryResult.iterator(); iter.hasNext();) {
            List row = iter.next();
            filterCounts.put((String) row.get(0), (Integer) row.get(1));
        }
        return filterCounts;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public EnumerationService getEnumerationService() {
        return enumerationService;
    }

    public void setEnumerationService(EnumerationService enumerationService) {
        this.enumerationService = enumerationService;
    }

}
