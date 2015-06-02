package com.epam.customer.converter.populator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.epam.customer.data.EpamCustomerData;

import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;

/**
 * @author Irina_Vasilyeva
 */
@Component
public class EpamCustomerPopulator implements Populator<CustomerModel, EpamCustomerData> {
	private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@Autowired
	private CustomerNameStrategy customerNameStrategy;
	
	@Override
	public void populate(final CustomerModel source, final EpamCustomerData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		final String[] names = customerNameStrategy.splitName(source.getName());
		if (names != null)
		{
			target.setFirstName(names[0]);
			target.setLastName(names[1]);
		}

		target.setCreatedDt(formatter.format(source.getCreationtime()));
		target.setName(source.getName());		
		target.setUid(source.getUid());
		target.setEmail(source.getUid());
		target.setActive(!source.isLoginDisabled());
	}
}