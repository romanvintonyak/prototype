package com.epam.ticket.dao;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketDaoTest {

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
        doReturn(mockSearchResult).when(mockFlexibleSearchService).search("SELECT {pk} FROM {CsTicket}");
        //when
        int result = epamTicketDao.getTotalTicketCount();
    }

    /*public Integer getTotalTicketCount() {
        SearchResult result = getFlexibleSearchService().search("SELECT {pk} FROM {CsTicket}");
        int totalCount = result.getTotalCount();
        LOG.info("Ticket count:" + totalCount);
        return totalCount;
    }*/
}
