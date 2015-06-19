package com.epam.order.facades;

import com.epam.order.data.EpamOrderData;

import java.util.List;

/**
 * @author Iaroslav_Bezhenar
 */
public interface EpamOrderFacade {

    public EpamOrderData getOrderByCode(String itemCode);

    public List<EpamOrderData> getOrderByCriteria(EpamOrderSearchCriteria searchCriteria);

}
