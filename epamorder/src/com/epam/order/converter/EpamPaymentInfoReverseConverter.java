package com.epam.order.converter;


import com.epam.order.converter.populator.EpamPaymentInfoReversePopulator;
import com.epam.order.data.EpamPaymentInfoData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamPaymentInfoReverseConverter extends AbstractPopulatingConverter<EpamPaymentInfoData, PaymentInfoModel> {

    private EpamPaymentInfoReversePopulator epamPaymentInfoReversePopulator;

    public EpamPaymentInfoReverseConverter(EpamPaymentInfoReversePopulator epamPaymentInfoReversePopulator) {
        this.epamPaymentInfoReversePopulator = epamPaymentInfoReversePopulator;
    }

    @Override
    protected PaymentInfoModel createTarget() {
        return new PaymentInfoModel();
    }

    @Override
    public PaymentInfoModel convert(EpamPaymentInfoData epamPaymentInfoData) throws ConversionException {
        PaymentInfoModel paymentInfoModel = createTarget();
        epamPaymentInfoReversePopulator.populate(epamPaymentInfoData, paymentInfoModel);
        return paymentInfoModel;
    }

}
