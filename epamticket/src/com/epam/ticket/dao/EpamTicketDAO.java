package com.epam.ticket.dao;

import com.epam.dto.EpamTicketSearchCriteria;
import com.epam.dto.EpamTicketsFilterCriteria;

import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.ticket.dao.impl.DefaultTicketDao;
import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.model.CsTicketModel;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.google.common.base.Strings.isNullOrEmpty;

public class EpamTicketDAO extends DefaultTicketDao {
    
    public static final Logger LOG = Logger.getLogger(EpamTicketDAO.class);
    public static final String QUERY_STRING = "SELECT {t:pk} FROM {CsTicket AS t} ";
    private StringBuffer query;
    
    public CsTicketModel getTicketById(String ticketId) {
        List<CsTicketModel> csTicketModels = this.findTicketsById(ticketId);
        if (csTicketModels.size() > 1) {
            throw new AmbiguousIdentifierException("CsTicket with ticketId'" + ticketId + "' is not unique, " + csTicketModels.size() + " results!");
        }
        return csTicketModels.size() == 1 ? csTicketModels.get(0) : null;

    }

    public List<CsTicketModel> findTicketsByCriteria(EpamTicketSearchCriteria criteria) {
        query = new StringBuffer(QUERY_STRING);
        Map<String, Object> paramMap = new TreeMap<>();

        List<CsTicketPriority> priorities = criteria.getPriority();
        if (priorities != null && priorities.size() != 0) {
            query.append(getJoiningString());
            query.append("{priority} IN (?priority)");
            paramMap.put("priority", priorities);
        }

        List<CsTicketState> states = criteria.getState();
        if (states != null && states.size() != 0) {
            query.append(getJoiningString());
            query.append("{state} IN (?state)");
            paramMap.put("state", states);
        }

        List<CsTicketCategory> categories = criteria.getCategories();
        if (categories != null && categories.size() != 0) {
            query.append(getJoiningString());
            query.append("{category} IN (?category)");
            paramMap.put("category", categories);
        }

        String agentId = criteria.getAgentId();
        if (!isNullOrEmpty(agentId)) {
            query.append(getJoiningString());
            query.append("{assignedAgent} = ?agent");
            paramMap.put("agent", agentId);
        }

        String field = criteria.getSortName(); //todo validate field
        if (!isNullOrEmpty(field)) {
            EpamCsSort sort = sorts.get(criteria.getSortName());
            if(sort == null)
                throw new IllegalArgumentException("Sort " + criteria.getSortName() + " not found");
            query.append("ORDER BY {t.");
            query.append(sort.getFlexField()).append("} "); // danger, may cause FlexSearch manipulation
            query.append(criteria.getSortReverse()
                    ? "DESC" : "ASC");

        }

        LOG.info("Running query: " + query + " with params: " + paramMap);
        SearchResult<CsTicketModel> resultTickets = getFlexibleSearchService()
                .search(query.toString(), paramMap);

        return resultTickets.getResult();
    }

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

}
