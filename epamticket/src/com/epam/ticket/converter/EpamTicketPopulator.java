package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicket;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.model.CsAgentGroupModel;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.text.DateFormat;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.nullToEmpty;

public class EpamTicketPopulator implements Populator<CsTicketModel, EpamTicket> {

    public static final String EMPTY_STRING = "";

    private EpamTicketEventConverter ticketEventConverter;
    private DateFormat dateFormatter;

    public EpamTicketPopulator(EpamTicketEventConverter ticketEventConverter, DateFormat dateFormatter) {
        this.ticketEventConverter = ticketEventConverter;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void populate(CsTicketModel source, EpamTicket target) throws ConversionException {
        checkNotNull(source, "Source model should not be null");
        target.setTicketId(source.getTicketID());
        target.setCustomerUid(getCustomerUid(source));
        target.setCustomerDisplayName(getCustomerDisplayName(source));
        target.setOrder(getOrderCode(source));
        target.setCategory(getEnumCode(source.getCategory()));
        target.setPriority(getEnumCode(source.getPriority()));
        target.setState(getEnumCode(source.getState()));
        target.setAssignedAgent(getUserName(source.getAssignedAgent()));
        target.setAssignedGroup(getGroupName(source.getAssignedGroup()));
        target.setHeadline(source.getHeadline());
        target.setCreationTime(dateFormatter.format(source.getCreationtime()));
        target.setModifyTime(dateFormatter.format(source.getModifiedtime()));
        // FIXME: getEvents() is @Deprecated, but suggested method FlexibleSearchService::searchRelation
        // throws exception with message "not implemented yet" :)
        target.setEvents(source.getEvents().parallelStream()
                .map(ticketEventConverter::convert)
                .collect(Collectors.toList()));
    }

    private String getCustomerDisplayName(CsTicketModel csTicketModel) {
        UserModel customer = csTicketModel.getCustomer();
        if (customer == null) return EMPTY_STRING;
        // FIXME: getDisplayName() was replaced with getName() thus no possibility to set "displayName" was found, should be investigated
        return nullToEmpty(csTicketModel.getCustomer().getName());
    }

    private String getCustomerUid(CsTicketModel csTicketModel) {
        UserModel customer = csTicketModel.getCustomer();
        if (customer == null) return EMPTY_STRING;
        return nullToEmpty(csTicketModel.getCustomer().getUid());
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

    private String getUserName(PrincipalModel principalModel) {
        if (principalModel == null) return EMPTY_STRING;
        // FIXME: getDisplayName() was replaced with getName() thus no possibility to set "displayName" was found, should be investigated
        return nullToEmpty(principalModel.getName());
    }

    private String getGroupName(CsAgentGroupModel groupModel) {
        if (groupModel == null) return EMPTY_STRING;
        // FIXME: getDisplayName() was replaced with getName() thus no possibility to set "displayName" was found, should be investigated
        return nullToEmpty(groupModel.getName());
    }

}
