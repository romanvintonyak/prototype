package com.epam.ticket.dao;

import static org.junit.Assert.*;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;

public class TicketsCountDaoIntegrationTest extends ServicelayerTransactionalTest{
    
    @SuppressWarnings("unused")
    private static Logger LOG = Logger.getLogger(TicketsCountDaoIntegrationTest.class);
    
    @Resource
    EpamTicketDAO epamTicketDao;
    
    @Test
    public void getTicketCounts_shouldReturnCounts() {
        
        EpamTicketDAO.TicketCountsResult result = epamTicketDao.getTicketCounts();
        assertNotNull("TicketCountsResult should not be NULL", result);
        
        Map<String, Integer> priorityCounters = result.getFilterCategories().get("priority");
        assertNotNull("Priority counters must be present", priorityCounters);
        assertEquals("Priority states number must be equals 3", 3, priorityCounters.size());
    }

}
