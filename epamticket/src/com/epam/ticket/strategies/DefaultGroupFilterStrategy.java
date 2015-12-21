package com.epam.ticket.strategies;

import com.epam.ticket.dao.EpamTicketDAO;

import java.util.Set;

import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.user.UserService;

public class DefaultGroupFilterStrategy extends DefaultInNotInFilterStrategy {

    private EpamTicketDAO ticketDao;
    private UserService userService;
    
    @Override
    protected Set<UserGroupModel> getParams() {
        return getUserService().getAllUserGroupsForUser(getUserService().getCurrentUser());
    }

    @Override
    protected String getQueryWhereParam() {
        return "{assignedGroup} ";
    }

    @Override
    public EpamTicketDAO getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(EpamTicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
}
