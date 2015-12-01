package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicket;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.ticket.model.CsTicketModel;

import java.text.DateFormat;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.nullToEmpty;

public class EpamTicketPopulator implements Populator<CsTicketModel, EpamTicket> {

    public static final String EMPTY_STRING = "";

    private DateFormat dateFormatter;
    private EpamTicketEventConverter ticketEventConverter;

    public EpamTicketPopulator(DateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public void setTicketEventConverter(EpamTicketEventConverter ticketEventConverter) {
        this.ticketEventConverter = ticketEventConverter;
    }

    @Override
    public void populate(CsTicketModel csTicketModel, EpamTicket epamTicket) throws ConversionException {
        epamTicket.setTicketId(csTicketModel.getTicketID());
        
        epamTicket.setCustomerDisplayName(getCustomerDisplayName(csTicketModel));
        epamTicket.setCustomerUid(getCustomerUid(csTicketModel));
        
        epamTicket.setOrder(getOrderCode(csTicketModel));
        epamTicket.setCategory(getEnumCode(csTicketModel.getCategory()));
        epamTicket.setPriority(getEnumCode(csTicketModel.getPriority()));
        epamTicket.setState(getEnumCode(csTicketModel.getState()));

        epamTicket.setAssignedAgent(getUserName(csTicketModel.getAssignedAgent()));
        epamTicket.setAssignedGroup(getUserName(csTicketModel.getAssignedGroup()));

        epamTicket.setHeadline(csTicketModel.getHeadline());
        epamTicket.setCreationTime(dateFormatter.format(csTicketModel.getCreationtime()));
        epamTicket.setModifyTime(dateFormatter.format(csTicketModel.getModifiedtime()));

        // FIXME: getEvents() is @Deprecated, but suggested method FlexibleSearchService::searchRelation
        // throws exception with message "not implemented yet" :)
        csTicketModel.getEvents().parallelStream()
                .map(ticketEventConverter::convert)
                .collect(Collectors.toList());
    }

    private String getCustomerDisplayName(CsTicketModel csTicketModel){
    	UserModel customer = csTicketModel.getCustomer();
    	if(customer == null) return EMPTY_STRING;
    	return nullToEmpty(csTicketModel.getCustomer().getDisplayName());
    }
    
    private String getCustomerUid(CsTicketModel csTicketModel){
    	UserModel customer = csTicketModel.getCustomer();
    	if(customer == null) return EMPTY_STRING;
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
        return nullToEmpty(principalModel.getDisplayName());
    }

}
