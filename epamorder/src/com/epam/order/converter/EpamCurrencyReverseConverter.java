package com.epam.order.converter;


import com.epam.order.converter.populator.EpamCurrencyReversePopulator;
import com.epam.order.data.EpamCurrencyData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamCurrencyReverseConverter extends AbstractPopulatingConverter<EpamCurrencyData, CurrencyModel> {

    private EpamCurrencyReversePopulator epamCurrencyReversePopulator;

    public EpamCurrencyReverseConverter(EpamCurrencyReversePopulator epamCurrencyReversePopulator) {
        this.epamCurrencyReversePopulator = epamCurrencyReversePopulator;
    }

    @Override
    protected CurrencyModel createTarget() {
        return new CurrencyModel();
    }

    @Override
    public CurrencyModel convert(EpamCurrencyData epamCurrencyData) throws ConversionException {
        CurrencyModel currencyModel = createTarget();
        epamCurrencyReversePopulator.populate(epamCurrencyData, currencyModel);
        return currencyModel;
    }

}
