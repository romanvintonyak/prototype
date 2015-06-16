package com.epam.customer.converter.populator;

import com.epam.customer.BaseTest;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.core.model.user.CustomerModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@UnitTest
public class EpamCustomerReversePopulatorTest extends BaseTest {

    @Mock
    private CustomerNameStrategy mockCustomerNameStrategy;

    private EpamCustomerReversePopulator customerReversePopulator;
    private EpamCustomerData source;
    private CustomerModel target;

    @Before
    public void setUp() {
        customerReversePopulator = new EpamCustomerReversePopulator(mockCustomerNameStrategy);
        source = new EpamCustomerData();
        target = new CustomerModel();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterSourceIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter source cannot be null.");

        customerReversePopulator.populate(null, new CustomerModel());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterTargetIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter target cannot be null.");

        customerReversePopulator.populate(new EpamCustomerData(), null);
    }

    @Test
    public void shouldSetUidWhenSourceUidIsNotNull() {
        String expectedUid = "Uid";
        source.setUid(expectedUid);

        customerReversePopulator.populate(source, target);

        String errorMsg = String.format("Uid should be equals <%s>.", expectedUid);
        assertEquals(errorMsg, expectedUid, target.getUid());
    }

    @Test
    public void shouldNotSetUidWhenSourceUidIsNull() {
        customerReversePopulator.populate(source, target);

        assertNull("Uid should be null.", target.getUid());
    }

    @Test
    public void shouldSetCustomerIdWhenSourceUidIsNotNull() {
        String expectedUid = "Uid";
        source.setUid(expectedUid);

        customerReversePopulator.populate(source, target);

        String errorMsg = String.format("CustomerId should be equals <%s>.", expectedUid);
        assertEquals(errorMsg, expectedUid, target.getCustomerID());
    }

    @Test
    public void shouldNotSetCustomerIdWhenSourceUidIsNull() {
        customerReversePopulator.populate(source, target);

        assertNull("CustomerId should be null.", target.getCustomerID());
    }

    @Test
    public void shouldSetOriginalUidWhenSourceUidIsNotNull() {
        String expectedUid = "Uid";
        source.setUid(expectedUid);

        customerReversePopulator.populate(source, target);

        String errorMsg = String.format("OriginalUid should be equals <%s>.", expectedUid);
        assertEquals(errorMsg, expectedUid, target.getOriginalUid());
    }

    @Test
    public void shouldNotSetOriginalUidWhenSourceUidIsNull() {
        customerReversePopulator.populate(source, target);

        assertNull("OriginalUid should be null.", target.getOriginalUid());
    }

    @Test
    public void shouldSetNullIntoCustomerModelNameWhenEpamCustomerDataFirstNameAndLastNameAreNulls() {
        EpamCustomerData source = new EpamCustomerData();
        String firstName = "John";
        source.setFirstName(firstName);
        String lastName = "Doe";
        source.setLastName(lastName);
        String expectedName = firstName + " " + lastName;
        when(mockCustomerNameStrategy.getName(firstName, lastName)).thenReturn(expectedName);
        CustomerModel target = new CustomerModel();

        customerReversePopulator.populate(source, target);

        String errorMsg = String.format("Name should be <%s>.", expectedName);
        assertEquals(errorMsg, expectedName, target.getName());
    }

    @Test
    public void shouldSetLoginDisabledAsTrueWhenSourceActiveIsFalse() {
        boolean isActive = false;
        source.setActive(isActive);
        boolean expectedIsLoginDisabled = !isActive;

        customerReversePopulator.populate(source, target);

        String errorMsg = String.format("IsLoginDisabled should be equals <%s>.", expectedIsLoginDisabled);
        assertTrue(errorMsg, target.isLoginDisabled());
    }

    @Test
    public void shouldSetLoginDisabledAsFalseWhenSourceActiveIsTrue() {
        boolean isActive = true;
        source.setActive(isActive);
        boolean expectedIsLoginDisabled = !isActive;

        customerReversePopulator.populate(source, target);

        String errorMsg = String.format("IsLoginDisabled should be equals <%s>.", expectedIsLoginDisabled);
        assertFalse(errorMsg, target.isLoginDisabled());
    }

}