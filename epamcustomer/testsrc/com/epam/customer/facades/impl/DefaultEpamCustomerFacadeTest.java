package com.epam.customer.facades.impl;

import com.epam.customer.BaseTest;
import com.epam.customer.converter.EpamAddressConverter;
import com.epam.customer.converter.EpamAddressReverseConverter;
import com.epam.customer.converter.populator.EpamCustomerPopulator;
import com.epam.customer.converter.populator.EpamCustomerReversePopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@UnitTest
public class DefaultEpamCustomerFacadeTest extends BaseTest {

    @Mock
    private UserService mockUserService;

    @Mock
    private CustomerAccountService mockCustomerAccountService;

    @Mock
    private ModelService mockModelService;

    @Mock
    private EpamCustomerPopulator mockCustomerPopulator;

    @Mock
    private EpamCustomerReversePopulator mockCustomerReversePopulator;

    @Mock
    private EpamAddressConverter mockAddressConverter;

    @Mock
    private EpamAddressReverseConverter mockAddressReverseConverter;

    @Mock
    Populator<EpamAddressData, AddressModel> mockAddressReversePopulator;

    @InjectMocks
    private DefaultEpamCustomerFacade customerFacade = new DefaultEpamCustomerFacade();

    @Before
    public void setUp() {
        // TODO Add implementation
    }

}