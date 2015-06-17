package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamCustomerReversePopulator;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class EpamCustomerReverseConverterTest extends BaseTest {

    @Mock
    private EpamCustomerReversePopulator mockCustomerReversePopulator;

    private EpamCustomerReverseConverter customerReverseConverter;
    private EpamCustomerData customerData;

    @Before
    public void setUp() throws Exception {
        customerReverseConverter = new EpamCustomerReverseConverter(mockCustomerReversePopulator);
        customerData = new EpamCustomerData();
    }

    @Test
    public void shouldReturnCustomerModel() {
        CustomerModel actualCustomerModel = customerReverseConverter.convert(customerData);

        verify(mockCustomerReversePopulator).populate(eq(customerData), any(CustomerModel.class));
        assertNotNull("CustomerModel object should not be null.", actualCustomerModel);
    }

}