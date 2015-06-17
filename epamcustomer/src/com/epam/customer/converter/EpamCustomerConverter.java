package com.epam.customer.converter;

import com.epam.customer.converter.populator.EpamCustomerPopulator;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class EpamCustomerConverter extends AbstractPopulatingConverter<CustomerModel, EpamCustomerData> {

    private EpamCustomerPopulator epamCustomerPopulator;

    public EpamCustomerConverter(EpamCustomerPopulator epamCustomerPopulator) {
        this.epamCustomerPopulator = epamCustomerPopulator;
    }

    @Override
    public EpamCustomerData convert(CustomerModel customerModel) throws ConversionException {
        EpamCustomerData customerData = new EpamCustomerData();
        epamCustomerPopulator.populate(customerModel, customerData);
        return customerData;
    }

}
