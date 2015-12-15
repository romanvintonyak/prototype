package com.epam.ticket.converter;

import com.epam.dto.EpamTicket;
import com.epam.ticket.populator.CsTicketPopulator;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.ticket.model.CsTicketModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class CsTicketConverter extends AbstractPopulatingConverter<EpamTicket, CsTicketModel> {

    private CsTicketPopulator populator;

    public  CsTicketConverter(CsTicketPopulator populator) {
        this.populator = populator;
    }

    @Override
    protected CsTicketModel createTarget() {
        return null;
    }

    @Override
    public CsTicketModel convert(EpamTicket source) {
        checkNotNull(source, "Source model should not be null");
        CsTicketModel target = new CsTicketModel();
        populator.populate(source, target);
        return target;
    }
}
