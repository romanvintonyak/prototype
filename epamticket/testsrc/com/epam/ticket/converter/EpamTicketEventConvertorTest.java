package com.epam.ticket.converter;

import com.epam.ticket.data.EpamTicketEvent;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.ticket.events.model.CsTicketEventModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

@UnitTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/epamticket-spring-test.xml")
public class EpamTicketEventConvertorTest {

    public static final String UNEXPECTED_CONVERTED_VALUE = "Unexpected converted value";

    public static final Date START_DATE = new Date();
    public static final Date END_DATE = new Date();

    @Autowired
    private EpamTicketEventConverter converter;

    @Test(expected = NullPointerException.class) //start date and empty list of events should be set at least
    public void testFailConvert() {
        CsTicketEventModel source = new CsTicketEventModel();
        converter.convert(source);
    }

    @Test
    public void testConvert() {

        CsTicketEventModel source = new CsTicketEventModel();
        source.setStartDateTime(START_DATE);
        source.setEndDateTime(END_DATE);
        // CsTicketEmailModel and CsTicketChangeEventEntryModel conversion being tested in separate test,
        // so that we use an empty list of emails and empty set for entries
        source.setEmails(new ArrayList<>());
        source.setEntries(new HashSet<>());

        EpamTicketEvent target = converter.convert(source);

        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getStartDateTime(), START_DATE);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getEndDateTime(), END_DATE);
    }
}
