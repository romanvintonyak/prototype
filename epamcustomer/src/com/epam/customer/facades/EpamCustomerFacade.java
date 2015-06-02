package com.epam.customer.facades;

import com.epam.customer.data.EpamAddressData;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;

import java.util.List;

/**
 * @author Roman_Kovalenko
 */
public interface EpamCustomerFacade {

    List<EpamAddressData> findCustomerAddresses(String customerId);

    EpamAddressData saveCustomerAddress(EpamAddressData address, String customerId);

    EpamCustomerData findCustomerByUID(String uid);
    
    void createCustomer(EpamCustomerData customerData) throws DuplicateUidException;
    
    void updateCustomer(EpamCustomerData customerData);
}
