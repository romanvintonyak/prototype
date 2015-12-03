package com.epam.ticket.dao;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;

public class TicketsCountDaoIntegrationTest extends ServicelayerTransactionalTest{
    
    private static Logger LOG = Logger.getLogger(TicketsCountDaoIntegrationTest.class);
    
    @Resource
    EpamTicketDAO epamTicketDao;
    
    @Test
    public void getTicketCounts_shouldReturnCounts() {
        
        EpamTicketDAO.TicketCountsResult result = epamTicketDao.getTicketCounts();
        assertNotNull("TicketCountsResult should not be NULL", result);
        assertTrue("Ticket count should not be null (check if there is a test data ina DB)", 
                result.getpHigh() != 0);
    }

}
