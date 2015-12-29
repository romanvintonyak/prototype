package com.epam.ticket.dao;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketDaoTest {

    public static final String UNEXPECTED_RESULT = "Unexpected result";
    public static final String QUERY = "SELECT {pk} FROM {CsTicket}";

    private EpamTicketDAO epamTicketDao;
    private EpamTicketsFilter filter;

    @Mock
    private FlexibleSearchService mockFlexibleSearchService;

    @Mock
    private SearchResult<List<Integer>> mockSearchResult;
    @Mock
    private SearchResult<List> searchResultList;
    @Mock
    private SearchResult<Object> searchResult;

    @Mock
    private FlexibleSearchQuery query;

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
        Integer fakeResult = 42;

        doReturn(mockSearchResult).when(mockFlexibleSearchService).search(QUERY);
        doReturn(fakeResult).when(mockSearchResult).getTotalCount();
        //when
        Integer result = epamTicketDao.getTotalTicketCount();
        //then
        verify(mockFlexibleSearchService, times(1)).search(QUERY);
        verify(mockSearchResult, times(1)).getTotalCount();
        assertEquals(UNEXPECTED_RESULT, fakeResult, result);
    }

    @Test
    public void shouldReturnTicketCountWithParams() {
        //given
        Map<String, Set<Object>> params = new TreeMap<>();
        ArgumentCaptor<FlexibleSearchQuery> argumentCaptor = ArgumentCaptor.forClass(FlexibleSearchQuery.class);
        when(mockFlexibleSearchService.search(argumentCaptor.capture())).thenReturn(searchResult);
        //when
        epamTicketDao.getTicketCountsWithQueryParams(QUERY, params);
        //then
        verify(mockFlexibleSearchService, times(1)).search(argumentCaptor.getValue());
        assertEquals(argumentCaptor.getValue().getQuery(), QUERY);
        assertEquals(argumentCaptor.getValue().getQueryParameters(), params);
    }

    @Test
    public void shouldReturnFilteredCounts() {
        String key = "key";
        Integer value = 42;
        List<List> result =  asList(asList(key, value));
        doReturn(searchResultList).when(mockFlexibleSearchService).search(query);
        doReturn(result).when(searchResultList).getResult();
        //when
        Map<String, Integer> filterCounts = epamTicketDao.getFilterCounts(query);
        //then
        assertEquals(filterCounts.get(key),value);
        verify(mockFlexibleSearchService, times(1)).search(query);

    }


}
