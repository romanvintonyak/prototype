package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamAddressReversePopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.core.model.user.AddressModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class EpamAddressReverseConverterTest extends BaseTest {

    @Mock
    private EpamAddressReversePopulator mockAddressReversePopulator;

    private EpamAddressReverseConverter addressReverseConverter;

    @Before
    public void setUp() {
        addressReverseConverter = new EpamAddressReverseConverter(mockAddressReversePopulator);
    }

    @Test
    public void shouldReturnCustomerAddressData() {
        EpamAddressData addressData = new EpamAddressData();

        AddressModel actualAddressModel = addressReverseConverter.convert(addressData);

        verify(mockAddressReversePopulator).populate(eq(addressData), anyObject());
        assertNotNull("AddressModel object should not be null.", actualAddressModel);
    }
}