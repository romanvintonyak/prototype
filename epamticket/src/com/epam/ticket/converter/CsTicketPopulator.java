package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicket;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.enums.CsTicketCategory;
import de.hybris.platform.ticket.enums.CsTicketPriority;
import de.hybris.platform.ticket.model.CsTicketModel;

import static com.google.common.base.Preconditions.checkNotNull;

public class CsTicketPopulator implements Populator<EpamTicket, CsTicketModel> {

    @Override
    public void populate(EpamTicket source, CsTicketModel target) throws ConversionException {
        checkNotNull(source, "Source model should not be null");
        target.setCategory(CsTicketCategory.valueOf(source.getCategory()));
        target.setPriority(CsTicketPriority.valueOf(source.getPriority()));
        target.setHeadline(source.getHeadline());
    }
}
