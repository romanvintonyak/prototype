package com.epam.customer.facades.impl;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.EpamAddressConverter;
import com.epam.customer.converter.EpamAddressReverseConverter;
import com.epam.customer.converter.populator.EpamAddressReversePopulator;
import com.epam.customer.converter.populator.EpamCustomerPopulator;
import com.epam.customer.converter.populator.EpamCustomerReversePopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@UnitTest
public class DefaultEpamCustomerFacadeTest extends BaseTest {

    @Mock
    private UserService mockUserService;

    @Mock
    private CustomerAccountService mockCustomerAccountService;

    @Mock
    private ModelService mockModelService;

    @Mock
    private EpamCustomerPopulator mockEpamCustomerPopulator;

    @Mock
    private EpamCustomerReversePopulator mockEpamCustomerReversePopulator;

    @Mock
    private EpamAddressConverter mockEpamAddressConverter;

    @Mock
    private EpamAddressReverseConverter mockEpamAddressReverseConverter;

    @Mock
    private EpamAddressReversePopulator mockEpamAddressReversePopulator;

    @InjectMocks
    private DefaultEpamCustomerFacade defaultEpamCustomerFacade;

    @Before
    public void setUp() {
        // TODO Add implementation
    }


    @Test
    public void shouldThrowUnknownIdentifierExceptionWhenCustomerNotFoundById() {
        String uid = "uid";
        thrown.expect(UnknownIdentifierException.class);
        thrown.expectMessage(String.format("User with uid '{0}' not found!", uid));
        when(mockUserService.getUserForUID(uid)).thenReturn(null);

        defaultEpamCustomerFacade.findCustomerAddresses(uid);
        defaultEpamCustomerFacade.createCustomerAddress(new EpamAddressData(), uid);
        defaultEpamCustomerFacade.updateCustomerAddress(new EpamAddressData(), uid);

        verify(mockUserService, times(3)).getUserForUID(uid);
    }

    @Test
    public void shouldReturnAnEmptyListWhenCustomerHasNoAnyAddresses() {
        String uid = "uid";
        CustomerModel mockUser = mock(CustomerModel.class);
        when(mockUser.getAddresses()).thenReturn(Collections.<AddressModel>emptyList());
        when(mockUserService.getUserForUID(uid)).thenReturn(mockUser);

        List<EpamAddressData> actualEpamAddressDatas = defaultEpamCustomerFacade.findCustomerAddresses(uid);

        verify(mockUserService).getUserForUID(uid);
        verify(mockUser).getAddresses();
        assertTrue("List of EpamAddressData should be empty.", actualEpamAddressDatas.isEmpty());
    }

}