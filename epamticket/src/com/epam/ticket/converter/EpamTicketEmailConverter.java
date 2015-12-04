package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketEmail;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.ticket.events.model.CsTicketEmailModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class EpamTicketEmailConverter extends AbstractPopulatingConverter<CsTicketEmailModel, EpamTicketEmail> {

    private EpamTicketEmailPopulator populator;

    public EpamTicketEmailConverter(EpamTicketEmailPopulator populator) {
        this.populator = populator;
    }

    @Override
    protected EpamTicketEmail createTarget() {
        return null;
    }

    @Override
    public EpamTicketEmail convert(CsTicketEmailModel source) {
        checkNotNull(source, "Source model should not be null");
        EpamTicketEmail target = new EpamTicketEmail();
        populator.populate(source, target);
        return target;
    }
}
