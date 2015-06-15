package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamCustomerPopulator;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class EpamCustomerConverterTest extends BaseTest {

    @Mock
    private EpamCustomerPopulator mockCustomerPopulator;

    private EpamCustomerConverter customerConverter;

    @Before
    public void setUp() throws Exception {
        customerConverter = new EpamCustomerConverter(mockCustomerPopulator);
    }

    @Test
    public void shouldReturnCustomerData() {
        CustomerModel customerModel = new CustomerModel();

        EpamCustomerData actualCustomerData = customerConverter.convert(customerModel);

        verify(mockCustomerPopulator).populate(eq(customerModel), any(EpamCustomerData.class));
        assertNotNull("EpamCustomerData object should not be null.", actualCustomerData);
    }

}