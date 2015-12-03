package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicket;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.user.EmployeeModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.StubLocaleProvider;
import de.hybris.platform.servicelayer.internal.model.impl.ModelValueHistory;
import de.hybris.platform.servicelayer.model.ItemContextBuilder;
import de.hybris.platform.servicelayer.model.ItemModelInternalContext;
import de.hybris.platform.servicelayer.model.strategies.DefaultFetchStrategy;
import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.events.model.CsTicketEventModel;
import de.hybris.platform.ticket.model.CsAgentGroupModel;
import de.hybris.platform.ticket.model.CsTicketModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/*
* Parent converter includes related converters according to model graph, so that testing this is enough
*/
@UnitTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/epamticket-spring-test.xml")
public class EpamTicketConvertorTest {

    public static final String UNEXPECTED_CONVERTED_VALUE = "Unexpected converted value";

    public static final String TICKET_ID = "ticket_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String ORDER_CODE = "order_code";
    public static final CsTicketCategory CATEGORY = CsTicketCategory.PROBLEM;
    public static final CsTicketPriority PRIORITY = CsTicketPriority.HIGH;
    public static final CsTicketState STATE = CsTicketState.OPEN;
    public static final String AGENT_NAME = "agent_name";
    public static final String GROUP_NAME = "group_name";
    public static final String HEADLINE = "headline";
    public static final Date CREATION_TIME = new Date();
    public static final Date MODIFY_TIME = new Date();

    private ItemModelInternalContext ctx;

    @Autowired
    private EpamTicketConverter converter;

    @Autowired
    private DateFormat dateFormatter;

    @Before
    public void setUp() throws Exception {

        ItemContextBuilder builder = new ItemContextBuilder();
        //builder.setPk(pk);
        //builder.setItemType(itemType);
        //builder.setTenantID(TENANT_ID);
        builder.setValueHistory(new ModelValueHistory());
        builder.setFetchStrategy(new DefaultFetchStrategy());
        builder.setLocaleProvider(new StubLocaleProvider(Locale.ENGLISH));
        //builder.setDynamicAttributesProvider(new MockDynamicAttributesProvider(dynamicAttributes, dynamicLocAttributes));
        //builder.setAttributeProvider(new MockAttributeProvider(attributes, locAttributes));
        ctx = builder.build();
    }

    @Test(expected = NullPointerException.class) //empty list of emails and set of changes with initial change should be set at least
    public void testFailConvert() {
        CsTicketModel source = new CsTicketModel();
        converter.convert(source);
    }

    @Test
    public void testSuccessConvert() throws Exception {

        CsTicketModel source = new CsTicketModel();
        source.setTicketID(TICKET_ID);

        UserModel customer = new UserModel(ctx);
        customer.setUid(CUSTOMER_ID);
        customer.setName(CUSTOMER_NAME);
        source.setCustomer(customer);

        AbstractOrderModel order = new AbstractOrderModel();
        order.setCode(ORDER_CODE);
        source.setOrder(order);

        source.setCategory(CATEGORY);
        source.setPriority(PRIORITY);
        source.setState(STATE);

        EmployeeModel agent = new EmployeeModel(ctx);
        agent.setName(AGENT_NAME);
        source.setAssignedAgent(agent);

        CsAgentGroupModel group = new CsAgentGroupModel(ctx);
        group.setName(GROUP_NAME);
        source.setAssignedGroup(group);

        source.setHeadline(HEADLINE);
        source.setCreationtime(CREATION_TIME);
        source.setModifiedtime(MODIFY_TIME);

        Field eventsField = ReflectionUtils.findField(CsTicketModel.class, "_events");
        ReflectionUtils.makeAccessible(eventsField);
        // CsTicketEventModel conversion being tested in separate test, so that we use an empty list of events
        ReflectionUtils.setField(eventsField, source, new ArrayList<CsTicketEventModel>());

        EpamTicket target = converter.convert(source);

        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getTicketId(), TICKET_ID);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getCustomerUid(), CUSTOMER_ID);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getCustomerDisplayName(), CUSTOMER_NAME);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getOrder(), ORDER_CODE);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getCategory(), CATEGORY.getCode());
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getPriority(), PRIORITY.getCode());
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getState(), STATE.getCode());
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getAssignedAgent(), AGENT_NAME);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getAssignedGroup(), GROUP_NAME);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getHeadline(), HEADLINE);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getCreationTime(), dateFormatter.format(CREATION_TIME));
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getModifyTime(), dateFormatter.format(MODIFY_TIME));
    }
}