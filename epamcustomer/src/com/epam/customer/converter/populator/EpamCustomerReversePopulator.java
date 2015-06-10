package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Irina_Vasilyeva
 */
@Component
public class EpamCustomerReversePopulator implements Populator<EpamCustomerData, CustomerModel> {

    private CustomerNameStrategy customerNameStrategy;

    @Autowired
    public EpamCustomerReversePopulator(CustomerNameStrategy customerNameStrategy) {
        this.customerNameStrategy = customerNameStrategy;
    }

    @Override
    public void populate(final EpamCustomerData source, final CustomerModel target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setName(customerNameStrategy.getName(source.getFirstName(), source.getLastName()));
        target.setOriginalUid(source.getUid());
        target.setUid(source.getUid());
        target.setCustomerID(source.getUid());
        target.setLoginDisabled(!source.isActive());
    }

}
