package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketChangeEventEntry;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.type.AttributeDescriptorModel;
import de.hybris.platform.ticket.events.model.CsTicketChangeEventEntryModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@UnitTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/epamticket-spring-test.xml")
public class TicketChangeEventEntryConversionTest extends AbstractConversionTest {

    public static final String DESCRIPTOR_NAME = "descriptor_name";
    public static final String OLD_STRING_VALUE = "old_string_value";
    public static final String NEW_STRING_VALUE = "new_string_value";
    public static final String OLD_BINARY_VALUE = "old_binary_value";
    public static final String NEW_BINARY_VALUE = "new_binary_value";

    @Autowired
    private EpamTicketChangeEventEntryConverter converter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test(expected = NullPointerException.class)
    public void testNullSourceConvert() {
        converter.convert(null);
    }

    @Test(expected = NullPointerException.class) //changed attribute should be set at least
    public void testFailConvert() {
        CsTicketChangeEventEntryModel source = new CsTicketChangeEventEntryModel();
        converter.convert(source);
    }

    @Test
    public void testConvert() {

        CsTicketChangeEventEntryModel source = new CsTicketChangeEventEntryModel();
        AttributeDescriptorModel descriptor = new AttributeDescriptorModel(ctx);
        descriptor.setName(DESCRIPTOR_NAME);
        source.setAlteredAttribute(descriptor);
        source.setOldStringValue(OLD_STRING_VALUE);
        source.setNewStringValue(NEW_STRING_VALUE);
        source.setOldBinaryValue(OLD_BINARY_VALUE);
        source.setNewBinaryValue(NEW_BINARY_VALUE);

        EpamTicketChangeEventEntry target = converter.convert(source);

        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getAlteredAttribute(), DESCRIPTOR_NAME);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getOldStringValue(), OLD_STRING_VALUE);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getNewStringValue(), NEW_STRING_VALUE);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getOldBinaryValue(), OLD_BINARY_VALUE);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getNewBinaryValue(), NEW_BINARY_VALUE);
    }
}
