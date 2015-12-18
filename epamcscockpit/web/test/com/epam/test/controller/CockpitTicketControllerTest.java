package com.epam.test.controller;

import com.epam.dto.EpamCustomerEvent;
import com.epam.dto.EpamNewTicket;
import com.epam.dto.EpamTicket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/webroot/WEB-INF/springmvc-servlet.xml", "/epamcscockpit-spring-test.xml"})
@WebAppConfiguration
public class CockpitTicketControllerTest {

    private static final String TICKET_ID = "0007000";
    private static final String BASE_URL = "/rest/tickets/";
    private static final String TICKET_COUNT = "ticketCount";

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static EpamTicket epamTicket;
    private static EpamNewTicket epamNewTicket;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    static {
        initTicket();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldReturnListOfTickets() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andReturn();
        Assert.notNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnTicketBuId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + TICKET_ID)).andExpect(status().isOk()).andReturn();
        Assert.notNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnTicketCount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + TICKET_COUNT)).andExpect(status().isOk()).andReturn();
        Assert.notNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldCreateTicketAndReturnThisTicket() throws Exception {
        MvcResult response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(epamNewTicket)))
                .andExpect(status().isOk())
                .andReturn();
        Assert.notNull(response.getResponse().getContentAsString());

    }

    private static void initTicket() {
        epamTicket = new EpamTicket();
        epamTicket.setHeadline("headline");
        epamTicket.setCategory("category");
        epamTicket.setPriority("priority");

        EpamCustomerEvent epamCustomerEvent;

        epamCustomerEvent = new EpamCustomerEvent();
        epamCustomerEvent.setReason("reason");
        epamCustomerEvent.setInterventionType("interventionType");
        epamCustomerEvent.setText("text");

        epamNewTicket = new EpamNewTicket();
        epamNewTicket.setNewTicket(epamTicket);
        epamNewTicket.setCreationEvent(epamCustomerEvent);
    }

}
