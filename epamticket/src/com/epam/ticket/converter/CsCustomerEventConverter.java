package com.epam.ticket.converter;

import com.epam.dto.EpamCustomerEvent;
import com.epam.ticket.populator.CsCustomerEventPopulator;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class CsCustomerEventConverter extends AbstractPopulatingConverter<EpamCustomerEvent, CsCustomerEventModel> {

    private CsCustomerEventPopulator populator;

    public CsCustomerEventConverter(CsCustomerEventPopulator populator) {
        this.populator = populator;
    }

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
