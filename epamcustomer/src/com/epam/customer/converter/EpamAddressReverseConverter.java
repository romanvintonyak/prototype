package com.epam.customer.converter;

import com.epam.customer.converter.populator.EpamAddressReversePopulator;
import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

/**
 * @author Roman_Kovalenkol
 */
public class EpamAddressReverseConverter extends AbstractPopulatingConverter<EpamAddressData, AddressModel> {

    private EpamAddressReversePopulator epamAddressReversePopulator;

    public EpamAddressReverseConverter(EpamAddressReversePopulator epamAddressReversePopulator) {
        this.epamAddressReversePopulator = epamAddressReversePopulator;
    }

    @Override
    public AddressModel convert(EpamAddressData addressData) throws ConversionException {
        AddressModel addressModel = new AddressModel();
        epamAddressReversePopulator.populate(addressData, addressModel);
        return addressModel;
    }
}
