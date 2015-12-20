package com.epam.ticket.dao;

import com.epam.ticket.strategies.DefaultEnumFilterStrategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.doReturn;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class EpamTicketDaoStrategyTest {
    
    private static final String COUNT_QUERY = "SELECT {e.code}, count({priority})" +
            "";
    private EpamTicketDAO epamTicketDao;
    private DefaultEnumFilterStrategy enumStrategy;
    private Map<String, Integer> filteredCounts;
    
    @Mock
    private FlexibleSearchService mockFlexibleSearchService;
    
    @Before
    public void setUp() {
        filteredCounts = new HashMap<>();
        filteredCounts.put("high", Integer.valueOf(2));
        filteredCounts.put("low", Integer.valueOf(0));
        filteredCounts.put("medium", Integer.valueOf(1));
        
        
    }
    
    @Test
    public void shouldCountTicketsFilteredWithEnumFilters() {
        doReturn(filteredCounts).when(mockFlexibleSearchService.search(COUNT_QUERY));
    }
    

}
