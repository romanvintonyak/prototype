package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicket;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.model.CsTicketModel;
import java.text.DateFormat;

import static com.google.common.base.Strings.nullToEmpty;

public class EpamTicketPopulator implements Populator<CsTicketModel, EpamTicket> {

    public static final String EMPTY_STRING = "";
    private DateFormat dateFormatter;

    public EpamTicketPopulator(DateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void populate(CsTicketModel csTicketModel, EpamTicket epamTicket) throws ConversionException {
        epamTicket.setTicketId(csTicketModel.getTicketID());
        epamTicket.setCustomer(csTicketModel.getCustomer().getDisplayName());
        epamTicket.setOrder(getOrderCode(csTicketModel));
        epamTicket.setCategory(getEnumCode(csTicketModel.getCategory()));
        epamTicket.setPriority(getEnumCode(csTicketModel.getPriority()));
        epamTicket.setState(getEnumCode(csTicketModel.getState()));

        epamTicket.setAssightedEmployee(getuserName(csTicketModel.getAssignedAgent()));
        epamTicket.setAssightedgroup(getuserName(csTicketModel.getAssignedGroup()));

        epamTicket.setHeadline(csTicketModel.getHeadline());
        epamTicket.setCreationTime(dateFormatter.format(csTicketModel.getCreationtime()));
        epamTicket.setModifyTime(dateFormatter.format(csTicketModel.getModifiedtime()));

    }

    private String getOrderCode(CsTicketModel csTicketModel) {
        AbstractOrderModel orderModel = csTicketModel.getOrder();
        if (orderModel == null) return EMPTY_STRING;
        return nullToEmpty(orderModel.getCode());
    }

    private String getEnumCode(HybrisEnumValue hybrisEnumValue) {
        if (hybrisEnumValue == null) return EMPTY_STRING;
        return nullToEmpty(hybrisEnumValue.getCode());
    }

    private String getuserName(PrincipalModel principalModel) {
        if (principalModel == null) return EMPTY_STRING;
        return nullToEmpty(principalModel.getDisplayName());
    }

}
