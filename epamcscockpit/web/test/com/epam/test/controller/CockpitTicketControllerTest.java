package com.epam.test.controller;

import com.epam.dto.EpamCustomerEvent;
import com.epam.dto.EpamNewTicket;
import com.epam.dto.EpamTicket;
import com.epam.dto.EpamTicketStateHolder;
import com.epam.dto.TicketCounterHolder;
import com.epam.test.helper.RestHelperMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/webroot/WEB-INF/springmvc-servlet.xml", "/epamcscockpit-spring-test.xml"})
@WebAppConfiguration
public class CockpitTicketControllerTest {

    private static final String TICKET_ID = "0007000";
    private static final String BASE_URL = "/rest/tickets/";
    private static final String TICKET_COUNT = "ticketCount";
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private EpamTicket epamTicket;
    private EpamTicketStateHolder stateHolder;
    private EpamNewTicket epamNewTicket;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestHelperMock restHelperMock;

    private MockMvc mockMvc;

    private TicketCounterHolder ticketCounterHolder;
    private List<EpamTicket> epamTicketList;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        initTicket();
        ticketCounterHolder = new TicketCounterHolder();
        epamTicketList = Collections.singletonList(epamTicket);
    }

    @Test
    public void shouldReturnListOfTickets() throws Exception {
        restHelperMock.setMockResponseDto(epamTicketList);
        MvcResult response = mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(epamTicketList)))
                .andExpect(status().isOk())
                .andReturn();
        Assert.notNull(response.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnTicketById() throws Exception {
        restHelperMock.setMockResponseDto(epamTicket);
        MvcResult response = mockMvc.perform(get(BASE_URL + TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();
        Assert.notNull(response.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnTicketCount() throws Exception {
        ticketCounterHolder.setTotal(100);
        restHelperMock.setMockResponseDto(ticketCounterHolder);
        MvcResult response = mockMvc.perform(get(BASE_URL + TICKET_COUNT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(ticketCounterHolder)))
                .andExpect(status().isOk())
                .andReturn();
        Assert.notNull(response.getResponse().getContentAsString());
    }

    @Test
    public void shouldCloseTicket() throws Exception {
        restHelperMock.setMockResponseDto(epamTicket);
        MvcResult response = mockMvc.perform(put(BASE_URL + TICKET_ID + "/changestate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(stateHolder)))
                .andExpect(status().isOk())
                .andReturn();
        Assert.notNull(response.getResponse().getContentAsString());
    }

    @Test
    public void shouldCreateTicketAndReturnThisTicket() throws Exception {
        restHelperMock.setMockResponseDto(epamTicket);
        MvcResult response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(epamNewTicket)))
                .andExpect(status().isOk())
                .andReturn();
        Assert.notNull(response.getResponse().getContentAsString());
    }

    private void initTicket() {

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

        stateHolder = new EpamTicketStateHolder();
        stateHolder.setNewState("Closed");
        stateHolder.setComment("Close ticket");
    }

}
