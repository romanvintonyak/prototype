package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketEvent;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.ticket.events.model.CsTicketEventModel;

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
        EpamTicketEvent target = new EpamTicketEvent();
        populator.populate(source, target);
        return target;
    }
}
