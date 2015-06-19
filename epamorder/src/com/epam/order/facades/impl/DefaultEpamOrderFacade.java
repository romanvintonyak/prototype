package com.epam.order.facades.impl;

import com.epam.order.converter.EpamOrderConverter;
import com.epam.order.converter.populator.EpamOrderPopulator;
import com.epam.order.data.EpamOrderData;
import com.epam.order.facades.EpamOrderFacade;
import com.epam.order.facades.EpamOrderSearchCriteria;
import com.epam.order.service.EpamOrderService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iaroslav_Bezhenar
 */
public class DefaultEpamOrderFacade implements EpamOrderFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultEpamOrderFacade.class);
    public static final String ORDER_NOT_FOUND = "Order with code '{0}' not found!";
    public static final String ORDER_IN_CRITERIA_NOT_FOUND = "Order with code with provided search criteria was not found!";

    private EpamOrderService epamOrderService;
    private EpamOrderPopulator epamOrderPopulator;
    private EpamOrderConverter epamOrderConverter;

    public DefaultEpamOrderFacade(EpamOrderService epamOrderService, EpamOrderPopulator epamOrderPopulator, EpamOrderConverter epamOrderConverter) {
        this.epamOrderService = epamOrderService;
        this.epamOrderPopulator = epamOrderPopulator;
        this.epamOrderConverter = epamOrderConverter;
    }

    public EpamOrderData getOrderByCode(String itemCode) {
        ServicesUtil.validateParameterNotNullStandardMessage("OrderCode", itemCode);

        LOG.info(String.format("Invoke the #getOrderByCode() with parameter code=%s.", itemCode));

        OrderModel orderModel = epamOrderService.getOrderByCode(itemCode);
        if (null == orderModel) {
            LOG.error(String.format("Order with code '%s' not found!", itemCode));
            throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND, itemCode));
        }

        EpamOrderData epamOrderData = new EpamOrderData();
        epamOrderPopulator.populate(orderModel, epamOrderData);

        return epamOrderData;
    }

    @Override
    public List<EpamOrderData> getOrderByCriteria(EpamOrderSearchCriteria searchCriteria) {
        ServicesUtil.validateParameterNotNullStandardMessage("EpamOrderSearchCriteria", searchCriteria);

        LOG.info(String.format("Invoke the #getOrderByCriteria() with parameter searchCriteria=%s.", searchCriteria));

        List<OrderModel> orderModelList = epamOrderService.getOrderByCriteria(searchCriteria);
        if (null == orderModelList) {
            LOG.error(String.format("Order with searchCriteria '%s' not found!", searchCriteria));
            throw new UnknownIdentifierException(String.format(ORDER_IN_CRITERIA_NOT_FOUND, searchCriteria));
        }
        List<EpamOrderData> orders = new ArrayList<>();
        (orderModelList).forEach(orderModel -> orders.add(epamOrderConverter.convert(orderModel)));
        return orders;
    }
}
