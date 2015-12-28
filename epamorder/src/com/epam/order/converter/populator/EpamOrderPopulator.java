package com.epam.order.converter.populator;

import com.epam.order.data.EpamCurrencyData;
import com.epam.order.data.EpamOrderData;
import com.epam.order.data.EpamPaymentInfoData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.OrderModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * @author Iaroslav_Bezhenar
 */
@Component
public class EpamOrderPopulator implements Populator<OrderModel, EpamOrderData> {

    EpamCurrencyPopulator epamCurrencyPopulator;
    EpamPaymentInfoPopulator epamPaymentInfoPopulator;

    @Override
    public void populate(final OrderModel source, final EpamOrderData target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        EpamCurrencyData epamCurrencyData = new EpamCurrencyData();
        EpamPaymentInfoData epamPaymentInfoData = new EpamPaymentInfoData();
        Format formatter = new SimpleDateFormat("MMM dd - KK:mm a");

        target.setCode(source.getCode());

        if (source.getCurrency() != null)
            epamCurrencyPopulator.populate(source.getCurrency(), epamCurrencyData);
        target.setCurrency(epamCurrencyData);

        target.setCreatedDate(formatter.format(source.getCreationtime()));
        Long deliveryAddressId = source.getDeliveryAddress() != null ? source.getDeliveryAddress().getPk().getLong() : null;
        target.setDeliveryAddressId(String.valueOf(deliveryAddressId));

        target.setDeliveryStatus(source.getDeliveryStatus() != null ? source.getDeliveryStatus().getCode() != null ? source.getDeliveryStatus().getCode() : null : null);
        target.setOrderStatus(source.getStatus() != null ? source.getStatus().getCode() != null ? source.getStatus().getCode() : null : null);

        if (source.getPaymentInfo() != null)
            epamPaymentInfoPopulator.populate(source.getPaymentInfo(), epamPaymentInfoData);
        target.setEpamPaymentInfoData(epamPaymentInfoData);

        target.setTotalPrice(String.valueOf(source.getTotalPrice()));
        target.setIsFraudulent(source.getFraudulent());
        target.setIsPotentiallyFraudulent(source.getPotentiallyFraudulent());
        target.setDisplayOrderStatus(source.getStatusDisplay());
        target.setStore(source.getStore() != null ? source.getStore().getName() != null ? source.getStore().getName() : null : null);
        target.setSalesApplication(source.getSalesApplication() != null ? source.getSalesApplication().getCode() != null ? source.getSalesApplication().getCode() : null : null);
    }

    public void setEpamCurrencyPopulator(EpamCurrencyPopulator epamCurrencyPopulator) {
        this.epamCurrencyPopulator = epamCurrencyPopulator;
    }

    public void setEpamPaymentInfoPopulator(EpamPaymentInfoPopulator epamPaymentInfoPopulator) {
        this.epamPaymentInfoPopulator = epamPaymentInfoPopulator;
    }
}
