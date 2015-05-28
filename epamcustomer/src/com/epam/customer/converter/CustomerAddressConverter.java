package com.epam.customer.converter;

import com.epam.customer.data.CustomerAddressData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

/**
 * @author Roman_Kovalenko
 */
@Component
public class CustomerAddressConverter extends AbstractPopulatingConverter<AddressModel, CustomerAddressData> {

    @Override
    protected CustomerAddressData createTarget() {
        return new CustomerAddressData() ;
    }

    @Override
    public CustomerAddressData convert(AddressModel addressModel) throws ConversionException {
        final CustomerAddressData customerAddress = createTarget();
        customerAddress.setFirstName(addressModel.getFirstname());
        customerAddress.setLastName(addressModel.getLastname());
        customerAddress.setPhone(addressModel.getPhone1());
        customerAddress.setAddress1(addressModel.getStreetname() + ", "+ addressModel.getStreetnumber());
        customerAddress.setAddress2(null);
        customerAddress.setTown(addressModel.getTown());
        customerAddress.setPostalCode(addressModel.getPostalcode());
        customerAddress.setRegion(addressModel.getRegion()!= null ? addressModel.getRegion().getName() : null);
        customerAddress.setCountry(addressModel.getCountry()!= null ? addressModel.getCountry().getName() : null);
        customerAddress.setIsBillingAddress(addressModel.getBillingAddress());
        customerAddress.setIsDeliveryAddress(addressModel.getShippingAddress());
        return customerAddress;
    }


}
