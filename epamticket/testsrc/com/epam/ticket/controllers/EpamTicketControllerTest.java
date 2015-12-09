package com.epam.ticket.controllers;

import com.epam.ticket.data.EpamTicket;
import com.epam.ticket.facades.impl.DefaultEpamTicketFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({/*"epamticket-web-spring.xml",*/ "/epamticket-spring-test.xml"})
@WebAppConfiguration(value = "web/webroot")
public class EpamTicketControllerTest {

    private MockMvc mockMvc;
    private EpamTicket epamTicket;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultEpamTicketFacade defaultEpamTicketFacadeMock;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        epamTicket = new EpamTicket();
        epamTicket.setHeadline("headline");
        epamTicket.setCategory("category");
        epamTicket.setPriority("priority");
    }

    @Test
    public void testAddTicket() throws Exception {

        /*mockMvc.perform(post("http://localhost:9001/epamticket/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(epamTicket)))
                .andExpect(status().isOk());

        verify(defaultEpamTicketFacadeMock, times(1)).addTicket(epamTicket);*/
    }

    protected String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
