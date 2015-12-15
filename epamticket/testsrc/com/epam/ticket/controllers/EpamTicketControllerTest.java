package com.epam.ticket.controllers;

import com.epam.ticket.data.EpamCustomerEvent;
import com.epam.ticket.data.EpamNewTicket;
import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/webroot/WEB-INF/springmvc-servlet.xml", "/epamticket-spring-test.xml"})
@WebAppConfiguration
public class EpamTicketControllerTest {

    public static final String BASE_URL = "/v1/tickets/";
    public static final String UNEXPECTED_RESPONSE_BODY = "Unexpected response body";

    private MockMvc mockMvc;

    private EpamNewTicket epamNewTicket;
    private EpamTicket epamTicket;
    private EpamCustomerEvent epamCustomerEvent;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultEpamTicketFacade defaultEpamTicketFacadeMock;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        epamTicket = new EpamTicket();
        epamTicket.setHeadline("headline");
        epamTicket.setCategory("category");
        epamTicket.setPriority("priority");

        epamCustomerEvent = new EpamCustomerEvent();
        epamCustomerEvent.setReason("reason");
        epamCustomerEvent.setInterventionType("interventionType");
        epamCustomerEvent.setText("text");

        epamNewTicket = new EpamNewTicket();
        epamNewTicket.setNewTicket(epamTicket);
        epamNewTicket.setCreationEvent(epamCustomerEvent);
    }

    @Test
    public void shouldAddAndReturnNewTicket() throws Exception {

        ArgumentCaptor<EpamTicket> ticketArg = ArgumentCaptor.forClass(EpamTicket.class);
        ArgumentCaptor<EpamCustomerEvent> eventArg = ArgumentCaptor.forClass(EpamCustomerEvent.class);
        doReturn(epamNewTicket.getNewTicket()).when(defaultEpamTicketFacadeMock)
                .addTicket(ticketArg.capture(), eventArg.capture());

        MvcResult response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(epamNewTicket)))
                .andExpect(content().string(toJsonString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamNewTicket.getNewTicket()), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1))
                .addTicket(ticketArg.getValue(), eventArg.getValue());
    }

    @Test
    public void shouldReturnTicketById() throws Exception {

        String ticketId = "1";
        doReturn(epamTicket).when(defaultEpamTicketFacadeMock).getTicketById(ticketId);

        MvcResult response = mockMvc.perform(get(BASE_URL + ticketId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJsonString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamTicket), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getTicketById(ticketId);
    }

    protected String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
