package com.epam.ticket.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.ticket.dao.counters.impl.DefaultAgentCategoryCounterStrategy;
import com.epam.ticket.data.EpamTicketFrontFilter;
import com.epam.ticket.data.TicketCountsResult;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.session.SessionService;

public class TicketsCountDaoIntegrationTest extends ServicelayerTransactionalTest{
    
    private static Logger LOG = Logger.getLogger(TicketsCountDaoIntegrationTest.class);
    
    @Resource
    private FlexibleSearchService flexibleSearchService;
    @Resource
    private SessionService sessionService;


    @Resource
    EpamTicketDAO epamTicketDao;
    
//    @Resource
//    DefaultAgentCategoryCounterStrategy defaultAgentCategoryCounterStrategy;
    
    @BeforeClass
    public static void setUp() {
        de.hybris.platform.util.Config.setParameter("db.log.active", "true");
        User u = UserManager.getInstance().getUserByLogin( "csagent" );
        JaloSession js = JaloSession.getCurrentSession(); 
        js.setUser(u);
    }
    
    @Test
    public void getTicketCounts_shouldReturnFilters() {
        
        TicketCountsResult result = epamTicketDao.getTicketCounts();
        assertNotNull("TicketCountsResult should not be NULL", result);
        
        Set<EpamTicketFrontFilter> filters = result.getFilters();
        assertTrue("Filters must be present", filters.size() > 0);
    }
    
//    @Test
//    public void defaultAgentCounterStrategy_ShouldFindTicketsAssignedToCSAGENT() {
//        LOG.info(JaloSession.getCurrentSession().getUser().getName());
//        Map<String, Integer> assinedToCSAGENT = defaultAgentCategoryCounterStrategy.countCategory("");
//        assertNotEquals("Assined tickets count can't be 0", 0, assinedToCSAGENT.get("Assigned to me").intValue() );
//    }
}
