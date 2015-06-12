package com.epam.customer.converter;

import com.epam.customer.converter.populator.EpamCustomerPopulator;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.CustomerModel;

public class EpamCustomerConverter extends AbstractPopulatingConverter<CustomerModel, EpamCustomerData> {

    private EpamCustomerPopulator customerPopulator;

    // TODO Injection should be performed by means of converters-spring.xml
    public EpamCustomerConverter(EpamCustomerPopulator customerPopulator) {
        this.customerPopulator = customerPopulator;
    }

    @Override
    protected EpamCustomerData createTarget() {
        // TODO Singleton returns prototype implementation
        return null;
    }

    @Override
    public EpamCustomerData convert(CustomerModel customerModel) {
        // TODO Add implementation like in EpamAddressConverter.java
        return null;
    }

}
