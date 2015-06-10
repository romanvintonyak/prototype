package com.epam.customer.converter.populator;

import com.epam.customer.BaseTest;
import com.epam.customer.data.EpamAddressData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.AddressModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@UnitTest
public class EpamAddressPopulatorTest extends BaseTest {

    private EpamAddressPopulator addressPopulator = new EpamAddressPopulator();

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterSourceIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter source cannot be null.");

        addressPopulator.populate(null, new EpamAddressData());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterTargetIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter target cannot be null.");

        addressPopulator.populate(new AddressModel(), null);
    }

    @Test
    public void shouldSetEmptyAddress1WhenStreetNameIsAbsentInSourceParameter() {
        AddressModel source = getAddressModel();
        EpamAddressData target = new EpamAddressData();

        addressPopulator.populate(source, target);

        assertTrue("Address1 should be an empty string.", target.getAddress1().isEmpty());
    }

    private AddressModel getAddressModel() {
        AddressModel addressModel = new AddressModel();
        addressModel.setBillingAddress(true);
        addressModel.setShippingAddress(true);
        return addressModel;
    }

    @Test
    public void shouldSetAddress1WhenOnlyStreetNameSetInSourceParameter() {
        AddressModel source = getAddressModel();
        String expectedAddress = "The Street";
        source.setStreetname(expectedAddress);
        EpamAddressData target = new EpamAddressData();

        addressPopulator.populate(source, target);

        assertEquals("Address1 should be equals the [" + expectedAddress + "]", expectedAddress, target.getAddress1());
    }

    @Test
    public void shouldSetAddress1WhenBothStreetNameAndStreetNumberSetInSourceParameter() {
        AddressModel source = getAddressModel();
        String streetName = "The Street";
        source.setStreetname(streetName);
        String streetNumber = "42";
        source.setStreetnumber(streetNumber);
        EpamAddressData target = new EpamAddressData();
        String expectedAddress = streetName + ", " + streetNumber;

        addressPopulator.populate(source, target);

        assertEquals("Address1 should be equals the [" + expectedAddress + "]", expectedAddress, target.getAddress1());
    }

}
