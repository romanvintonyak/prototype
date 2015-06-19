package com.epam.order.converter.populator;

import com.epam.order.data.EpamPaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.DebitPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import org.springframework.util.Assert;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamPaymentInfoPopulator implements Populator<PaymentInfoModel, EpamPaymentInfoData> {

    @Override
    public void populate(PaymentInfoModel source, EpamPaymentInfoData target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        ItemModel itemModel = source.getOriginal();
        if (itemModel != null) {
            if (itemModel instanceof CreditCardPaymentInfoModel) {
                CreditCardPaymentInfoModel paymentInfoModel = (CreditCardPaymentInfoModel) itemModel;
                target.setType((paymentInfoModel.getType() != null ? paymentInfoModel.getType().getType() : null));
                target.setCardOwner(paymentInfoModel.getCcOwner());
                target.setAccountNumber(paymentInfoModel.getNumber());
                target.setExpirationDate(paymentInfoModel.getValidToMonth() + " " + paymentInfoModel.getValidToYear());
            }
            if (itemModel instanceof DebitPaymentInfoModel) {
                DebitPaymentInfoModel paymentInfoModel = (DebitPaymentInfoModel) itemModel;
                target.setCardOwner(paymentInfoModel.getBaOwner());
                target.setAccountNumber(paymentInfoModel.getAccountNumber());
            }
        }
    }
}
