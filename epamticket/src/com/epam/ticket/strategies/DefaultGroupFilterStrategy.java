package com.epam.ticket.strategies;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.strategies.FilterStrategy;
import com.epam.ticket.dao.EpamTicketDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hybris.platform.servicelayer.user.UserService;

public class DefaultGroupFilterStrategy implements FilterStrategy {

    private EpamTicketDAO ticketDao;
    private UserService userService;

    @Override
    public String getType() {
        return "GROUP";
    }

    @Override
    public Set<EpamTicketsFilterCriteria> getFilterCriteriasWithCounts(EpamTicketsFilter configFilter) {
        Set<EpamTicketsFilterCriteria> filterCriteriaCounts = new HashSet<>();

        for (EpamTicketsFilterCriteria configCriteria : configFilter.getCriterias()) {
            List<Integer> counts = getTicketDao().getTicketCountWithCriteria(configCriteria);
            Integer count = counts.isEmpty() ? Integer.valueOf(0) : counts.get(0);

            EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria(configCriteria.getName(), configCriteria.getDisplayName(), null, null);
            criteria.setCount(count);
            filterCriteriaCounts.add(criteria);
        }
        return filterCriteriaCounts;
    }

    @Override
    public String buildFilterSubquery(/*EpamTicketsFilter filter, List<String> criterias*/ String dummy) {
        
/*        StringBuffer query = new StringBuffer();
        int queryLengthSoFar = 0;
        for (EpamTicketsFilterCriteria filterCriteria : filter.getCriterias()) {
            if (criterias.contains(filterCriteria.getName())) {
                if (query.length() > 0 ) query.append(" OR ");
                query.append(filter.getFilterStrategy().buildFilterSubquery(filter.getName()));
                query.append(" " + filterCriteria.getFilterQuery() + " ");

                if (!filterCriteria.getFilterQuery().equals("IS NULL")) {
                    query.append("(?" + filterCriteria.getName() + ")");
                    //paramMap.put(filterCriteria.getName(), filter.getFilterStrategy().getParams());
                }
            }
        }
*/
        return "{assignedGroup} ";
    }
    

    public EpamTicketDAO getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public Set<?> getParams() {
        return getUserService().getAllUserGroupsForUser(getUserService().getCurrentUser());
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
