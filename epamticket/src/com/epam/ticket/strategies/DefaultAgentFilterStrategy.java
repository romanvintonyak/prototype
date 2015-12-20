package com.epam.ticket.strategies;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.strategies.FilterStrategy;
import com.epam.ticket.dao.EpamTicketDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

public class DefaultAgentFilterStrategy  implements FilterStrategy{

    private EpamTicketDAO ticketDao;
    private UserService userService;

    @Override
    public String getType() {
        return "AGENT";
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
    public String buildFilterSubquery(String categoryName) {
        return "{assignedAgent} ";
    }

    public EpamTicketDAO getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public Set<?> getParams() {
        Set<UserModel> users = new HashSet<>(); 
        users.add(getUserService().getCurrentUser());
        return users;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


}
