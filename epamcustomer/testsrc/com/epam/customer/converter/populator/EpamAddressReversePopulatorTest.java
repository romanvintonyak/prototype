package com.epam.customer.converter.populator;

import com.epam.customer.BaseTest;
import com.epam.customer.data.EpamAddressData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.AddressModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@UnitTest
public class EpamAddressReversePopulatorTest extends BaseTest {

    private EpamAddressReversePopulator epamAddressReversePopulator = new EpamAddressReversePopulator();
    private EpamAddressData source;
    private AddressModel target;

    @Before
    public void setUp() {
        source = new EpamAddressData();
        target = new AddressModel();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterSourceIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter source cannot be null.");

        epamAddressReversePopulator.populate(null, target);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterTargetIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter target cannot be null.");

        epamAddressReversePopulator.populate(source, null);
    }

    @Test
    public void shouldSetFirstNameWhenSourceFirstNameIsNotNull() {
        String expectedFirstName = "John";
        source.setFirstName(expectedFirstName);

        epamAddressReversePopulator.populate(source, target);

        String errorMsg = String.format("FirstName should be equals <%s>.", expectedFirstName);
        assertEquals(errorMsg, expectedFirstName, target.getFirstname());
    }

    @Test
    public void shouldNotSetFirstNameWhenSourceFirstNameIsNull() {
        epamAddressReversePopulator.populate(source, target);

        assertNull("FirstName should be null.", target.getFirstname());
    }

    @Test
    public void shouldSetLastNameWhenSourceLastNameIsNotNull() {
        String expectedLastName = "Dou";
        source.setLastName(expectedLastName);

        epamAddressReversePopulator.populate(source, target);

        String errorMsg = String.format("LastName should be equals <%s>.", expectedLastName);
        assertEquals(errorMsg, expectedLastName, target.getLastname());
    }

    @Test
    public void shouldNotSetLastNameWhenSourceLastNameIsNull() {
        epamAddressReversePopulator.populate(source, target);

        assertNull("LastName should be null.", target.getLastname());
    }

    @Test
    public void shouldNotSetStreetNameAndStreetNumberWhenAddress1IsNull() {
        source.setAddress1(null);

        epamAddressReversePopulator.populate(source, target);

        assertNull("StreetName should be null.", target.getStreetname());
        assertNull("StreetNumber should be null.", target.getStreetnumber());
    }

    @Test
    public void shouldNotSetStreetNameAndStreetNumberWhenAddress1IsEmpty() {
        source.setAddress1("");

        epamAddressReversePopulator.populate(source, target);

        assertNull("StreetName should be null.", target.getStreetname());
        assertNull("StreetNumber should be null.", target.getStreetnumber());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddress1DoesNotContainStreetNameOrStreetNumber() {
        source.setAddress1("Only One Value");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Address1 cannot be split into StreetName and StreetNumber because it contains less than 2 tokens.");

        epamAddressReversePopulator.populate(source, target);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddress1ContainsMorePartsThanStreetNameAndStreetNumber() {
        source.setAddress1("Street Name, 42, A");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Address1 cannot be split into StreetName and StreetNumber because it contains more than 2 tokens.");

        epamAddressReversePopulator.populate(source, target);
    }

    @Test
    public void shouldSetPhoneWhenSourcePhone1IsNotNull() {
        String expectedPhone = "+123 456-78-90";
        source.setPhone(expectedPhone);

        epamAddressReversePopulator.populate(source, target);

        String errorMsg = String.format("Phone should be equals <%s>.", expectedPhone);
        assertEquals(errorMsg, expectedPhone, target.getPhone1());
    }

    @Test
    public void shouldNotSetPhoneWhenSourcePhone1IsNull() {
        epamAddressReversePopulator.populate(source, target);

        assertNull("Phone should be null.", target.getPhone1());
    }

    @Test
    public void shouldSetTownWhenSourceTownIsNotNull() {
        String expectedTown = "Ottawa";
        source.setTown(expectedTown);

        epamAddressReversePopulator.populate(source, target);

        String errorMsg = String.format("Town should be equals <%s>.", expectedTown);
        assertEquals(errorMsg, expectedTown, target.getTown());
    }

    @Test
    public void shouldNotSetTownWhenSourceTownIsNull() {
        epamAddressReversePopulator.populate(source, target);

        assertNull("Town should be null.", target.getTown());
    }

    @Test
    public void shouldSetPostalCodeWhenSourcePostalCodeIsNotNull() {
        String expectedPostalCode = "K1A 0B1";
        source.setPostalCode(expectedPostalCode);

        epamAddressReversePopulator.populate(source, target);

        String errorMsg = String.format("PostalCode should be equals <%s>.", expectedPostalCode);
        assertEquals(errorMsg, expectedPostalCode, target.getPostalcode());
    }

    @Test
    public void shouldNotSetPostalCodeWhenSourcePostalCodeIsNull() {
        epamAddressReversePopulator.populate(source, target);

        assertNull("PostalCode should be null.", target.getPostalcode());
    }

}