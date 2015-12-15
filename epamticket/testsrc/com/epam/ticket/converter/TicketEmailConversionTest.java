package com.epam.ticket.converter;

import com.epam.dto.EpamTicketEmail;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.ticket.events.model.CsTicketEmailModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@UnitTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/epamticket-spring-test.xml")
public class TicketEmailConversionTest extends AbstractConversionTest {

    public static final String MESSAGE_ID = "message_id";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String SUBJECT = "subject";
    public static final String BODY = "body";

    @Autowired
    private EpamTicketEmailConverter converter;

    @Test(expected = NullPointerException.class)
    public void testNullSourceConvert() {
        converter.convert(null);
    }

    @Test
    public void testConverter() {

        CsTicketEmailModel source = new CsTicketEmailModel();
        source.setMessageId(MESSAGE_ID);
        source.setFrom(FROM);
        source.setTo(TO);
        source.setSubject(SUBJECT);
        source.setBody(BODY);

        EpamTicketEmail target = converter.convert(source);

        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getMessageId(), MESSAGE_ID);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getFrom(), FROM);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getTo(), TO);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getSubject(), SUBJECT);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getBody(), BODY);
    }
}
