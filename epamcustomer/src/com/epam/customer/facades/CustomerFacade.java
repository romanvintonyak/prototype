package com.epam.customer.facades;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;

/**
 * @author Roman_Kovalenko
 */
public interface CustomerFacade {

    List<AddressData> findCustomerAddresses(String customerId);

    void createCustomerAddress(CustomerData customer, AddressData address);

    void updateCustomerAddress(CustomerData customer, AddressData address);
}
