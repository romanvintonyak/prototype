package com.epam.order.converter.populator;

import com.epam.order.data.EpamCurrencyData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import org.springframework.util.Assert;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamCurrencyReversePopulator implements Populator<EpamCurrencyData, CurrencyModel> {

    @Override
    public void populate(EpamCurrencyData source, CurrencyModel target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        throw new UnsupportedOperationException();
    }
}
