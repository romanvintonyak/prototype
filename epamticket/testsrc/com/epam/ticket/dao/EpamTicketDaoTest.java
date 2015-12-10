package com.epam.ticket.dao;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketDaoTest {

    public static final String UNEXPECTED_RESULT = "Unexpected result";

    private EpamTicketDAO epamTicketDao;

    @Mock
    private FlexibleSearchService mockFlexibleSearchService;

    @Mock
    private SearchResult mockSearchResult;

    @Before
    public void setUp() throws Exception {
        epamTicketDao = new EpamTicketDAO();
        epamTicketDao.setFlexibleSearchService(mockFlexibleSearchService);
    }

    @Test
    public void shouldReturnTotalTicketCount() {
        //given
        String query = "SELECT {pk} FROM {CsTicket}";
        Integer fakeResult = 42;

        doReturn(mockSearchResult).when(mockFlexibleSearchService).search(query);
        doReturn(fakeResult).when(mockSearchResult).getTotalCount();
        //when
        Integer result = epamTicketDao.getTotalTicketCount();
        //then
        verify(mockFlexibleSearchService, times(1)).search(query);
        verify(mockSearchResult, times(1)).getTotalCount();
        assertEquals(UNEXPECTED_RESULT, fakeResult, result);
    }
}
