package com.epam.ticket.dao;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.ticket.strategies.DefaultEnumFilterStrategy;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.ticket.model.CsTicketModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketDaoFindTicketsTest {

    private EpamTicketDAO epamTicketDao;
    private Map<String, String[]> searchCriteria;
    private Set<EpamTicketsFilter> filters;
    private EpamTicketsFilter filter;
    private Set<EpamTicketsFilterCriteria> criterias;

    @Mock private FlexibleSearchService mockFlexibleSearchService;
    @Mock private SearchResult<CsTicketModel> mockSearchResult;

    
    @Captor private ArgumentCaptor<String> stringQueryArg;
    @Captor private ArgumentCaptor<Map<String, List<String>>> queryParamsArg;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        epamTicketDao = new EpamTicketDAO();
        epamTicketDao.setFlexibleSearchService(mockFlexibleSearchService);
        
        List<EpamCsSort> sorts = new ArrayList<>();
        sorts.add(new EpamCsSort("ticketId"));
        epamTicketDao.setAvailableSorts(sorts);

        searchCriteria = new HashMap<>();
        String [] stateCriterias = {"Closed"};
        String [] sortCriteria = {"ticketId"};
        searchCriteria.put("state", stateCriterias);
        searchCriteria.put(EpamTicketDAO.SORT_NAME, sortCriteria);
        filters = getAvailableFilters();
        
    }
    
    @Test
    public void shouldFindTicketsByCriteria() {
        doReturn(mockSearchResult).when(mockFlexibleSearchService).search(stringQueryArg.capture(), queryParamsArg.capture());
        //when
        epamTicketDao.findTicketsByCriteria(searchCriteria, filters);
        //then
        assertFalse("Params should not be empty", queryParamsArg.getValue().isEmpty());
        assertEquals("There must be only state param", 1,  queryParamsArg.getValue().size());
        assertTrue("Param 'state' not present", queryParamsArg.getValue().containsKey("state"));
        List<String> paramState = queryParamsArg.getValue().get("state");
        assertEquals("State must be 'Closed'", "Closed", paramState.get(0));
        assertTrue("ORDER BY must be present when sortName is set", stringQueryArg.getValue().contains("ORDER BY"));
        assertTrue("ASC must be present when SORT_REVERSE is false or null", stringQueryArg.getValue().contains("ASC"));
        verify(mockFlexibleSearchService, times(1)).search(stringQueryArg.getValue(), queryParamsArg.getValue());
    }

    @Test
    public void shouldFindTicketsByCriteriaWithReverseOrdering() {
        String sortReverse[] = {"true"};
        searchCriteria.put(EpamTicketDAO.SORT_REVERSE, sortReverse);
        doReturn(mockSearchResult).when(mockFlexibleSearchService).search(stringQueryArg.capture(), queryParamsArg.capture());
        //when
        epamTicketDao.findTicketsByCriteria(searchCriteria, filters);
        //then
        assertFalse("Params should not be empty", queryParamsArg.getValue().isEmpty());
        assertEquals("There must be only state param", 1,  queryParamsArg.getValue().size());
        assertTrue("Param 'state' not present", queryParamsArg.getValue().containsKey("state"));
        List<String> paramState = queryParamsArg.getValue().get("state");
        assertEquals("State must be 'Closed'", "Closed", paramState.get(0));
        assertTrue("ORDER BY must be present when sortName is set", stringQueryArg.getValue().contains("ORDER BY"));
        assertTrue("DESC must be present when SORT_REVERSE is true", stringQueryArg.getValue().contains("DESC"));
        verify(mockFlexibleSearchService, times(1)).search(stringQueryArg.getValue(), queryParamsArg.getValue());
    }

    @Test
    public void shouldFindTicketsWithoutCriteria() {
        doReturn(mockSearchResult).when(mockFlexibleSearchService).search(stringQueryArg.capture(), queryParamsArg.capture());
        //when
        epamTicketDao.findTicketsByCriteria(new HashMap<String, String[]>(), filters);
        //then
        assertTrue("Params should be empty", queryParamsArg.getValue().isEmpty());
        assertFalse("ORDER BY must not be present when sortName is not set", stringQueryArg.getValue().contains("ORDER BY"));
        verify(mockFlexibleSearchService, times(1)).search(stringQueryArg.getValue(), queryParamsArg.getValue());
    }
    
    private Set<EpamTicketsFilter> getAvailableFilters() {
        Set<EpamTicketsFilter> filters = new HashSet<>();
        
        criterias = new HashSet<>();
        EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria("Closed", "Closed", "", false);
        criterias.add(criteria);

        filter = new EpamTicketsFilter("state", "STATE");
        filter.setFilterStrategy(new DefaultEnumFilterStrategy());
        filter.setCriterias(criterias);
        filters.add(filter);
        
        return filters;
    }


}
