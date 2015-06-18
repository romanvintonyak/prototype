package com.epam.customer.converter;

import com.epam.customer.converter.populator.EpamCustomerReversePopulator;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class EpamCustomerReverseConverter extends AbstractPopulatingConverter<EpamCustomerData, CustomerModel> {

    private EpamCustomerReversePopulator epamCustomerReversePopulator;

    public EpamCustomerReverseConverter(EpamCustomerReversePopulator epamCustomerReversePopulator) {
        this.epamCustomerReversePopulator = epamCustomerReversePopulator;
    }

    @Override // hybris marks this method as deprecated but wants to see it here
    protected CustomerModel createTarget() {
        return new CustomerModel();
    }

    @Override
    public CustomerModel convert(EpamCustomerData customerData) throws ConversionException {
        CustomerModel customerModel = createTarget();
        epamCustomerReversePopulator.populate(customerData, customerModel);
        return customerModel;
    }

}
