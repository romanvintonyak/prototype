package com.epam.ticket.facades;

import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;
@IntegrationTest
@ContextConfiguration("classpath:epamticket-spring.xml")
public class DefaultEpamTicketFacadeTest {

    @Resource
    private DefaultEpamTicketFacade ticketFacade;


    @Test
    public void testGetTicketListByAgent() throws Exception {
//        EmployeeModel employeeModel = new EmployeeModel();
//        employeeModel.setName("UserName");
//        List<CsTicketModel> csTicketModelist = ticketFacade.getTicketListByAgent(employeeModel);
//        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-");
//        System.out.println("Size = "+csTicketModelist.size());
//        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-");

    }
}