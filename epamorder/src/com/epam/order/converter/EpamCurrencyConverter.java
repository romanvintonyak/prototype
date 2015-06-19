package com.epam.order.converter;


import com.epam.order.converter.populator.EpamCurrencyPopulator;
import com.epam.order.data.EpamCurrencyData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamCurrencyConverter extends AbstractPopulatingConverter<CurrencyModel, EpamCurrencyData> {

    private EpamCurrencyPopulator epamCurrencyPopulator;

    public EpamCurrencyConverter(EpamCurrencyPopulator epamCurrencyPopulator) {
        this.epamCurrencyPopulator = epamCurrencyPopulator;
    }

    @Override
    protected EpamCurrencyData createTarget() {
        return new EpamCurrencyData();
    }

    @Override
    public EpamCurrencyData convert(CurrencyModel currencyModel) throws ConversionException {
        EpamCurrencyData epamCurrencyData = createTarget();
        epamCurrencyPopulator.populate(currencyModel, epamCurrencyData);
        return epamCurrencyData;
    }

}
