package com.epam.ticket.dao;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.epam.ticket.facades.EpamTicketSearchCriteria;

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
        FlexibleSearchQuery query = new FlexibleSearchQuery(
                  "SELECT PL.PLOW, PH.PHIGH, PM.PMEDIUM "
                + "FROM "
                + "( "
                + "  {{SELECT count(*) AS PLOW "
                + "    FROM {CsTicket AS c JOIN EnumerationValue AS ev ON {c.priority}={ev.pk}} "
                + "    WHERE {ev.code} = 'Low' "
                + "  }} "
                + ") AS PL, "
                + "(  "
                + "  {{SELECT count({c1.pk}) AS PHIGH "
                + "    FROM {CsTicket AS c1 JOIN EnumerationValue AS ev1 ON {c1.priority}={ev1.pk}} "
                + "    WHERE {ev1.code} = 'High' "
                + "  }} "
                + ") AS PH, "
                + "( "
                + "  {{SELECT count({c1.pk}) AS PMEDIUM "
                + "    FROM {CsTicket AS c1 JOIN EnumerationValue AS ev1 ON {c1.priority}={ev1.pk}} "
                + "    WHERE {ev1.code} = 'Medium' "
                + "  }} "
                + ") AS PM"
        );
        query.setResultClassList(Arrays.asList(Integer.class, Integer.class, Integer.class));
        final SearchResult<List<Integer>> qResult = getFlexibleSearchService().search(query);
        List<Integer> qr = qResult.getResult().get(0);
        if (qr != null && !qr.isEmpty()) {
            result.setpLow(qr.get(0)); result.getPriority().put("Low", qr.get(0));
            result.setpHigh(qr.get(1)); result.getPriority().put("High", qr.get(1));
            result.setpMedium(qr.get(2)); result.getPriority().put("Medium", qr.get(2));
        }
        LOG.info("Ticket counts:" + result);
        return result;
    }

    private String getJoiningString() {
        return query.length() == QUERY_STRING.length() ? " WHERE " : " AND ";

    }
    
    public class TicketCountsResult implements Serializable {
        private int pLow;
        private int pHigh;
        private int pMedium;
        private Map<String, Integer> priority;
        
        public TicketCountsResult() {
            setPriority(new HashMap<>());
        }
        
        public int getpLow() {
            return pLow;
        }
        public void setpLow(int plow) {
            this.pLow = plow;
        }
        public int getpHigh() {
            return pHigh;
        }
        public void setpHigh(int phigh) {
            this.pHigh = phigh;
        }
        public int getpMedium() {
            return pMedium;
        }
        public void setpMedium(int pmedium) {
            this.pMedium = pmedium;
        }
        @Override
        public String toString() {
            return "pLow=" + pLow + ", pHigh=" + pHigh + ", pMedium=" + "pMedium";
        }

        public Map<String, Integer> getPriority() {
            return priority;
        }

        private void setPriority(Map<String, Integer> priority) {
            this.priority = priority;
        }
        
        
    }

}
