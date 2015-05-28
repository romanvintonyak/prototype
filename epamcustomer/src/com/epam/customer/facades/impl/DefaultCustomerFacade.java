package com.epam.customer.facades.impl;

import com.epam.customer.facades.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman_Kovalenko
 */
@Service
public class DefaultCustomerFacade implements CustomerFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCustomerFacade.class);
    public static final String CUSTOMER_MODEL_CANNOT_BE_NULL = "Customer model cannot be null";
    public static final String ADDRESS_MODEL_CANNOT_BE_NULL = "Address model cannot be null";
    public static final String USER_NOT_FOUND = "User with uid '{0}' not found!";
    public static final String ADDRESS_TYPE_CANNOT_BE_NULL = "Address type cannot be null";

    @Autowired
    private UserService userService;

    @Override
    public List<AddressData> findCustomerAddresses(String customerId) {
        ServicesUtil.validateParameterNotNull(customerId, CUSTOMER_MODEL_CANNOT_BE_NULL);
        final UserModel user = userService.getUserForUID(customerId);
        if (null == user) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, user.getUid()));
        }
        final List<AddressData> addressDataList = new ArrayList<>();
        for(AddressModel addressModel : user.getAddresses()) {
            AddressData addressData = new AddressData();
            addressData.setTitle(addressModel.getTitle().getCode());
            addressDataList.add(addressData);
        }
        return addressDataList;
    }

    @Override
    public void createCustomerAddress(CustomerData customer, AddressData address) {
        ServicesUtil.validateParameterNotNull(customer, ADDRESS_MODEL_CANNOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(address, ADDRESS_MODEL_CANNOT_BE_NULL);
    }

    @Override
    public void updateCustomerAddress(CustomerData customer, AddressData address) {
        ServicesUtil.validateParameterNotNull(customer, ADDRESS_MODEL_CANNOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(address, ADDRESS_MODEL_CANNOT_BE_NULL);
    }

}