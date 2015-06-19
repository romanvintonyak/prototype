package com.epam.order.converter;


import com.epam.order.converter.populator.EpamPaymentInfoPopulator;
import com.epam.order.data.EpamPaymentInfoData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamPaymentInfoConverter extends AbstractPopulatingConverter<PaymentInfoModel, EpamPaymentInfoData> {

    private EpamPaymentInfoPopulator epamPaymentInfoPopulator;

    public EpamPaymentInfoConverter(EpamPaymentInfoPopulator epamPaymentInfoPopulator) {
        this.epamPaymentInfoPopulator = epamPaymentInfoPopulator;
    }

    @Override
    protected EpamPaymentInfoData createTarget() {
        return new EpamPaymentInfoData();
    }

    @Override
    public EpamPaymentInfoData convert(PaymentInfoModel paymentInfoModel) throws ConversionException {
        EpamPaymentInfoData epamPaymentInfoData = createTarget();
        epamPaymentInfoPopulator.populate(paymentInfoModel, epamPaymentInfoData);
        return epamPaymentInfoData;
    }

}
