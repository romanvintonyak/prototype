package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketEmail;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.events.model.CsTicketEmailModel;

public class EpamTicketEmailPopulator implements Populator<CsTicketEmailModel, EpamTicketEmail> {

    @Override
    public void populate(CsTicketEmailModel source, EpamTicketEmail target) throws ConversionException {
        target.setMessageId(source.getMessageId());
        target.setFrom(source.getFrom());
        target.setTo(source.getTo());
        target.setSubject(source.getSubject());
        target.setBody(source.getBody());
        target.setTicketEventId(source.getTicket().getPk().getLong());
    }
}
