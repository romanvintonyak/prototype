package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Roman_Kovalenko
 */
@Component // TODO Switch to XML-based configuration
@Qualifier("populator")
public class EpamAddressPopulator implements Populator<AddressModel, EpamAddressData> {

    @Override
    public void populate(final AddressModel source, final EpamAddressData target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setPk(source.getPk() != null ? source.getPk().getLong() : null);
        target.setFirstName(source.getFirstname());
        target.setLastName(source.getLastname());
        target.setAddress1(buildAddress1From(source));
        target.setPhone(source.getPhone1());
        target.setTown(source.getTown());
        target.setPostalCode(source.getPostalcode());
        target.setRegion(source.getRegion() != null ? source.getRegion().getName() : null);
        target.setCountry(source.getCountry() != null ? source.getCountry().getName() : null);
        target.setIsBillingAddress(source.getBillingAddress());
        target.setIsDeliveryAddress(source.getShippingAddress());
    }

    private String buildAddress1From(final AddressModel source) {
        if (StringUtils.hasText(source.getStreetname())) {
            return buildAddress(source);
        }
        return "";
    }

    private String buildAddress(final AddressModel source) {
        String streetName = source.getStreetname();

        if (StringUtils.hasText(source.getStreetnumber())) {
            return streetName + ", " + source.getStreetnumber();
        } else {
            return streetName;
        }
    }

}
