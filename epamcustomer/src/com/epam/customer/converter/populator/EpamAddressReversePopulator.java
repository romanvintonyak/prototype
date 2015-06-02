package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Roman_Kovalenko
 */
@Component
@Qualifier("reversePopulator")
public class EpamAddressReversePopulator implements Populator<EpamAddressData, AddressModel> {

    @Override
    public void populate(final EpamAddressData source, final AddressModel target) {
        //target.setCountry(source.getCountry());
        target.setFirstname(source.getFirstName());
        target.setLastname(source.getLastName());
        target.setTown(source.getCountry());
        //target.setRegion(source.getRegion());
        target.setPostalcode(source.getPostalCode());
        target.setPhone1(source.getPhone());
        source.getAddress1();
        source.getAddress2();
        //todo
    }
}