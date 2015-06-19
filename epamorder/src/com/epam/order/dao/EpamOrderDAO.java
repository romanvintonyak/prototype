package com.epam.order.dao;

import com.epam.order.facades.EpamOrderSearchCriteria;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;

/**
 * @author Iaroslav_Bezhenar
 */
public interface EpamOrderDAO {
    public OrderModel getOrderByCode(String itemCode);

    List<OrderModel> getOrderByCriteria(EpamOrderSearchCriteria searchCriteria);
}
