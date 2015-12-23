package com.epam.ticket.dao;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketDaoTest {

    public static final String UNEXPECTED_RESULT = "Unexpected result";

    private EpamTicketDAO epamTicketDao;
    private EpamTicketsFilter filter;

    @Mock
    private FlexibleSearchService mockFlexibleSearchService;

    @Mock
    private SearchResult<List<Integer>> mockSearchResult;
    
    @Before
    public void setUp() throws Exception {
        final String criteriaName = "medium";
        final String criteriaDisplayName = "Medium";
        final String filterDisplayName = "PRIORITY";
        final String filterName = "priority";

        epamTicketDao = new EpamTicketDAO();
        epamTicketDao.setFlexibleSearchService(mockFlexibleSearchService);
 
        Set<EpamTicketsFilterCriteria> criterias = new HashSet<>();
        EpamTicketsFilterCriteria criteria;

        criteria = new EpamTicketsFilterCriteria(criteriaName, criteriaDisplayName, "", false);
        criteria.setCount(10);
        criterias.add(criteria);

        filter = new EpamTicketsFilter(filterName, filterDisplayName);
        filter.setCriterias(criterias);

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
