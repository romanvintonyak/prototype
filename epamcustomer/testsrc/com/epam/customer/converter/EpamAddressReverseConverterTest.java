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
    private AddressModel addressModel;

    @Before
    public void setUp() {
        addressReverseConverter = spy(new EpamAddressReverseConverter(mockAddressReversePopulator));
        addressData = new EpamAddressData();
        addressModel = new AddressModel();
    }

    @Test
    public void shouldReturnCustomerAddressModel() {
        when(addressReverseConverter.createTarget()).thenReturn(addressModel);

        AddressModel actualAddressModel = addressReverseConverter.convert(addressData);

        verify(addressReverseConverter).createTarget();
        verify(mockAddressReversePopulator).populate(addressData, addressModel);
        assertNotNull("AddressModel object should not be null.", actualAddressModel);
    }
}