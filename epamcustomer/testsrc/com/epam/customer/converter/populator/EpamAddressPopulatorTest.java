package com.epam.customer.converter.populator;

import com.epam.customer.BaseTest;
import com.epam.customer.data.EpamAddressData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@UnitTest
public class EpamAddressPopulatorTest extends BaseTest {

    private EpamAddressPopulator addressPopulator = new EpamAddressPopulator();
    private AddressModel source;
    private EpamAddressData target;

    @Before
    public void setUp() {
        source = getAddressModel();
        target = new EpamAddressData();
    }

    private AddressModel getAddressModel() {
        if (source == null) {
            source = new AddressModel();
            // Following fields are not expected to have a null values. See appropriate getters in the AddressModel.java
            source.setBillingAddress(true);
            source.setShippingAddress(true);
        }
        return source;
    }

    @After
    public void tearDown() {
        source = null;
        target = null;
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterSourceIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter source cannot be null.");

        addressPopulator.populate(null, target);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterTargetIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter target cannot be null.");

        addressPopulator.populate(source, null);
    }

    @Test
    public void shouldSetPkWhenSourcePkIsNotNull() {
        AddressModel source = Mockito.spy(new AddressModel());
        when(source.getBillingAddress()).thenReturn(true);
        when(source.getShippingAddress()).thenReturn(true);
        Long expectedLongPkValue = 42L;
        when(source.getPk()).thenReturn(PK.fromLong(expectedLongPkValue));

        addressPopulator.populate(source, target);

        String errorMsg = String.format("Pk should be equals <%d>.", expectedLongPkValue);
        assertEquals(errorMsg, expectedLongPkValue, target.getPk());
    }

    @Test
    public void shouldNotSetPkWhenSourcePkIsNull() {
        AddressModel source = Mockito.spy(new AddressModel());
        when(source.getBillingAddress()).thenReturn(true);
        when(source.getShippingAddress()).thenReturn(true);
        when(source.getPk()).thenReturn(null);

        addressPopulator.populate(source, target);

        assertNull("Pk should be null.", target.getPk());
    }

    @Test
    public void shouldSetFirstNameWhenSourceFirstNameIsNotNull() {
        String expectedFirstName = "John";
        source.setFirstname(expectedFirstName);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("FirstName should be equals <%s>.", expectedFirstName);
        assertEquals(errorMsg, expectedFirstName, target.getFirstName());
    }

    @Test
    public void shouldNotSetFirstNameWhenSourceFirstNameIsNull() {
        addressPopulator.populate(source, target);

        assertNull("FirstName should be null.", target.getFirstName());
    }

    @Test
    public void shouldSetLastNameWhenSourceLastNameIsNotNull() {
        String expectedLastName = "Dou";
        source.setLastname(expectedLastName);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("LastName should be equals <%s>.", expectedLastName);
        assertEquals(errorMsg, expectedLastName, target.getLastName());
    }

    @Test
    public void shouldNotSetLastNameWhenSourceLastNameIsNull() {
        addressPopulator.populate(source, target);

        assertNull("LastName should be null.", target.getLastName());
    }

    @Test
    public void shouldSetEmptyAddress1WhenStreetNameIsAbsentInSourceParameter() {
        addressPopulator.populate(source, target);

        assertTrue("Address1 should be an empty string.", target.getAddress1().isEmpty());
    }

    @Test
    public void shouldSetAddress1WhenOnlyStreetNameSetInSourceParameter() {
        String expectedAddress = "The Street";
        source.setStreetname(expectedAddress);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("Address1 should be equals the <%s>.", expectedAddress);
        assertEquals(errorMsg, expectedAddress, target.getAddress1());
    }

    @Test
    public void shouldSetAddress1WhenBothStreetNameAndStreetNumberSetInSourceParameter() {
        String streetName = "The Street";
        source.setStreetname(streetName);
        String streetNumber = "42";
        source.setStreetnumber(streetNumber);
        String expectedAddress = streetName + ", " + streetNumber;

        addressPopulator.populate(source, target);

        String errorMsg = String.format("Address1 should be equals the <%s>.", expectedAddress);
        assertEquals(errorMsg, expectedAddress, target.getAddress1());
    }

