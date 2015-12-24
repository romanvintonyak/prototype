package com.epam.ticket.strategies;

import com.epam.dto.EpamTicketsFilter;
import com.epam.dto.EpamTicketsFilterCriteria;
import com.epam.strategies.FilterStrategy;
import com.epam.ticket.dao.EpamTicketDAO;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketFilterStrategiesTest {

    private Map<String, Integer> enumFilterCounts;
    private List<Integer> agentFilterCounts;
    private List<Integer> groupFilterCounts;
    private FilterStrategy enumStrategy;
    private DefaultAgentFilterStrategy agentStrategy;
    private DefaultGroupFilterStrategy groupStrategy;
    private EpamTicketsFilter enumFilter;
    private EpamTicketsFilter groupFilter;
    private EpamTicketsFilter agentFilter;
    private Set<EpamTicketsFilterCriteria> enumCriterias;
    private Set<EpamTicketsFilterCriteria> groupCriterias;
    private Set<EpamTicketsFilterCriteria> agentCriterias;
    

    @Mock private EpamTicketDAO mockDao;
    
    @Captor private ArgumentCaptor<FlexibleSearchQuery> flexibleQueryArg;
    @Captor private ArgumentCaptor<String> queryArg;
    @Captor private ArgumentCaptor< Map<String, Set<UserGroupModel>> > groupQueryParamArg;
    @Captor private ArgumentCaptor< Map<String, Set<UserModel>> > agentQueryParamArg;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        enumStrategy = new DefaultEnumFilterStrategy();
        ((DefaultEnumFilterStrategy)enumStrategy).setTicketDao(mockDao);
        
        groupStrategy = new DefaultGroupFilterStrategy();
        groupStrategy.setTicketDao(mockDao);

        agentStrategy = new DefaultAgentFilterStrategy();
        agentStrategy.setTicketDao(mockDao);

        enumCriterias = new HashSet<>();
        EpamTicketsFilterCriteria criteria = new EpamTicketsFilterCriteria("Closed", "Closed", "", false);
        enumCriterias.add(criteria);

        groupCriterias = new HashSet<>();
        criteria = new EpamTicketsFilterCriteria("myGroup", "My Group", "", false);
        groupCriterias.add(criteria);

        agentCriterias = new HashSet<>();
        criteria = new EpamTicketsFilterCriteria("myAgent", "My Agent", "", false);
        agentCriterias.add(criteria);

        enumFilter = new EpamTicketsFilter("state", "STATE");
        enumFilter.setFilterStrategy(enumStrategy);
        enumFilter.setCriterias(enumCriterias);

        groupFilter = new EpamTicketsFilter("group", "GROUP");
        groupFilter.setFilterStrategy(new DefaultGroupFilterStrategy());
        groupFilter.setCriterias(groupCriterias);

        agentFilter = new EpamTicketsFilter("agent", "AGENT");
        agentFilter.setFilterStrategy(new DefaultAgentFilterStrategy());
        agentFilter.setCriterias(agentCriterias);

        enumFilterCounts = new HashMap<>();
        enumFilterCounts.put("Closed", 42);

        agentFilterCounts = new ArrayList<>();
        agentFilterCounts.add(43);
        
        groupFilterCounts = new ArrayList<>();
        groupFilterCounts.add(44);
    }
    
    @Test
    public void shouldReturnEnumFilterCriteriasWithCounts() {
        doReturn(enumFilterCounts).when(mockDao).getFilterCounts(flexibleQueryArg.capture());
        
        Set<EpamTicketsFilterCriteria> criteriasWithCounts = enumStrategy.getFilterCriteriasWithCounts(enumFilter);
        
        assertFalse("Should return criterias with count", criteriasWithCounts.isEmpty() );
        assertEquals("Should return 1 criteria", 1, criteriasWithCounts.size());
        
        EpamTicketsFilterCriteria criteria = criteriasWithCounts.iterator().next();
        assertEquals("Should return Closed criteria", "Closed", criteria.getName());
        assertEquals("Count for Closed should be 42", Integer.valueOf(42), criteria.getCount());
        
        verify(mockDao, times(1)).getFilterCounts(flexibleQueryArg.getValue());
        
    }

    @Test
    public void shouldReturnAgentFilterCriteriasWithCounts() {
        doReturn(agentFilterCounts).when(mockDao).<UserModel>getTicketCountsWithQueryParams(queryArg.capture(), agentQueryParamArg.capture());
        
        Set<EpamTicketsFilterCriteria> criteriasWithCounts = agentStrategy.getFilterCriteriasWithCounts(agentFilter);
        
        assertFalse("Should return criterias with count", criteriasWithCounts.isEmpty() );
        assertEquals("Should return 1 criteria", 1, criteriasWithCounts.size());
        
        EpamTicketsFilterCriteria criteria = criteriasWithCounts.iterator().next();
        assertEquals("Should return myAgent criteria", "myAgent", criteria.getName());
        assertEquals("Count for Closed should be 43", Integer.valueOf(43), criteria.getCount());
        
        verify(mockDao, times(1)).getTicketCountsWithQueryParams(queryArg.getValue(), agentQueryParamArg.getValue());
        
    }

    @Test
    public void shouldReturnGroupFilterCriteriasWithCounts() {
        doReturn(groupFilterCounts).when(mockDao).<UserGroupModel>getTicketCountsWithQueryParams(queryArg.capture(), groupQueryParamArg.capture());
        
        Set<EpamTicketsFilterCriteria> criteriasWithCounts = groupStrategy.getFilterCriteriasWithCounts(groupFilter);
        
        assertFalse("Should return criterias with count", criteriasWithCounts.isEmpty() );
        assertEquals("Should return 1 criteria", 1, criteriasWithCounts.size());
        
        EpamTicketsFilterCriteria criteria = criteriasWithCounts.iterator().next();
        assertEquals("Should return myGroup criteria", "myGroup", criteria.getName());
        assertEquals("Count for Closed should be 44", Integer.valueOf(44), criteria.getCount());
        
        verify(mockDao, times(1)).getTicketCountsWithQueryParams(queryArg.getValue(), groupQueryParamArg.getValue());
        
    }
}
