package com.epam.ticket.populator;

import com.epam.ticket.data.EpamTicketEmail;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.events.model.CsTicketEmailModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class EpamTicketEmailPopulator implements Populator<CsTicketEmailModel, EpamTicketEmail> {

    @Override
    public void populate(CsTicketEmailModel source, EpamTicketEmail target) throws ConversionException {
        checkNotNull(source, "Source model should not be null");
        target.setMessageId(source.getMessageId());
        target.setFrom(source.getFrom());
        target.setTo(source.getTo());
        target.setSubject(source.getSubject());
        target.setBody(source.getBody());
    }
}
