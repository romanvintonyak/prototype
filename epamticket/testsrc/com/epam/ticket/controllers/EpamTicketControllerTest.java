package com.epam.ticket.controllers;

import com.epam.dto.EpamCustomerEvent;
import com.epam.dto.EpamFilteredTicketsCounts;
import com.epam.dto.EpamNewTicket;
import com.epam.dto.EpamTicket;
import com.epam.dto.TicketCounterHolder;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/webroot/WEB-INF/springmvc-servlet.xml", "/epamticket-spring-test.xml"})
@WebAppConfiguration
public class EpamTicketControllerTest {

    public static final String BASE_URL = "/v1/tickets/";
    public static final String UNEXPECTED_RESPONSE_BODY = "Unexpected response body";
    private static final String TICKET_ID = "ticketId";
    private static final String CLOSED = "Closed";
    private static final String COMMENT = "comment";
    private static final Integer COUNT = 42;

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
        createTicket();
        createEvent();
        createNewTicket();
    }

    @Test
    public void shouldGetTicketListByCriteria() throws Exception {
        //given
        doReturn(epamTicket).when(defaultEpamTicketFacadeMock).getTicketById(TICKET_ID);
        //when
        MvcResult response = mockMvc.perform(get(BASE_URL + TICKET_ID)
                .contentType(APPLICATION_JSON))
                .andExpect(content().string(toJsonString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();
        //then
        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamTicket), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getTicketById(TICKET_ID);
    }


    @Test
    public void shouldAddAndReturnNewTicket() throws Exception {
        //given
        ArgumentCaptor<EpamTicket> ticketArg = ArgumentCaptor.forClass(EpamTicket.class);
        ArgumentCaptor<EpamCustomerEvent> eventArg = ArgumentCaptor.forClass(EpamCustomerEvent.class);
        doReturn(epamNewTicket.getNewTicket()).when(defaultEpamTicketFacadeMock)
                .addTicket(ticketArg.capture(), eventArg.capture());
        //when
        MvcResult response = mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(toJsonString(epamNewTicket)))
                .andExpect(content().string(toJsonString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();
        //then
        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamNewTicket.getNewTicket()), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1))
                .addTicket(ticketArg.getValue(), eventArg.getValue());
    }

    @Test
    public void shouldReturnTicketById() throws Exception {
        //given
        doReturn(epamTicket).when(defaultEpamTicketFacadeMock).getTicketById(TICKET_ID);
        //when
        MvcResult response = mockMvc.perform(get(BASE_URL + TICKET_ID)
                .contentType(APPLICATION_JSON))
                .andExpect(content().string(toJsonString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();
        //then
        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamTicket), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getTicketById(TICKET_ID);
    }

    @Test
    public void shouldCloseTicketWhenItPossible() throws Exception {
        //given
        String changeJsonString = "{\"newState\":\"Closed\",\"comment\":\"comment\"}";
        when(defaultEpamTicketFacadeMock.changeTicketState(TICKET_ID, CLOSED, COMMENT)).thenReturn(epamTicket);
        //when
        MvcResult response = mockMvc.perform(put(BASE_URL + TICKET_ID + "/changestate")
                .contentType(APPLICATION_JSON)
                .content(changeJsonString))
                .andExpect(content().string(toJsonString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();
        //then
        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamTicket), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1))
                .changeTicketState(TICKET_ID, CLOSED, COMMENT);
    }

    @Test
    public void shouldGetTotalCountTickets() throws  Exception{
        //given
        TicketCounterHolder counterHolder = new TicketCounterHolder();
        counterHolder.setTotal(COUNT);
        when(defaultEpamTicketFacadeMock.getTotalTicketCount()).thenReturn(COUNT);
        //when
        MvcResult response = mockMvc.perform(get(BASE_URL + "/ticketCount")
                .contentType(APPLICATION_JSON))
                .andExpect(content().string(toJsonString(counterHolder)))
                .andExpect(status().isOk())
                .andReturn();
        //then
        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(counterHolder), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getTotalTicketCount();
    }

    @Test
    public void shouldGetFilteredCountTickets() throws  Exception{
        //given
        EpamFilteredTicketsCounts counterHolder = new EpamFilteredTicketsCounts();
        when(defaultEpamTicketFacadeMock.getFilteredTicketsCounts()).thenReturn(counterHolder);
        //when
        MvcResult response = mockMvc.perform(get(BASE_URL + "/filteredTicketsCounts")
                .contentType(APPLICATION_JSON))
                .andExpect(content().string(toJsonString(counterHolder)))
                .andExpect(status().isOk())
                .andReturn();
        //then
        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(counterHolder), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getFilteredTicketsCounts();
    }

    protected String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
    private void createNewTicket() {
        epamNewTicket = new EpamNewTicket();
        epamNewTicket.setNewTicket(epamTicket);
        epamNewTicket.setCreationEvent(epamCustomerEvent);
    }

    private void createEvent() {
        epamCustomerEvent = new EpamCustomerEvent();
        epamCustomerEvent.setReason("reason");
        epamCustomerEvent.setInterventionType("interventionType");
        epamCustomerEvent.setText("text");
    }

    private void createTicket() {
        epamTicket = new EpamTicket();
        epamTicket.setHeadline("headline");
        epamTicket.setCategory("category");
        epamTicket.setPriority("priority");
    }

}
