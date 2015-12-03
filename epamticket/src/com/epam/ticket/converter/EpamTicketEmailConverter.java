package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketEmail;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.ticket.events.model.CsTicketEmailModel;

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
    public EpamTicketEmail convert(CsTicketEmailModel csTicketEmailModel) {
        EpamTicketEmail epamTicketEmail = new EpamTicketEmail();
        populator.populate(csTicketEmailModel, epamTicketEmail);
        return epamTicketEmail;
    }
}
