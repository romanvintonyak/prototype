package com.epam.ticket.services.impl;

import com.epam.ticket.dao.EpamTicketDAO;
import com.epam.ticket.facades.EpamTicketSearchCriteria;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.ticket.model.CsTicketModel;
import de.hybris.platform.ticket.service.TicketBusinessService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@UnitTest
public class DefaultEpamTicketServiceTest {

    private List<CsTicketModel> expectedTicketModels;
    private CsTicketModel expectedTicketModel;

    private EpamTicketDAO mockTicketDao;
    private TicketBusinessService mockTicketBusinessService;
    private DefaultEpamTicketService ticketService;

    @Before
    public void setUp() {
        mockTicketDao = mock(EpamTicketDAO.class);
        ticketService = new DefaultEpamTicketService();
        ticketService.setTicketDao(mockTicketDao);
        ticketService.setTicketBusinessService(mockTicketBusinessService);

        expectedTicketModel = new CsTicketModel();
    }

    @Test
    public void shouldNotAddTicketWithoutCategoryAndPriority() {

        /*CsTicketModel dummyWrongTicket = new CsTicketModel();
        CsCustomerEventModel
        doThrow(ModelSavingException.class).when(mockTicketDao).addTicket(dummyWrongTicket);

        try {
            ticketService.addTicket(dummyWrongTicket);
        } catch (ModelSavingException expected) {
        }

        verify(mockTicketDao, times(1)).addTicket(dummyWrongTicket);*/
    }

    @Test
    public void shouldAddTicketWithCategoryAndPriority() {

        /*CsTicketModel dummyNormalTicket = new CsTicketModel();
        dummyNormalTicket.setPriority(CsTicketPriority.HIGH);
        dummyNormalTicket.setCategory(CsTicketCategory.INCIDENT);
        doNothing().when(mockTicketDao).addTicket(dummyNormalTicket);

        ticketService.addTicket(dummyNormalTicket);

        verify(mockTicketDao, times(1)).addTicket(dummyNormalTicket);*/
    }

    @Test
    public void shouldReturnListOfTicketModelsByCriteria() throws Exception {

        expectedTicketModels = new ArrayList<>();
        expectedTicketModels.add(expectedTicketModel);
        EpamTicketSearchCriteria dummyCriteria = new EpamTicketSearchCriteria();
        when(mockTicketDao.findTicketsByCriteria(dummyCriteria)).thenReturn(expectedTicketModels);

        List<CsTicketModel> actualTicketModels = ticketService.getTicketsByCriteria(dummyCriteria);

        assertEquals("The lists of expected and actual ticket models should be the same",
                expectedTicketModels, actualTicketModels);
        verify(mockTicketDao, times(1)).findTicketsByCriteria(dummyCriteria);
    }

    @Test
    public void shouldReturnTicketModelByAgentId() throws Exception {

        String dummyTicketId = "1";
        when(mockTicketDao.getTicketById(dummyTicketId)).thenReturn(expectedTicketModel);

        CsTicketModel actualTicketModel = ticketService.getTicketById(dummyTicketId);

        assertEquals("Expected and actual ticket models should be the same", expectedTicketModel, actualTicketModel);
        verify(mockTicketDao, times(1)).getTicketById(dummyTicketId);
    }
}