package com.epam.ticket.converter;

import com.epam.ticket.populator.EpamTicketPopulator;
import com.epam.ticket.data.EpamTicket;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.model.CsTicketModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class EpamTicketConverter extends AbstractPopulatingConverter<CsTicketModel, EpamTicket> {

    private EpamTicketPopulator epamTicketPopulator;

    public EpamTicketConverter(EpamTicketPopulator epamTicketPopulator) {
        this.epamTicketPopulator = epamTicketPopulator;
    }

    @Override
    protected EpamTicket createTarget() {
        return null;
    }

    @Override
    public EpamTicket convert(CsTicketModel source) throws ConversionException {
        checkNotNull(source, "Source model should not be null");
        EpamTicket target = new EpamTicket();
        epamTicketPopulator.populate(source, target);
        return target;
    }

}