package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamCustomerPopulator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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
        // TODO Add implementation
    }

}