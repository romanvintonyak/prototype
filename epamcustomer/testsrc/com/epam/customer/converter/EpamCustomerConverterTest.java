package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamCustomerPopulator;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class EpamCustomerConverterTest extends BaseTest {

    @Mock
    private EpamCustomerPopulator mockCustomerPopulator;

    private EpamCustomerConverter customerConverter;
    private CustomerModel customerModel;
    private EpamCustomerData customerData;

    @Before
    public void setUp() throws Exception {
        customerConverter = spy(new EpamCustomerConverter(mockCustomerPopulator));
        customerModel = new CustomerModel();
        customerData = new EpamCustomerData();
    }

    @Test
    public void shouldReturnCustomerData() {
        when(customerConverter.createTarget()).thenReturn(customerData);

        EpamCustomerData actualCustomerData = customerConverter.convert(customerModel);

        verify(customerConverter).createTarget();
        verify(mockCustomerPopulator).populate(customerModel, customerData);
        assertNotNull("EpamCustomerData object should not be null.", actualCustomerData);
    }

}