package com.epam.ticket.facades;

import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import de.hybris.bootstrap.annotations.IntegrationTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
@IntegrationTest
@ContextConfiguration("/epamticket-spring.xml")
public class DefaultEpamTicketFacadeIntegrationTest {

    @Resource
    private DefaultEpamTicketFacade ticketFacade;


    @Test
    public void testGetTicketListByAgent() throws Exception {
//        EmployeeModel employeeModel = new EmployeeModel();
//        employeeModel.setName("UserName");
//        Integer ticketcount = ticketFacade.getTotalTicketCount();
//        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-");
//        System.out.println("Size = "+ticketcount);
//        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-");

    }
}