package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamCustomerReversePopulator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class EpamCustomerReverseConverterTest extends BaseTest {

    @Mock
    private EpamCustomerReversePopulator mockCustomerReversePopulator;

    private EpamCustomerReverseConverter customerReverseConverter;

    @Before
    public void setUp() throws Exception {
        customerReverseConverter = new EpamCustomerReverseConverter(mockCustomerReversePopulator);
    }

    @Test
    public void shouldReturnCustomerModel() {
        // TODO Add implementation
    }

}