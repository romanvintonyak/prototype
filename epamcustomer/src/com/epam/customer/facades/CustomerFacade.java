package com.epam.customer.facades;

import com.epam.customer.data.CustomerAddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;

/**
 * @author Roman_Kovalenko
 */
public interface CustomerFacade {

    List<CustomerAddressData> findCustomerAddresses(String customerId);

    CustomerAddressData createCustomerAddress(CustomerData customer, CustomerAddressData address);

    CustomerAddressData updateCustomerAddress(CustomerData customer, CustomerAddressData address);
}
