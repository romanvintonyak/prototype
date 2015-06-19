package com.epam.order.service;

import com.epam.order.facades.EpamOrderSearchCriteria;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;

/**
 * @author Iaroslav_Bezhenar
 */
public interface EpamOrderService {
    public OrderModel getOrderByCode(String itemCode);

    public List<OrderModel> getOrderByCriteria(EpamOrderSearchCriteria searchCriteria);
}
