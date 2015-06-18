package com.epam.customer.facades.impl;

import com.epam.customer.converter.EpamAddressConverter;
import com.epam.customer.converter.EpamAddressReverseConverter;
import com.epam.customer.converter.populator.EpamAddressReversePopulator;
import com.epam.customer.converter.populator.EpamCustomerPopulator;
import com.epam.customer.converter.populator.EpamCustomerReversePopulator;
import com.epam.customer.data.EpamAddressData;
import com.epam.customer.data.EpamCustomerData;
import com.epam.customer.facades.EpamCustomerFacade;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman_Kovalenko
 */
public class DefaultEpamCustomerFacade implements EpamCustomerFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultEpamCustomerFacade.class);
    public static final String CUSTOMER_MODEL_CANNOT_BE_NULL = "Customer model cannot be null";
    public static final String CUSTOMER_ID_CANNOT_BE_NULL = "Customer uid cannot be null";
    public static final String ADDRESS_ID_CANNOT_BE_NULL = "Customer address uid cannot be null";
    public static final String ADDRESS_MODEL_CANNOT_BE_NULL = "Address model cannot be null";
    public static final String USER_NOT_FOUND = "User with uid '{0}' not found!";
    public static final String USER_IS_NOT_CUSTOMER = "User with uid '{0}' is not a customer";
    public static final String SUCH_USER_ALREADY_EXIST = "User with uid '{0}' already exists";

    private UserService userService;
    private CustomerAccountService customerAccountService;
    private ModelService modelService;
    private EpamCustomerPopulator epamCustomerPopulator;
    private EpamCustomerReversePopulator epamCustomerReversePopulator;
    private EpamAddressConverter epamAddressConverter;
    private EpamAddressReverseConverter epamAddressReverseConverter;
    private EpamAddressReversePopulator epamAddressReversePopulator;

    public DefaultEpamCustomerFacade(UserService userService,
                                     CustomerAccountService customerAccountService,
                                     ModelService modelService,
                                     EpamCustomerPopulator epamCustomerPopulator,
                                     EpamCustomerReversePopulator epamCustomerReversePopulator,
                                     EpamAddressConverter epamAddressConverter,
                                     EpamAddressReverseConverter epamAddressReverseConverter,
                                     EpamAddressReversePopulator epamAddressReversePopulator) {
        this.userService = userService;
        this.customerAccountService = customerAccountService;
        this.modelService = modelService;
        this.epamCustomerPopulator = epamCustomerPopulator;
        this.epamCustomerReversePopulator = epamCustomerReversePopulator;
        this.epamAddressConverter = epamAddressConverter;
        this.epamAddressReverseConverter = epamAddressReverseConverter;
        this.epamAddressReversePopulator = epamAddressReversePopulator;
    }

    @Override
    public List<EpamAddressData> findCustomerAddresses(final String customerId) {
        ServicesUtil.validateParameterNotNull(customerId, CUSTOMER_MODEL_CANNOT_BE_NULL);

        final CustomerModel user = (CustomerModel) userService.getUserForUID(customerId);
        if (null == user) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, customerId));
        }

        final List<EpamAddressData> addressDataList = new ArrayList<>();
        user.getAddresses().stream()
                .forEach(addressModel -> addressDataList.add(epamAddressConverter.convert(addressModel)));
        return addressDataList;
    }

    @Override
    public EpamAddressData createCustomerAddress(final EpamAddressData addressData, final String customerId) {
        ServicesUtil.validateParameterNotNull(addressData, ADDRESS_MODEL_CANNOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(customerId, CUSTOMER_ID_CANNOT_BE_NULL);

        final CustomerModel customer = (CustomerModel) userService.getUserForUID(customerId);
        if (null == customer) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, customerId));
        }

        AddressModel customerAddress = epamAddressReverseConverter.convert(addressData);
        customerAccountService.saveAddressEntry(customer, customerAddress);
        return epamAddressConverter.convert(customerAddress);
    }

    @Override
    public EpamAddressData updateCustomerAddress(final EpamAddressData addressData, final String customerId) {
        ServicesUtil.validateParameterNotNull(addressData, ADDRESS_MODEL_CANNOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(customerId, CUSTOMER_ID_CANNOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(addressData.getPk(), ADDRESS_ID_CANNOT_BE_NULL);

        final CustomerModel customer = (CustomerModel) userService.getUserForUID(customerId);
        if (null == customer) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, customerId));
        }

        AddressModel customerAddress = customerAccountService.getAddressForCode(customer, addressData.getPk().toString());
        epamAddressReversePopulator.populate(addressData, customerAddress);
        customerAccountService.saveAddressEntry(customer, customerAddress);
        return epamAddressConverter.convert(customerAddress);
    }

    @Override
    public EpamCustomerData findCustomerByUID(final String uid) {
        ServicesUtil.validateParameterNotNullStandardMessage("UID", uid);

        CustomerModel customerModel = retrieveCustomerModelByUID(uid);
        EpamCustomerData customerData = new EpamCustomerData();
        epamCustomerPopulator.populate(customerModel, customerData);

        return customerData;
    }

    @Override
    public void createCustomer(final EpamCustomerData customerData) throws DuplicateUidException {
        ServicesUtil.validateParameterNotNullStandardMessage("customerData", customerData);
        ServicesUtil.validateParameterNotNullStandardMessage("email", customerData.getEmail());
        ServicesUtil.validateParameterNotNullStandardMessage("firstName", customerData.getFirstName());
        ServicesUtil.validateParameterNotNullStandardMessage("lastName", customerData.getLastName());

        customerData.setEmail(customerData.getEmail().toLowerCase());
        if (userService.isUserExisting(customerData.getEmail())) {
            throw new DuplicateUidException(String.format(SUCH_USER_ALREADY_EXIST, customerData.getEmail()));
        }

        CustomerModel customerModel = new CustomerModel();
        epamCustomerReversePopulator.populate(customerData, customerModel);
        customerModel.setUid(customerData.getEmail());
        customerModel.setCustomerID(customerData.getEmail());

        modelService.save(customerModel);
    }

    @Override
    public void updateCustomer(final EpamCustomerData customerData) {
        ServicesUtil.validateParameterNotNullStandardMessage("customerData", customerData);
        ServicesUtil.validateParameterNotNullStandardMessage("UID", customerData.getUid());
        ServicesUtil.validateParameterNotNullStandardMessage("firstName", customerData.getFirstName());
        ServicesUtil.validateParameterNotNullStandardMessage("lastName", customerData.getLastName());

        String uid = customerData.getUid().toLowerCase();
        if (!userService.isUserExisting(uid)) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, uid));
        }

        CustomerModel customerModel = retrieveCustomerModelByUID(uid);
        epamCustomerReversePopulator.populate(customerData, customerModel);

        modelService.save(customerModel);
    }

    private CustomerModel retrieveCustomerModelByUID(final String uid) {
        UserModel userModel = userService.getUserForUID(uid);
        if (null == userModel) {
            throw new UnknownIdentifierException(String.format(USER_NOT_FOUND, uid));
        } else if (!(userModel instanceof CustomerModel)) {
            throw new UnknownIdentifierException(String.format(USER_IS_NOT_CUSTOMER, uid));
        }

        return (CustomerModel) userModel;
    }

}