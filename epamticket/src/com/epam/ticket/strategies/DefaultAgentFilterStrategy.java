package com.epam.ticket.strategies;

import com.epam.ticket.dao.EpamTicketDAO;

import java.util.HashSet;
import java.util.Set;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

public class DefaultAgentFilterStrategy extends DefaultInNotInFilterStrategy <UserModel> {

    private EpamTicketDAO ticketDao;
    private UserService userService;

    @Override
    protected Set<UserModel> getParams() {
        Set<UserModel> params = new HashSet<>();
        params.add(getUserService().getCurrentUser());
        return params;
    }

    @Override
    protected String getQueryWhereParam() {
        return "{assignedAgent} ";
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
