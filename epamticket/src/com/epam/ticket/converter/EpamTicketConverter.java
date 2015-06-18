package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicket;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.model.CsTicketModel;

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
    public EpamTicket convert(CsTicketModel ticketModel) throws ConversionException {
        EpamTicket epamTicket = new EpamTicket();
        epamTicketPopulator.populate(ticketModel, epamTicket);
        return epamTicket;
    }

}