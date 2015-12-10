package com.epam.ticket.converter;

import com.epam.ticket.data.EpamCustomerEvent;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.ticket.enums.CsEventReason;
import de.hybris.platform.ticket.enums.CsInterventionType;
import de.hybris.platform.ticket.events.model.CsCustomerEventModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@UnitTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/epamticket-spring-test.xml")
public class CustomerEventConversionTest extends AbstractConversionTest {

    public static final String TEXT = "text";
    public static final CsInterventionType INTERVENTION_TYPE = CsInterventionType.CALL;
    public static final CsEventReason REASON = CsEventReason.COMPLAINT;

    @Autowired
    private CsCustomerEventConverter epamToCsConverter;

    @Test(expected = NullPointerException.class)
    public void testNullSourceConvert() {
        epamToCsConverter.convert(null);
    }

    @Test
    public void shouldConvertEpamToCsCustomerEvent() {

        EpamCustomerEvent source = new EpamCustomerEvent();
        source.setText(TEXT);
        source.setInterventionType(INTERVENTION_TYPE.getCode());
        source.setReason(REASON.getCode());

        CsCustomerEventModel target = epamToCsConverter.convert(source);

        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getText(), TEXT);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getInterventionType(), INTERVENTION_TYPE);
        assertEquals(UNEXPECTED_CONVERTED_VALUE, target.getReason(), REASON);
    }
}
