package com.epam.order.converter;


import com.epam.order.converter.populator.EpamOrderReversePopulator;
import com.epam.order.data.EpamOrderData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamOrderReverseConverter extends AbstractPopulatingConverter<EpamOrderData, OrderModel> {

    private EpamOrderReversePopulator epamOrderReversePopulator;

    public EpamOrderReverseConverter(EpamOrderReversePopulator epamOrderReversePopulator) {
        this.epamOrderReversePopulator = epamOrderReversePopulator;
    }

    @Override
    protected OrderModel createTarget() {
        return new OrderModel();
    }

    @Override
    public OrderModel convert(EpamOrderData epamOrderData) throws ConversionException {
        OrderModel orderModel = createTarget();
        epamOrderReversePopulator.populate(epamOrderData, orderModel);
        return orderModel;
    }

}
