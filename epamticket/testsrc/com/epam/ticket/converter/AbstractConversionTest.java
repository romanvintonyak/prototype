package com.epam.ticket.converter;

import de.hybris.platform.servicelayer.StubLocaleProvider;
import de.hybris.platform.servicelayer.internal.model.impl.ModelValueHistory;
import de.hybris.platform.servicelayer.model.ItemContextBuilder;
import de.hybris.platform.servicelayer.model.ItemModelContext;
import de.hybris.platform.servicelayer.model.strategies.DefaultFetchStrategy;

import java.util.Locale;

public class AbstractConversionTest {

    public static final String UNEXPECTED_CONVERTED_VALUE = "Unexpected converted value";

    protected ItemModelContext ctx;

    public void setUp() throws Exception {
        ItemContextBuilder builder = new ItemContextBuilder();
        //builder.setPk(pk);
        //builder.setItemType(itemType);
        //builder.setTenantID(TENANT_ID);
        builder.setValueHistory(new ModelValueHistory());
        builder.setFetchStrategy(new DefaultFetchStrategy());
        builder.setLocaleProvider(new StubLocaleProvider(Locale.ENGLISH));
        //builder.setDynamicAttributesProvider(new MockDynamicAttributesProvider(dynamicAttributes, dynamicLocAttributes));
        //builder.setAttributeProvider(new MockAttributeProvider(attributes, locAttributes));
        ctx = builder.build();
    }
}
