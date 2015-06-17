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

    @Before
    public void setUp() throws Exception {
        customerConverter = new EpamCustomerConverter(mockCustomerPopulator);
        customerModel = new CustomerModel();
    }

    @Test
    public void shouldReturnCustomerData() {
        EpamCustomerData actualCustomerData = customerConverter.convert(customerModel);

        verify(mockCustomerPopulator).populate(eq(customerModel), any(EpamCustomerData.class));
        assertNotNull("EpamCustomerData object should not be null.", actualCustomerData);
    }

}