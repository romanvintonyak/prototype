package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamAddressReversePopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.AddressModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@UnitTest
public class EpamAddressReverseConverterTest extends BaseTest {

    @Mock
    private EpamAddressReversePopulator mockAddressReversePopulator;

    private EpamAddressReverseConverter addressReverseConverter;
    private EpamAddressData addressData;

    @Before
    public void setUp() {
        addressReverseConverter = spy(new EpamAddressReverseConverter(mockAddressReversePopulator));
        addressData = new EpamAddressData();
    }

    @Test
    public void shouldReturnCustomerAddressModel() {
        AddressModel actualAddressModel = addressReverseConverter.convert(addressData);

        verify(mockAddressReversePopulator).populate(eq(addressData), any(AddressModel.class));
        assertNotNull("AddressModel object should not be null.", actualAddressModel);
    }
}