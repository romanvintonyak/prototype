package com.epam.order.converter;


import com.epam.order.converter.populator.EpamOrderPopulator;
import com.epam.order.data.EpamOrderData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamOrderConverter extends AbstractPopulatingConverter<OrderModel, EpamOrderData> {

    private EpamOrderPopulator epamOrderPopulator;

    public EpamOrderConverter(EpamOrderPopulator epamOrderPopulator) {
        this.epamOrderPopulator = epamOrderPopulator;
    }

    @Override
    protected EpamOrderData createTarget() {
        return new EpamOrderData();
    }

    @Override
    public EpamOrderData convert(OrderModel orderModel) throws ConversionException {
        EpamOrderData epamOrderData = createTarget();
        epamOrderPopulator.populate(orderModel, epamOrderData);
        return epamOrderData;
    }

}
