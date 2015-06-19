package com.epam.customer.converter.populator;

import com.epam.customer.data.EpamAddressData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Roman_Kovalenko
 */
public class EpamAddressPopulator implements Populator<AddressModel, EpamAddressData> {
    private static final String ADDRESS_DELIMITER = ", ";

    @Override
    public void populate(final AddressModel source, final EpamAddressData target) {
        Assert.notNull(source, "Parameter source cannot be null.");
        Assert.notNull(target, "Parameter target cannot be null.");

        target.setPk(source.getPk() != null ? source.getPk().getLong() : null);
        target.setFirstName(source.getFirstname());
        target.setLastName(source.getLastname());
        target.setAddress1(buildAddress(source));
        target.setPhone(source.getPhone1());
        target.setTown(source.getTown());
        target.setPostalCode(source.getPostalcode());
        target.setRegion(source.getRegion() != null ? source.getRegion().getName() : null);
        target.setCountry(source.getCountry() != null ? source.getCountry().getName() : null);
        target.setIsBillingAddress(source.getBillingAddress());
        target.setIsDeliveryAddress(source.getShippingAddress());
    }

    private String buildAddress(final AddressModel source) {
        StringBuilder address = new StringBuilder();

        if (StringUtils.hasText(source.getCompany())) {
            address.append(source.getCompany()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getDepartment())) {
            address.append(source.getDepartment()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getPobox())) {
            address.append(source.getPobox()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getStreetnumber())) {
            address.append(source.getStreetnumber()).append(" ");
        }
        if (StringUtils.hasText(source.getStreetname())) {
            address.append(source.getStreetname());
        }
        if (StringUtils.hasText(source.getAppartment())) {
            address.append("APT ").append(source.getAppartment());
        }
        if (StringUtils.hasText(source.getStreetnumber()) || StringUtils.hasText(source.getStreetname()) ||
                StringUtils.hasText(source.getAppartment())) {
            address.append(source.getPobox()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getDistrict())) {
            address.append(source.getDistrict()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getTown())) {
            address.append(source.getDistrict()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getLine1())) {
            address.append(source.getLine1()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getLine2())) {
            address.append(source.getLine2()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getRegion()!= null ? source.getRegion().getName() : null)) {
            address.append(source.getRegion().getName()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getPostalcode())) {
            address.append(source.getPostalcode()).append(ADDRESS_DELIMITER);
        }
        if (StringUtils.hasText(source.getCountry()!= null ? source.getCountry().getName() : null)) {
            address.append(source.getCountry().getName());
        }
        return address.toString();
    }

}
