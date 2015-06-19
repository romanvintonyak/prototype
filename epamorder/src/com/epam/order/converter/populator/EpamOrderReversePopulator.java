package com.epam.order.converter.populator;

import com.epam.order.data.EpamOrderData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.OrderModel;
import org.springframework.util.Assert;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamOrderReversePopulator implements Populator<EpamOrderData, OrderModel> {

    @Override
    public void populate(final EpamOrderData source, final OrderModel target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        throw new UnsupportedOperationException();
    }
}
