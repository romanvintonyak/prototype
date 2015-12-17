package com.epam.ticket.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.dto.EpamFrontConfig;
import com.epam.dto.EpamTicketsFilterConfig;
import com.epam.dto.EpamTicketsFilterCriteria;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketDaoTest {

    public static final String UNEXPECTED_RESULT = "Unexpected result";

    private EpamTicketDAO epamTicketDao;
    private EpamTicketsFilterCriteria criteria;
    private EpamTicketsFilterConfig filter;
    private EpamFrontConfig filterConfig;

    @Mock
    private FlexibleSearchService mockFlexibleSearchService;

    @Mock
    private SearchResult mockSearchResult;
    
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

        criteria = new EpamTicketsFilterCriteria(criteriaName, criteriaDisplayName, "", "");
        criteria.setCount(10);
        criterias.add(criteria);

        filter = new EpamTicketsFilterConfig(filterName, filterDisplayName);
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
    
    @Test
    public void shouldReturnFilteredTicketsCounts() {
//        epamTicketDao.setAvailableFilters(availableFilters);
//        when(filterQueryExecuter.executeFilter(any(),any()))
//        EpamFilteredTicketsCounts result = epamTicketDao.getFilteredTicketsCounts();
       assertTrue(false);
        

    }
}
