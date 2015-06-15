package com.epam.customer.converter;

import com.epam.customer.converter.populator.EpamCustomerReversePopulator;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.CustomerModel;

public class EpamCustomerReverseConverter extends AbstractPopulatingConverter<EpamCustomerData, CustomerModel> {

    private EpamCustomerReversePopulator customerReversePopulator;

    // TODO Injection should be performed by means of converters-spring.xml
    public EpamCustomerReverseConverter(EpamCustomerReversePopulator customerReversePopulator) {
        this.customerReversePopulator = customerReversePopulator;
    }

    @Override
    protected CustomerModel createTarget() {
        // TODO Singleton returns prototype implementation
        return new CustomerModel();
    }

    @Override
    public CustomerModel convert(EpamCustomerData customerData) {
        CustomerModel customerModel = createTarget();
        customerReversePopulator.populate(customerData, customerModel);
        return customerModel;
    }

}
