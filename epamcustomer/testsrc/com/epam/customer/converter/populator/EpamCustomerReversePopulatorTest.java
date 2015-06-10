package com.epam.customer.converter.populator;

import com.epam.customer.BaseTest;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.core.model.user.CustomerModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@UnitTest
public class EpamCustomerReversePopulatorTest extends BaseTest {

    @Mock
    private CustomerNameStrategy mockCustomerNameStrategy;

    private EpamCustomerReversePopulator customerReversePopulator;

    @Before
    public void setUp() {
        customerReversePopulator = new EpamCustomerReversePopulator(mockCustomerNameStrategy);
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

        assertEquals("Name should be [" + expectedName + "].", expectedName, target.getName());
    }

}