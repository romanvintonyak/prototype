package com.epam.customer.facades.impl;

import com.epam.customer.converter.CustomerAddressConverter;
import com.epam.customer.converter.EpamCustomerPopulator;
import com.epam.customer.converter.EpamCustomerReversePopulator;
import com.epam.customer.data.CustomerAddressData;
import com.epam.customer.data.EpamCustomerData;
import com.epam.customer.facades.CustomerFacade;

import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
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
    public static final String USER_IS_NOT_CUSTOMER = "User with uid '{0}' is not a customer";
    public static final String SUCH_USER_ALREADY_EXIST = "User with uid '{0}' already exists";

    @Autowired
    private UserService userService;
    
    @Autowired
    private EpamCustomerPopulator customerPopulator;
    
    @Autowired
    private EpamCustomerReversePopulator customerReversePopulator;
    
    @Autowired
    private ModelService modelService;

    @Autowired
    private CustomerAddressConverter customerAddressConverter;

    @Override
    public List<CustomerAddressData> findCustomerAddresses(String customerId) {
        ServicesUtil.validateParameterNotNull(customerId, CUSTOMER_MODEL_CANNOT_BE_NULL);
        final UserModel user = userService.getUserForUID(customerId);
        if (null == user) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, customerId));
        }
        final List<CustomerAddressData> addressDataList = new ArrayList<>();
        for(AddressModel addressModel : user.getAddresses()) {
            addressDataList.add(customerAddressConverter.convert(addressModel));
        }
        return addressDataList;
    }

    @Override
    public CustomerAddressData createCustomerAddress(EpamCustomerData customer, CustomerAddressData address) {
        ServicesUtil.validateParameterNotNull(customer, ADDRESS_MODEL_CANNOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(address, ADDRESS_MODEL_CANNOT_BE_NULL);

        return null;
    }

    @Override
    public CustomerAddressData updateCustomerAddress(EpamCustomerData customer, CustomerAddressData address) {
        ServicesUtil.validateParameterNotNull(customer, ADDRESS_MODEL_CANNOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(address, ADDRESS_MODEL_CANNOT_BE_NULL);

        return null;
    }
    
    @Override
    public EpamCustomerData findCustomerByUID(String uid) {
    	ServicesUtil.validateParameterNotNullStandardMessage("UID", uid); 
    	
    	CustomerModel customerModel = retrieveCustomerModelByUID(uid);
    	EpamCustomerData customerData = new EpamCustomerData();
    	customerPopulator.populate(customerModel, customerData);

        return customerData;
    }
    
    @Override
    public void createCustomer(EpamCustomerData customerData) throws DuplicateUidException {
    	ServicesUtil.validateParameterNotNullStandardMessage("customerData", customerData); 
    	ServicesUtil.validateParameterNotNullStandardMessage("email", customerData.getEmail()); 
    	ServicesUtil.validateParameterNotNullStandardMessage("firstName", customerData.getFirstName()); 
    	ServicesUtil.validateParameterNotNullStandardMessage("lastName", customerData.getLastName()); 
    	
    	customerData.setEmail(customerData.getEmail().toLowerCase());
    	if (userService.isUserExisting(customerData.getEmail())) {
    		throw new DuplicateUidException(String.format(SUCH_USER_ALREADY_EXIST, customerData.getEmail())); 
    	}
    	
    	CustomerModel customerModel = new CustomerModel();
    	customerReversePopulator.populate(customerData, customerModel);
    	customerModel.setUid(customerData.getEmail());
    	customerModel.setCustomerID(customerData.getEmail());

    	modelService.save(customerModel);
    }
    
    @Override
    public void updateCustomer(EpamCustomerData customerData) {
    	ServicesUtil.validateParameterNotNullStandardMessage("customerData", customerData); 
    	ServicesUtil.validateParameterNotNullStandardMessage("UID", customerData.getUid());
    	ServicesUtil.validateParameterNotNullStandardMessage("firstName", customerData.getFirstName()); 
    	ServicesUtil.validateParameterNotNullStandardMessage("lastName", customerData.getLastName()); 
    	
    	String uid = customerData.getUid().toLowerCase();
    	if (!userService.isUserExisting(uid)) {
    		throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, uid));
    	}
    	
    	CustomerModel customerModel = retrieveCustomerModelByUID(uid);
    	customerReversePopulator.populate(customerData, customerModel);

    	modelService.save(customerModel);
    }
    
    private CustomerModel retrieveCustomerModelByUID(String uid) {
    	UserModel userModel = userService.getUserForUID(uid);
    	if (null == userModel) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, uid));
        } else if (!(userModel instanceof CustomerModel)) {
        	throw new UnknownIdentifierException(String.format(USER_IS_NOT_CUSTOMER, uid));
        }
    	
    	return (CustomerModel) userModel;
    }
    
    
}