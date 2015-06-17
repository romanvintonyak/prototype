package com.epam.ticket.dao;

import com.epam.ticket.facades.EpamTicketSearchCriteria;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.ticket.dao.impl.DefaultTicketDao;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EpamTicketDAO extends DefaultTicketDao {


    public List<CsTicketModel> findTicketsByCriteria(EpamTicketSearchCriteria criteria) {
        Map<String, List<CsTicketPriority>> paramMap = new TreeMap<>();
        StringBuffer query = new StringBuffer("SELECT {t:pk} FROM {CsTicket AS t} ");

        List<CsTicketPriority> priorities = criteria.getPriorities();
        if (priorities != null && priorities.size() != 0) {
            query.append(" WHERE ");
            query.append("{priority} IN (?priority)");
            paramMap.put("priority", priorities);
        }

        SearchResult<CsTicketModel> resultTickets = getFlexibleSearchService()
                .search(query.toString(), paramMap);

        return resultTickets.getResult();
    }
}
