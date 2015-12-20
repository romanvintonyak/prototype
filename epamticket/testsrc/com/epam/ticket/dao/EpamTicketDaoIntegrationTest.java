package com.epam.ticket.dao;

import com.epam.dto.EpamFrontConfig;
import com.epam.ticket.services.EpamTicketService;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import static org.junit.Assert.assertFalse;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.ticket.model.CsTicketModel;

@IntegrationTest
@ContextConfiguration("/epamticket-spring.xml")
public class EpamTicketDaoIntegrationTest extends ServicelayerTransactionalTest {
    
    private static final Logger LOG = Logger.getLogger(EpamTicketDaoIntegrationTest.class); 
    
    @Resource
    FlexibleSearchService flexibleSearchService;
    @Resource
    EpamTicketService epamTicketService;
    @Resource
    UserService userService;
    @Resource
    SessionService sessionService;
    
/*    @SuppressWarnings("rawtypes")
    @Test
    public void shouldReturnWorking_CountTicketsQuery() {
        FlexibleSearchQuery query = defaultEnumFilterStrategyImpl.buildCountTicketsQuery("priority");

        final SearchResult<List> searchResult = flexibleSearchService.search(query);
        final List<List> categoryStates = searchResult.getResult();
        assertFalse("Should return working count query", categoryStates.isEmpty());
        
        for (final Iterator<List> iter = categoryStates.iterator(); iter.hasNext(); ) {
            List row = iter.next();
            LOG.info((String) row.get(0) + ":" + (Integer) row.get(1));
        }
    }
*/    
    @Test
    public void shouldCountFilteredTickets() {
        EpamFrontConfig config = epamTicketService.getFrontConfigWithCounters();
        assertFalse("Config should contain filters", config.getAvailableFilters().isEmpty());
        assertFalse("Config should contain filter categories", config.getAvailableFilters().iterator().next().getCriterias().isEmpty());
        LOG.info(config);
        
        String groupCriteria = "Unassigned";
        String queryUnnassigned = "SELECT count({c:PK}) FROM {CsTicket AS c} WHERE {c.assignedGroup} IS NULL";
        String queryMy = "SELECT count({c.pk}) FROM { CsTicket AS c } WHERE {c.assignedgroup} IN" 
                + "({{ SELECT targetpk FROM { User AS u JOIN PrincipalGroupRelation AS p ON {u.pk} = sourcepk}"
                + "where {u.pk} = (?session.user) }})";
        String queryOther = "SELECT count({c.pk}) FROM { CsTicket AS c } WHERE {c.assignedgroup} NOT IN" 
                + "({{ SELECT targetpk FROM { User AS u JOIN PrincipalGroupRelation AS p ON {u.pk} = sourcepk}"
                + "where {u.pk} = (?session.user) }})";
        
        de.hybris.platform.util.Config.setParameter("db.log.active", "true");
        User u = UserManager.getInstance().getUserByLogin( "csagent" );
        JaloSession js = JaloSession.getCurrentSession(); 
        js.setUser(u);
//        userService.setCurrentUser(userService.getUserForUID("csagent"));
        Set<UserGroupModel> groups = userService.getAllUserGroupsForUser(userService.getCurrentUser());
        
        for (UserGroupModel group : groups) {
            LOG.info(group.getDisplayName());
        }
        FlexibleSearchQuery query = new FlexibleSearchQuery("select {c:PK} from {CsTicket As c} where {c.assignedGroup} not in (?groups)");
        query.addQueryParameter("groups", groups);
        SearchResult<CsTicketModel> searchResult = flexibleSearchService.search(query);
        List<CsTicketModel> tickets = searchResult.getResult();
        for (CsTicketModel ticket : tickets) {
            LOG.info(ticket.getComments());
        }
    }
    
}
