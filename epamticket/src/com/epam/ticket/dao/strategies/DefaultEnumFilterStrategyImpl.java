package com.epam.ticket.dao.strategies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

public class DefaultEnumFilterStrategyImpl implements FilterStrategyStrategy {
    private FlexibleSearchService flexibleSearchService;    

    @Override
    public Map<String, Integer> countTickets(String categoryName) {
        final String queryString = 
                  "SELECT {e.code}, count({" + categoryName + "}) " 
                + "FROM {CsTicket AS c JOIN enumerationvalue AS e ON {c."
                         + categoryName + "}={e.pk} } "
                + "GROUP BY {" + categoryName + "}";
        
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.setResultClassList( Arrays.asList(String.class, Integer.class) );
        final SearchResult<List> searchResult = flexibleSearchService.search(query);
        final List<List> categoryStates = searchResult.getResult();
        
        final Map<String, Integer> category = new HashMap<>();
        for (final Iterator<List> iter = categoryStates.iterator(); iter.hasNext(); ) {
            List row = iter.next();
            category.put((String) row.get(0), (Integer) row.get(1));
        }
        
        return category;
    }
    
    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
       this.flexibleSearchService = flexibleSearchService;
    }

    @Override
    public String buildFilterSubquery(String categoryName) {
        // TODO Auto-generated method stub
        return null;
    }

}
