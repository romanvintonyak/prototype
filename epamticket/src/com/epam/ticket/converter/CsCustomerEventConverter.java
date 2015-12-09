package com.epam.ticket.converter;

import com.epam.ticket.data.EpamCustomerEvent;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class CsCustomerEventConverter extends AbstractPopulatingConverter<EpamCustomerEvent, CsCustomerEventModel> {

    private CsCustomerEventPopulator populator;

    @Override
    protected CsCustomerEventModel createTarget() {
        return null;
    }

    @Override
    public CsCustomerEventModel convert(EpamCustomerEvent source) throws ConversionException {
        checkNotNull(source, "Source model should not be null");
        CsCustomerEventModel target = new CsCustomerEventModel();
        populator.populate(source, target);
        return target;
    }
}
