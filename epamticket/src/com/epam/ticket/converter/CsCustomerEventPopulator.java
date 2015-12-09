package com.epam.ticket.converter;

import com.epam.ticket.data.EpamCustomerEvent;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.enums.CsEventReason;
import de.hybris.platform.ticket.enums.CsInterventionType;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class CsCustomerEventPopulator implements Populator<EpamCustomerEvent, CsCustomerEventModel>  {

    @Override
    public void populate(EpamCustomerEvent source, CsCustomerEventModel target) throws ConversionException {
        checkNotNull(source, "Source model should not be null");
        target.setInterventionType(CsInterventionType.valueOf(source.getInterventionType()));
        target.setReason(CsEventReason.valueOf(source.getReason()));
        target.setText(source.getText());
    }
}
