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

    @Before
    public void setUp() {
        addressConverter = spy(new EpamAddressConverter(mockAddressPopulator));
        addressModel = new AddressModel();
    }

    @Test
    public void shouldReturnCustomerAddressData() {
        EpamAddressData actualAddressData = addressConverter.convert(addressModel);

        verify(mockAddressPopulator).populate(eq(addressModel), any(EpamAddressData.class));
        assertNotNull("EpamAddressData object should not be null.", actualAddressData);
    }

}