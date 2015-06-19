package com.epam.order.converter.populator;

import com.epam.order.data.EpamPaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import org.springframework.util.Assert;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamPaymentInfoReversePopulator implements Populator<EpamPaymentInfoData, PaymentInfoModel> {

    @Override
    public void populate(EpamPaymentInfoData source, PaymentInfoModel target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        throw new UnsupportedOperationException();
    }
}
