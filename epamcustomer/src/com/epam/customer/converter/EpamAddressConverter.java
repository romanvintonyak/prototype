package com.epam.customer.converter;

import com.epam.customer.converter.populator.EpamAddressPopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Roman_Kovalenko
 */
public class EpamAddressConverter extends AbstractPopulatingConverter<AddressModel, EpamAddressData> {

    private EpamAddressPopulator epamAddressPopulator;

    public EpamAddressConverter(EpamAddressPopulator epamAddressPopulator) {
        this.epamAddressPopulator = epamAddressPopulator;
    }

    @Override
    public EpamAddressData convert(AddressModel addressModel) throws ConversionException {
        EpamAddressData customerAddressData = new EpamAddressData();
        epamAddressPopulator.populate(addressModel, customerAddressData);
        return customerAddressData;
    }

}
