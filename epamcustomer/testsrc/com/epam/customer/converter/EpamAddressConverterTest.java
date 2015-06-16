package com.epam.customer.converter;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.populator.EpamAddressPopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.AddressModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@UnitTest
public class EpamAddressConverterTest extends BaseTest {

    @Mock
    private EpamAddressPopulator mockAddressPopulator;

    private EpamAddressConverter addressConverter;
    private AddressModel addressModel;
    private EpamAddressData addressData;

    @Before
    public void setUp() {
        addressConverter = spy(new EpamAddressConverter(mockAddressPopulator));
        addressModel = new AddressModel();
        addressData = new EpamAddressData();
    }

    @Test
    public void shouldReturnCustomerAddressData() {
        when(addressConverter.createTarget()).thenReturn(addressData);

        EpamAddressData actualAddressData = addressConverter.convert(addressModel);

        verify(addressConverter).createTarget();
        verify(mockAddressPopulator).populate(addressModel, addressData);
        assertNotNull("EpamAddressData object should not be null.", actualAddressData);
    }

}