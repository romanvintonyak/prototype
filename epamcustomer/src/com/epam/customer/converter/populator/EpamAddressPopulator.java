package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import org.springframework.stereotype.Component;

/**
 * @author Roman_Kovalenko
 */
@Component
public class EpamAddressPopulator implements Populator<AddressModel, EpamAddressData> {

    @Override
    public void populate(final AddressModel source, final EpamAddressData target) {
        target.setPk(source.getPk().getLong());
        target.setFirstName(source.getFirstname());
        target.setLastName(source.getLastname());
        target.setPhone(source.getPhone1());
        target.setAddress1(source.getStreetname() + ", "+ source.getStreetnumber());
        target.setAddress2(null);
        target.setTown(source.getTown());
        target.setPostalCode(source.getPostalcode());
        target.setRegion(source.getRegion()!= null ? source.getRegion().getName() : null);
        target.setCountry(source.getCountry()!= null ? source.getCountry().getName() : null);
        target.setIsBillingAddress(source.getBillingAddress());
        target.setIsDeliveryAddress(source.getShippingAddress());
    }
}
