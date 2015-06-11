package com.epam.customer.converter;

import com.epam.customer.converter.populator.EpamAddressPopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Roman_Kovalenko
 */
@Component
@Qualifier("converter")
public class EpamAddressConverter extends AbstractPopulatingConverter<AddressModel, EpamAddressData> {

    private EpamAddressPopulator customerAddressPopulator;

    @Autowired
    public EpamAddressConverter(EpamAddressPopulator customerAddressPopulator) {
        this.customerAddressPopulator = customerAddressPopulator;
    }

    @Override
    protected EpamAddressData createTarget() {
        return new EpamAddressData();
    }

    @Override
    public EpamAddressData convert(AddressModel addressModel) throws ConversionException {
        EpamAddressData customerAddressData = createTarget();
        customerAddressPopulator.populate(addressModel, customerAddressData);
        return customerAddressData;
    }

}
