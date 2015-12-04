package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketChangeEventEntry;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.ticket.events.model.CsTicketChangeEventEntryModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class EpamTicketChangeEventEntryConverter
        extends AbstractPopulatingConverter<CsTicketChangeEventEntryModel, EpamTicketChangeEventEntry> {

    private EpamTicketChangeEventEntryPopulator populator;

    public EpamTicketChangeEventEntryConverter(EpamTicketChangeEventEntryPopulator populator) {
        this.populator = populator;
    }

    @Override
    protected EpamTicketChangeEventEntry createTarget() {
        return null;
    }

    @Override
    public EpamTicketChangeEventEntry convert(CsTicketChangeEventEntryModel source) {
        checkNotNull(source, "Source model should not be null");
        EpamTicketChangeEventEntry target = new EpamTicketChangeEventEntry();
        populator.populate(source, target);
        return target;
    }
}
