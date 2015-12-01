package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketChangeEventEntry;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.events.model.CsTicketChangeEventEntryModel;

public class EpamTicketChangeEventEntryPopulator implements
        Populator<CsTicketChangeEventEntryModel, EpamTicketChangeEventEntry> {

    @Override
    public void populate(CsTicketChangeEventEntryModel source, EpamTicketChangeEventEntry target)
            throws ConversionException {
        target.setEventId(source.getEvent().getPk().getLong());
        target.setAlteredAttribute(source.getAlteredAttribute().getName());
        target.setOldStringValue(source.getOldStringValue());
        target.setNewStringValue(source.getNewStringValue());
        target.setOldBinaryValue(source.getOldBinaryValue());
        target.setNewBinaryValue(source.getNewBinaryValue());
    }
}
