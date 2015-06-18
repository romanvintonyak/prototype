package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamAddressData;
import com.google.common.base.Strings;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import org.springframework.util.Assert;

/**
 * @author Roman_Kovalenko
 */
public class EpamAddressReversePopulator implements Populator<EpamAddressData, AddressModel> {

    @Override
    public void populate(final EpamAddressData source, final AddressModel target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setFirstname(source.getFirstName());
        target.setLastname(source.getLastName());
        setStreetNameAndStreetNumber(source, target);
        target.setPhone1(source.getPhone());
        target.setTown(source.getTown());
        target.setPostalcode(source.getPostalCode());
        // TODO How to implement this correctly? target.setRegion(source.getRegion());
        // TODO How to implement this correctly? target.setCountry(source.getCountry());
        // TODO What is the default value? target.setBillingAddress(source.isBillingAddress());
        // TODO What is the default value? target.setShippingAddress(source.isDeliveryAddress());
    }

    private void setStreetNameAndStreetNumber(final EpamAddressData source, final AddressModel target) {
        String addressDetails = source.getAddress1();
        if (Strings.isNullOrEmpty(addressDetails)) {
            return;
        }

        String[] addressDetailsArray = addressDetails.split(", ");
        throwExceptionIfAddressDetailsArrayHasLessOrMoreThanTwoTokens(addressDetailsArray);
        target.setStreetname(addressDetailsArray[0]);
        target.setStreetnumber(addressDetailsArray[1]);
    }

    private void throwExceptionIfAddressDetailsArrayHasLessOrMoreThanTwoTokens(final String[] addressDetailsArray) {
        if (addressDetailsArray.length < 2) {
            throw new IllegalArgumentException("Address1 cannot be split into StreetName and StreetNumber because it contains less than 2 tokens.");
        }

        if (addressDetailsArray.length > 2) {
            throw new IllegalArgumentException("Address1 cannot be split into StreetName and StreetNumber because it contains more than 2 tokens.");
        }
    }
}
