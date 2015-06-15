package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Irina_Vasilyeva
 */
@Component
public class EpamCustomerPopulator implements Populator<CustomerModel, EpamCustomerData> {

    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // TODO Inject as bean
    private CustomerNameStrategy customerNameStrategy;

    @Autowired // TODO Switch to XML-based configuration
    public EpamCustomerPopulator(CustomerNameStrategy customerNameStrategy) {
        this.customerNameStrategy = customerNameStrategy;
    }

    @Override
    public void populate(final CustomerModel source, final EpamCustomerData target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setUid(source.getUid());
        target.setName(source.getName());
        setFirstNameAndLastName(source, target);
        target.setEmail(source.getUid()); // TODO Clarify if uid really contains email
        target.setActive(!source.isLoginDisabled());
        target.setCreatedDate(formatter.format(source.getCreationtime()));
    }

    private void setFirstNameAndLastName(final CustomerModel source, final EpamCustomerData target) {
        final String[] names = customerNameStrategy.splitName(source.getName());
        if (names != null) {
            target.setFirstName(names[0]);
            target.setLastName(names[1]);
        }
    }
}
