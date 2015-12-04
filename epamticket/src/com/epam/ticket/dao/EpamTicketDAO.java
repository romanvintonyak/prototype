package com.epam.ticket.dao;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.epam.ticket.facades.EpamTicketSearchCriteria;

import de.hybris.platform.core.Registry;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.ticket.dao.impl.DefaultTicketDao;
import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.model.CsTicketModel;

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

        List<CsTicketPriority> priorities = criteria.getPriorities();
        if (priorities != null && priorities.size() != 0) {
            query.append(getJoiningString());
            query.append("{priority} IN (?priority)");
            paramMap.put("priority", priorities);
        }

        List<CsTicketState> states = criteria.getStates();
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

        LOG.info("Running query: " + query + " with params: " + paramMap);
        SearchResult<CsTicketModel> resultTickets = getFlexibleSearchService()
                .search(query.toString(), paramMap);

        return resultTickets.getResult();
    }

    public Integer getTotalTicketCount() {
        SearchResult result = getFlexibleSearchService().search("SELECT {pk} FROM {CsTicket}");
        int totalCount = result.getTotalCount();
        LOG.info("Ticket count:" + totalCount);
        return totalCount;
    }
    
    public TicketCountsResult getTicketCounts() {
        TicketCountsResult result = new TicketCountsResult();
        StringBuilder queryBuilder = new StringBuilder();
        
        queryBuilder.append(
                  "SELECT {e.code}, count({priority}) " 
                + "FROM {CsTicket AS c JOIN enumerationvalue AS e ON {c.priority}={e.pk} } "
                + "GROUP BY {priority}"
        );
        
        FlexibleSearchQuery query = new FlexibleSearchQuery(queryBuilder);
        query.setResultClassList( Arrays.asList(String.class, Integer.class) );
        final SearchResult<List> qResult = getFlexibleSearchService().search(query);
        
        for (final Iterator<List> iter = qResult.getResult().iterator(); iter.hasNext(); ) {
            List row = iter.next();
            result.getPriority().put((String) row.get(0), (Integer) row.get(1));
        }
        LOG.info("Ticket counts:" + result);
        return result;
    }

    private String getJoiningString() {
        return query.length() == QUERY_STRING.length() ? " WHERE " : " AND ";

    }
    
    public class TicketCountsResult implements Serializable {
        private static final long serialVersionUID = 1L;
        private Map<String, Integer> priority;
        
        public TicketCountsResult() {
            setPriority(new HashMap<>());
        }
        
        public Map<String, Integer> getPriority() {
            return priority;
        }

        private void setPriority(Map<String, Integer> priority) {
            this.priority = priority;
        }
        
        @Override
        public String toString() {
            return "Low=" + priority.get("Low") + ", High=" + priority.get("High") + ", Medium=" + priority.get("Medium");
        }
        
    }

}
