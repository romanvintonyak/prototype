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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@UnitTest
public class EpamAddressConverterTest extends BaseTest {

    @Mock
    private EpamAddressPopulator mockAddressPopulator;

    private EpamAddressConverter addressConverter;

    @Before
    public void setUp() {
        addressConverter = new EpamAddressConverter(mockAddressPopulator);
    }

    @Test
    public void shouldReturnCustomerAddressData() {
        AddressModel addressModel = new AddressModel();

        EpamAddressData actualAddressData = addressConverter.convert(addressModel);

        verify(mockAddressPopulator).populate(eq(addressModel), any(EpamAddressData.class));
        assertNotNull("EpamAddressData object should not be null.", actualAddressData);
    }

}