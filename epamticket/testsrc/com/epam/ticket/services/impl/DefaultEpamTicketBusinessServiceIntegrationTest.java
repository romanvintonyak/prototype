package com.epam.ticket.services.impl;

import com.epam.ticket.services.EpamTicketBusinessService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.ticket.enums.CsTicketState;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import de.hybris.platform.ticket.events.model.CsTicketChangeEventEntryModel;
import de.hybris.platform.ticket.jalo.AbstractTicketsystemTest;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketBusinessService;
import de.hybris.platform.ticket.service.TicketException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.annotation.Resource;
import java.util.Date;

import static de.hybris.platform.ticket.enums.CsTicketCategory.NOTE;
import static de.hybris.platform.ticket.enums.CsTicketPriority.LOW;
import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;

public class DefaultEpamTicketBusinessServiceIntegrationTest extends AbstractTicketsystemTest {

    public static final String CLOSED = "Closed";
    public static final String COMMENT = "comment";
    @Rule
    public ExpectedException thrown = none();
    @Resource
    private EpamTicketBusinessService epamTicketBusinessService;

    @Resource
    private TicketBusinessService ticketBusinessService;

    @Resource
    protected UserService userService;
    @Resource
    protected ModelService modelService;


    @Override
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
    }
    @Test
    public void shouldCreateTicket() {
        //given
        CsTicketModel ticket = prepareTicketWithEvents();
        //when
        CsTicketModel resultTicket = epamTicketBusinessService.setTicketState(ticket.getTicketID(), CLOSED, COMMENT);

    }

    @Test
    public void shouldCloseTicketWhen() throws TicketException {
        //given
        assertEquals(CsTicketState.CLOSED, resultTicket.getState());
        assertEquals(2, resultTicket.getEvents().size());
        assertEquals(COMMENT, resultTicket.getEvents().get(1).getText());
        CsTicketChangeEventEntryModel entry = resultTicket.getEvents().get(1).getEntries().iterator().next();
        assertEquals("Open", entry.getOldStringValue());
        assertEquals(CLOSED, entry.getNewStringValue());
    }

    @Test
    public void shouldNotCloseTicketWhenItAlreadyClosed() throws TicketException {
        //given
        CsTicketModel ticket = prepareTicketWithEvents();
        ticket.setState(CsTicketState.CLOSED);

        thrown.expect(TicketException.class);
        thrown.expectMessage("The ticket must not have been previously updated before specifically changing the state");
        //when
       epamTicketBusinessService.setTicketState(ticket.getTicketID(), CLOSED, COMMENT);
        //given
    }

    private CsTicketModel prepareTicketWithEvents() {
        final String headline = "Ticket Headline";
        final String note = "Ticket Creation Notes";

        final OrderModel testOrder = testUser.getOrders().iterator().next();

        CsTicketModel csTicket = modelService.create(CsTicketModel.class);
        csTicket.setCustomer(testUser);
        csTicket.setOrder(testOrder);
        csTicket.setCategory(NOTE);
        csTicket.setPriority(LOW);
        csTicket.setAssignedAgent(adminUser);
        csTicket.setAssignedGroup(testGroup);
        csTicket.setHeadline(headline);
        csTicket.setCreationtime(new Date());

        final CsCustomerEventModel creationEvent = modelService.create(CsCustomerEventModel.class);
        creationEvent.setText(note);
        return ticketBusinessService.createTicket(csTicket, creationEvent);
    }
}