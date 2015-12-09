package com.epam.ticket.populator;

import com.epam.ticket.data.EpamTicketChangeEventEntry;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.events.model.CsTicketChangeEventEntryModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class EpamTicketChangeEventEntryPopulator implements
        Populator<CsTicketChangeEventEntryModel, EpamTicketChangeEventEntry> {

    @Override
    public void populate(CsTicketChangeEventEntryModel source, EpamTicketChangeEventEntry target)
            throws ConversionException {
        checkNotNull(source, "Source model should not be null");
        target.setAlteredAttribute(source.getAlteredAttribute().getName());
        target.setOldStringValue(source.getOldStringValue());
        target.setNewStringValue(source.getNewStringValue());
        target.setOldBinaryValue(source.getOldBinaryValue());
        target.setNewBinaryValue(source.getNewBinaryValue());
    }
}
