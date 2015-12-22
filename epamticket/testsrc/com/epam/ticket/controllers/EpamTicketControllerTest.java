package com.epam.ticket.controllers;

import com.epam.dto.EpamCustomerEvent;
import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamNewTicket;
import com.epam.dto.EpamTicket;
import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.dto.TicketCounterHolder;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
   
    private static final Logger LOG = Logger.getLogger(EpamTicketControllerTest.class);

    public static final String BASE_URL = "/v1/tickets/";
    public static final String SEARCH_CRITERIA_PARAMS = "?priority=High&state=Open&sortReverse=true";
    public static final String TICKET_COUNT_URL = "ticketCount";
    public static final String CONFIG_URL = "config";
    public static final String UNEXPECTED_RESPONSE_BODY = "Unexpected response body";
    private static final String TICKET_ID = "ticketId";
    private static final String CLOSED = "Closed";
    private static final String COMMENT = "comment";

    private static final int TOTAL_TICKETS_COUNT = 10;

    private MockMvc mockMvc;

    private EpamNewTicket epamNewTicket;
    private EpamTicket epamTicket;
    private EpamCustomerEvent epamCustomerEvent;
    private TicketCounterHolder ticketCounterHolder;
    private EpamTicketsFilterCriteria criteria;
    private EpamTicketsFilter filter;
    private EpamFrontConfig filterConfig;
    private List<EpamTicket> epamTickets;

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

        epamTickets = new ArrayList<>();
        epamTickets.add(epamTicket);
        
        ticketCounterHolder = new TicketCounterHolder();
        ticketCounterHolder.setTotal(TOTAL_TICKETS_COUNT);
        
        Set<EpamTicketsFilterCriteria> criterias = new HashSet<>();
        criteria = new EpamTicketsFilterCriteria("medium", "Medium", "", false);
        criteria.setCount(10);
        criterias.add(criteria);

        Set<EpamTicketsFilter> filters = new HashSet<>();
        filter = new EpamTicketsFilter("priority", "PRIORITY");
        filter.setCriterias(criterias);
        filterConfig = new EpamFrontConfig();
        filterConfig.setAvailableFilters(filters);
    }

    @Test
    public void shouldAddAndReturnNewTicket() throws Exception {

        ArgumentCaptor<EpamTicket> ticketArg = ArgumentCaptor.forClass(EpamTicket.class);
        ArgumentCaptor<EpamCustomerEvent> eventArg = ArgumentCaptor.forClass(EpamCustomerEvent.class);
        doReturn(epamNewTicket.getNewTicket()).when(defaultEpamTicketFacadeMock)
                .addTicket(ticketArg.capture(), eventArg.capture());

        MvcResult response = mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
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
                .contentType(APPLICATION_JSON))
                .andExpect(content().string(toJsonString(epamTicket)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamTicket), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getTicketById(ticketId);
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
    public void shouldReturnTicketByCriteria() throws Exception {

        ArgumentCaptor<Map> searchArg = ArgumentCaptor.forClass(Map.class);
        doReturn(epamTickets).when(defaultEpamTicketFacadeMock).getTicketsByCriteria(searchArg.capture());

        MvcResult response = mockMvc.perform(get(BASE_URL + SEARCH_CRITERIA_PARAMS)
                .contentType(APPLICATION_JSON))
                .andExpect(content().string(toJsonString(epamTickets)))
                .andExpect(status().isOk())
                .andReturn();

        Map searchCriteria = searchArg.getAllValues().get(0);
        assertTrue("Priority criteria should be present", searchCriteria.containsKey("priority"));
        assertTrue("State criteria should be present", searchCriteria.containsKey("state"));
        assertEquals(UNEXPECTED_RESPONSE_BODY,
                toJsonString(epamTickets), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getTicketsByCriteria(searchArg.getValue());
    }

    @Test
    public void shouldReturnTotalTicketsCount() throws Exception {

        doReturn(TOTAL_TICKETS_COUNT).when(defaultEpamTicketFacadeMock).getTotalTicketCount();
        
        MvcResult response = mockMvc.perform(get(BASE_URL + TICKET_COUNT_URL)
            .contentType(APPLICATION_JSON))
            .andExpect(content().string(toJsonString(ticketCounterHolder)))
            .andExpect(status().isOk())
            .andReturn();
        
        assertEquals(UNEXPECTED_RESPONSE_BODY, 
                toJsonString(ticketCounterHolder), response.getResponse().getContentAsString());
        verify(defaultEpamTicketFacadeMock, times(1)).getTotalTicketCount();
    }
    
    @Test
    public void shouldReturnFilterConfigWithCounters() throws Exception {
       doReturn(filterConfig).when(defaultEpamTicketFacadeMock).getFrontConfigWithCounters();
        
       MvcResult response = mockMvc.perform(get(BASE_URL + CONFIG_URL)
           .contentType(APPLICATION_JSON))
           .andExpect(content().string(toJsonString(filterConfig)))
           .andExpect(status().isOk())
           .andReturn();
       
       assertEquals(UNEXPECTED_RESPONSE_BODY, 
               toJsonString(filterConfig), response.getResponse().getContentAsString());
       verify(defaultEpamTicketFacadeMock, times(1)).getFrontConfigWithCounters();
        
    }
    
    protected String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
