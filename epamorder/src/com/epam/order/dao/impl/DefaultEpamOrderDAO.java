package com.epam.order.dao.impl;

import com.epam.order.dao.EpamOrderDAO;
import com.epam.order.facades.EpamOrderSearchCriteria;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.daos.impl.DefaultOrderDao;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author Iaroslav_Bezhenar
 */
public class DefaultEpamOrderDAO extends DefaultOrderDao implements EpamOrderDAO {
    private static final Logger LOG = Logger.getLogger(DefaultEpamOrderDAO.class);

    @Override
    public OrderModel getOrderByCode(String itemCode) {
        LOG.info(String.format("Invoke the #getOrderByCode() with parameter code=%s.", itemCode));

        HashMap<String, String> params = new HashMap<>();
        params.put("itemCode", itemCode);
        String query = "SELECT DISTINCT {o:pk} FROM {Order AS o } WHERE {o:code} = ?itemCode";
        LOG.info("Running query: " + query + " with params: " + params);
        SearchResult<OrderModel> result = this.getFlexibleSearchService().search(query, params);
        if (result != null && result.getResult() != null && !result.getResult().isEmpty()) {
            List items = result.getResult();
            return (OrderModel) items.get(0);
        } else {
            LOG.warn("Cannot find Order with code [" + itemCode + "]");
            return null;
        }
    }

    @Override
    public List<OrderModel> getOrderByCriteria(EpamOrderSearchCriteria searchCriteria) {
        LOG.info(String.format("Invoke the #getOrderByCriteria() with parameter searchCriteria=%s.", searchCriteria));

        HashMap<String, String> params = new HashMap<>();
        StringBuilder query = new StringBuilder("SELECT {o:pk} FROM {Order AS o ");
        boolean isPersonalDataParamsInQuery = StringUtils.hasText(searchCriteria.getFirstName()) || StringUtils.hasText(searchCriteria.getLastName()) ||
                StringUtils.hasText(searchCriteria.getEmail()) || StringUtils.hasText(searchCriteria.getPhone());
        boolean isDeliveryAddressParamsInQuery = StringUtils.hasText(searchCriteria.getTown()) || StringUtils.hasText(searchCriteria.getRegion()) ||
                StringUtils.hasText(searchCriteria.getPostalCode());
        if (isPersonalDataParamsInQuery || isDeliveryAddressParamsInQuery) {
            query.append("JOIN User AS u ON {o:user}={u:pk} ");
            query.append("JOIN Address AS a ON {u:pk}={a:owner} ");
            if (isPersonalDataParamsInQuery) {
                if (StringUtils.hasText(searchCriteria.getFirstName())) {
                    query.append("AND {a:firstname} = ?firstname ");
                    params.put("firstname", searchCriteria.getFirstName());
                }
                if (StringUtils.hasText(searchCriteria.getLastName())) {
                    query.append("AND {a:lastname} = ?lastname ");
                    params.put("lastname", searchCriteria.getLastName());
                }
                if (StringUtils.hasText(searchCriteria.getEmail())) {
                    query.append("AND {a:email} = ?email ");
                    params.put("email", searchCriteria.getEmail());
                }
                if (StringUtils.hasText(searchCriteria.getPhone())) {
                    query.append("AND {a:cellphone} = ?phone ");
                    params.put("phone", searchCriteria.getPhone());
                }
            }
            if (isDeliveryAddressParamsInQuery) {
                query.append("AND {o:deliveryaddress} = {a:pk} ");
                if (StringUtils.hasText(searchCriteria.getTown())) {
                    query.append("AND {a:town} = ?town ");
                    params.put("town", searchCriteria.getTown());
                }
                if (StringUtils.hasText(searchCriteria.getRegion())) {
                    query.append("AND {a:region} = ?region ");
                    params.put("region", searchCriteria.getRegion());
                }
                if (StringUtils.hasText(searchCriteria.getPostalCode())) {
                    query.append("AND {a:postalcode} = ?postalcode ");
                    params.put("postalcode", searchCriteria.getPostalCode());
                }
            }
        }

        query.append("} ");

        if (StringUtils.hasText(searchCriteria.getOrderCode())) {
            query.append("WHERE {o:code} = ?itemCode ");
            params.put("itemCode", searchCriteria.getOrderCode());
        }
        LOG.info("Running query: " + query + " with params: " + params);
        SearchResult<OrderModel> result = this.getFlexibleSearchService().search(String.valueOf(query), params);
        if (result != null && result.getResult() != null && !result.getResult().isEmpty()) {
            return result.getResult();
        } else {
            LOG.info("#getOrderByCriteria() has no data ");
            return null;
        }
    }

}
