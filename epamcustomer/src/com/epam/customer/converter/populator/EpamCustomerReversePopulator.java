package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Irina_Vasilyeva
 */
@Component
public class EpamCustomerReversePopulator implements Populator<EpamCustomerData, CustomerModel> {

    private CustomerNameStrategy customerNameStrategy;
    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // TODO Inject as bean

    @Autowired // TODO Switch to XML-based configuration
    public EpamCustomerReversePopulator(CustomerNameStrategy customerNameStrategy) {
        this.customerNameStrategy = customerNameStrategy;
    }

    @Override
    public void populate(final EpamCustomerData source, final CustomerModel target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setUid(source.getUid()); // TODO Do the target.Uid, target.CustomerID and OriginalUid should contain the same value?
        target.setCustomerID(source.getUid());
        target.setOriginalUid(source.getUid());
        target.setName(customerNameStrategy.getName(source.getFirstName(), source.getLastName()));
        // TODO In EpamCustomerPopulator.java there is the following: target.setEmail(source.getUid());
        // Should we have to implement reverted operation here?
        target.setLoginDisabled(!source.isActive());
        setCreationTime(source, target);
    }

    private void setCreationTime(EpamCustomerData source, CustomerModel target) {
        if (source.getCreatedDate() == null) {
            return;
        }

        try {
            target.setCreationtime(formatter.parse(source.getCreatedDate()));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Cannot parse the CreatedDate into java.util.Date.", e);
        }
    }

}