    @Test
    public void shouldSetPhoneWhenSourcePhone1IsNotNull() {
        String expectedPhone = "+123 456-78-90";
        source.setPhone1(expectedPhone);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("Phone should be equals <%s>.", expectedPhone);
        assertEquals(errorMsg, expectedPhone, target.getPhone());
    }

    @Test
    public void shouldNotSetPhoneWhenSourcePhone1IsNull() {
        addressPopulator.populate(source, target);

        assertNull("Phone should be null.", target.getPhone());
    }

    @Test
    public void shouldSetTownWhenSourceTownIsNotNull() {
        String expectedTown = "Ottawa";
        source.setTown(expectedTown);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("Town should be equals <%s>.", expectedTown);
        assertEquals(errorMsg, expectedTown, target.getTown());
    }

    @Test
    public void shouldNotSetTownWhenSourceTownIsNull() {
        addressPopulator.populate(source, target);

        assertNull("Town should be null.", target.getTown());
    }

    @Test
    public void shouldSetPostalCodeWhenSourcePostalCodeIsNotNull() {
        String expectedPostalCode = "K1A 0B1";
        source.setPostalcode(expectedPostalCode);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("PostalCode should be equals <%s>.", expectedPostalCode);
        assertEquals(errorMsg, expectedPostalCode, target.getPostalCode());
    }

    @Test
    public void shouldNotSetPostalCodeWhenSourcePostalCodeIsNull() {
        addressPopulator.populate(source, target);

        assertNull("PostalCode should be null.", target.getPostalCode());
    }

    @Test
    public void shouldSetRegionWhenSourceRegionIsNotNull() {
        String expectedRegion = "Quebec City";
        RegionModel mockRegionModel = mock(RegionModel.class);
        when(mockRegionModel.getName()).thenReturn(expectedRegion);
        source.setRegion(mockRegionModel);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("Region should be equals <%s>.", expectedRegion);
        assertEquals(errorMsg, expectedRegion, target.getRegion());
    }

    @Test
    public void shouldNotSetRegionWhenSourceRegionIsNull() {
        addressPopulator.populate(source, target);

        assertNull("Region should be null.", target.getRegion());
    }

    @Test
    public void shouldSetCountryWhenSourceCountryIsNotNull() {
        String expectedCountry = "Canada";
        CountryModel mockCountryModel = mock(CountryModel.class);
        when(mockCountryModel.getName()).thenReturn(expectedCountry);
        source.setCountry(mockCountryModel);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("Country should be equals <%s>.", expectedCountry);
        assertEquals(errorMsg, expectedCountry, target.getCountry());
    }

    @Test
    public void shouldNotSetCountryWhenSourceCountryIsNull() {
        addressPopulator.populate(source, target);

        assertNull("Country should be null.", target.getCountry());
    }

    @Test
    public void shouldSetIsBillingAddressAsTrueWhenSourceBillingAddressIsTrue() {
        Boolean expectedBillingAddress = true;
        source.setBillingAddress(expectedBillingAddress);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("PostalCode should be equals <%b>.", expectedBillingAddress);
        assertEquals(errorMsg, expectedBillingAddress, target.isBillingAddress());
    }

    @Test
    public void shouldNotSetIsBillingAddressAsFalseWhenSourceBillingAddressIsFalse() {
        Boolean expectedBillingAddress = false;
        source.setBillingAddress(expectedBillingAddress);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("PostalCode should be equals <%b>.", expectedBillingAddress);
        assertEquals(errorMsg, expectedBillingAddress, target.isBillingAddress());
    }

    @Test
    public void shouldSetIsDeliveryAddressAsTrueWhenSourceShippingAddressIsTrue() {
        Boolean expectedDeliveryAddress = true;
        source.setShippingAddress(expectedDeliveryAddress);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("DeliveryAddress should be equals <%b>.", expectedDeliveryAddress);
        assertEquals(errorMsg, expectedDeliveryAddress, target.isDeliveryAddress());
    }

    @Test
    public void shouldNotSetIsDeliveryAddressAsFalseWhenSourceShippingAddressIsFalse() {
        Boolean expectedDeliveryAddress = false;
        source.setShippingAddress(expectedDeliveryAddress);

        addressPopulator.populate(source, target);

        String errorMsg = String.format("DeliveryAddress should be equals <%b>.", expectedDeliveryAddress);
        assertEquals(errorMsg, expectedDeliveryAddress, target.isDeliveryAddress());
    }

}
