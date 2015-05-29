package com.epam.customer.facades;

import com.epam.customer.data.CustomerAddressData;
import com.epam.customer.data.EpamCustomerData;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;

import java.util.List;

/**
 * @author Roman_Kovalenko
 */
public interface CustomerFacade {

    List<CustomerAddressData> findCustomerAddresses(String customerId);

    CustomerAddressData createCustomerAddress(EpamCustomerData customer, CustomerAddressData address);

    CustomerAddressData updateCustomerAddress(EpamCustomerData customer, CustomerAddressData address);
    
    EpamCustomerData findCustomerByUID(String uid);
    
    void createCustomer(EpamCustomerData customerData) throws DuplicateUidException;
    
    void updateCustomer(EpamCustomerData customerData);
}
