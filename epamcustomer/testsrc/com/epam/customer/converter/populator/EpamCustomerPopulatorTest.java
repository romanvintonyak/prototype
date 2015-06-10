package com.epam.customer.converter.populator;

import com.epam.customer.BaseTest;
import com.epam.customer.data.EpamCustomerData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.core.model.user.CustomerModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@UnitTest
public class EpamCustomerPopulatorTest extends BaseTest {

    @Mock
    private CustomerNameStrategy mockCustomerNameStrategy;

    private EpamCustomerPopulator customerPopulator;

    @Before
    public void setUp() {
        customerPopulator = new EpamCustomerPopulator(mockCustomerNameStrategy);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterSourceIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter source cannot be null.");

        customerPopulator.populate(null, new EpamCustomerData());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenParameterTargetIsNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Parameter target cannot be null.");

        customerPopulator.populate(new CustomerModel(), null);
    }

    @Test
    public void shouldSetNullsIntoEpamCustomerDataFirstNameAndLastNameWhenCustomerNameIsNull() {
        CustomerModel source = new CustomerModel();
        source.setCreationtime(new Date());
        EpamCustomerData target = new EpamCustomerData();
        when(mockCustomerNameStrategy.splitName(anyString())).thenReturn(null);

        customerPopulator.populate(source, target);

        assertNull("FirstName should be null", target.getFirstName());
        assertNull("LastName should be null", target.getLastName());
    }

    @Test
    public void shouldSetEpamCustomerDataFirstNameAndLastName() {
        CustomerModel source = new CustomerModel();
        source.setCreationtime(new Date());
        EpamCustomerData target = new EpamCustomerData();
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        String[] names = {expectedFirstName, expectedLastName};
        when(mockCustomerNameStrategy.splitName(anyString())).thenReturn(names);

        customerPopulator.populate(source, target);

        assertEquals("FirstName should be [" + expectedFirstName + "].", expectedFirstName, target.getFirstName());
        assertEquals("FirstName should be [" + expectedLastName + "].", expectedLastName, target.getLastName());
    }

}