package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketEvent;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.ticket.events.model.CsTicketEventModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class EpamTicketEventConverter extends AbstractPopulatingConverter<CsTicketEventModel, EpamTicketEvent> {

    private EpamTicketEventPopulator populator;

    public EpamTicketEventConverter(EpamTicketEventPopulator populator) {
        this.populator = populator;
    }

    @Override
    protected EpamTicketEvent createTarget() {
        return null;
    }

    @Override
    public EpamTicketEvent convert(CsTicketEventModel source) {
        checkNotNull(source, "Source model should not be null");
        EpamTicketEvent target = new EpamTicketEvent();
        populator.populate(source, target);
        return target;
    }
}
