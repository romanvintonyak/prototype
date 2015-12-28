package com.epam.order.service.impl;

import com.epam.order.dao.EpamOrderDAO;
import com.epam.order.facades.EpamOrderSearchCriteria;
import com.epam.order.service.EpamOrderService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.impl.DefaultOrderService;
import de.hybris.platform.servicelayer.internal.service.AbstractBusinessService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * @author Iaroslav_Bezhenar
 */
public class DefaultEpamOrderService extends AbstractBusinessService implements EpamOrderService {
    private static final Logger LOG = Logger.getLogger(DefaultOrderService.class);

    private EpamOrderDAO epamOrderDAO;

    @Override
    public OrderModel getOrderByCode(String itemCode) {
        LOG.info(String.format("Invoke the #getOrderByCode() with parameter code=%s.", itemCode));

        return epamOrderDAO.getOrderByCode(itemCode);
    }

    @Override
    public List<OrderModel> getOrderByCriteria(EpamOrderSearchCriteria searchCriteria) {
        LOG.info(String.format("Invoke the #getOrderByCriteria() with parameter searchCriteria=%s.", searchCriteria));

        return epamOrderDAO.getOrderByCriteria(searchCriteria);
    }

    @Required
    public void setEpamOrderDAO(EpamOrderDAO epamOrderDAO) {
        this.epamOrderDAO = epamOrderDAO;
    }
}
