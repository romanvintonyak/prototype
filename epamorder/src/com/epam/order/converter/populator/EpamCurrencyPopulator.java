package com.epam.order.converter.populator;

import com.epam.order.data.EpamCurrencyData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Iaroslav_Bezhenar
 */

@Component
public class EpamCurrencyPopulator implements Populator<CurrencyModel, EpamCurrencyData> {

    @Override
    public void populate(CurrencyModel source, EpamCurrencyData target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setIsoCode(source.getIsocode());
        target.setSymbol(source.getSymbol());
    }
}
