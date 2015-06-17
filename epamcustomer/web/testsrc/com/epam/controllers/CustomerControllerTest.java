package com.epam.controllers;

import com.epam.customer.data.EpamAddressData;
import com.epam.customer.data.EpamCustomerData;
import com.epam.customer.facades.EpamCustomerFacade;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
public class CustomerControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private EpamCustomerFacade mockCustomerFacade;

    private CustomerController customerController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(mockCustomerFacade);
    }

    @Test
    public void shouldReturnCollectionOfAddressDataWhenCustomerWithSpecifiedIdExists() {
        String customerId = "john_dou@gmail.com";
        String encodedCustomerId = encodeBase64(customerId);
        List<EpamAddressData> expectedAddressData = new ArrayList<>();
        EpamAddressData addressData1 = new EpamAddressData();
        EpamAddressData addressData2 = new EpamAddressData();
        expectedAddressData.add(addressData1);
        expectedAddressData.add(addressData2);
        when(mockCustomerFacade.findCustomerAddresses(customerId)).thenReturn(expectedAddressData);

        Collection<EpamAddressData> actualAddressData = customerController.getCustomerAddresses(encodedCustomerId);

        verify(mockCustomerFacade).findCustomerAddresses(customerId);
        assertEquals(expectedAddressData, actualAddressData);
    }

    private String encodeBase64(String customerId) {
        return new String(Base64.getEncoder().encode(customerId.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void shouldThrowUnknownIdentifierExceptionWhenCustomerWithSpecifiedIdNotExists() {
        String customerId = "john_dou@gmail.com";
        String encodedCustomerId = encodeBase64(customerId);
        when(mockCustomerFacade.findCustomerAddresses(customerId)).thenThrow(UnknownIdentifierException.class);
        thrown.expect(UnknownIdentifierException.class);

        customerController.getCustomerAddresses(encodedCustomerId);
    }

    @Test
    public void shouldPostAndReturnAddressDataWhenCustomerWithSpecifiedIdExists() {
        String customerId = "john_dou@gmail.com";
        String encodedCustomerId = encodeBase64(customerId);
        EpamAddressData givenAddressData = new EpamAddressData();
        EpamAddressData expectedAddressData = new EpamAddressData();
        when(mockCustomerFacade.createCustomerAddress(givenAddressData, customerId)).thenReturn(expectedAddressData);

        EpamAddressData actualAddressData = customerController.createCustomerAddresses(encodedCustomerId, givenAddressData);

        verify(mockCustomerFacade).createCustomerAddress(givenAddressData, customerId);
        assertEquals(expectedAddressData, actualAddressData);
    }

    @Test
    public void shouldPutAndReturnAddressDataWhenCustomerWithSpecifiedIdExists() {
        String customerId = "john_dou@gmail.com";
        String encodedCustomerId = encodeBase64(customerId);
        EpamAddressData givenAddressData = new EpamAddressData();
        EpamAddressData expectedAddressData = new EpamAddressData();
        when(mockCustomerFacade.updateCustomerAddress(givenAddressData, customerId)).thenReturn(expectedAddressData);

        EpamAddressData actualAddressData = customerController.updateCustomerAddresses(encodedCustomerId, givenAddressData);

        verify(mockCustomerFacade).updateCustomerAddress(givenAddressData, customerId);
        assertEquals(expectedAddressData, actualAddressData);
    }

    @Test
    public void shouldReturnCustomerDataWhenCustomerWithSpecifiedIdExists() {
        String customerId = "john_dou@gmail.com";
        String encodedCustomerId = encodeBase64(customerId);
        EpamCustomerData expectedCustomerData = new EpamCustomerData();
        when(mockCustomerFacade.findCustomerByUID(customerId)).thenReturn(expectedCustomerData);

        EpamCustomerData actualCustomerData = customerController.getCustomer(encodedCustomerId);

        verify(mockCustomerFacade).findCustomerByUID(customerId);
        assertEquals(expectedCustomerData, actualCustomerData);
    }

    @Test
    public void shouldCallFacadeCreateCustomer() throws DuplicateUidException {
        EpamCustomerData customerData = new EpamCustomerData();

        customerController.createCustomer(customerData);

        verify(mockCustomerFacade).createCustomer(customerData);
    }

    @Test
    public void shouldCallFacadeUpdateCustomer() throws DuplicateUidException {
        EpamCustomerData customerData = new EpamCustomerData();

        customerController.updateCustomer(customerData);

        verify(mockCustomerFacade).updateCustomer(customerData);
    }

}