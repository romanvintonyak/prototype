package com.epam.customer.data;

import java.io.Serializable;

/**
 * @author Roman_Kovalenko
 */
public class CustomerAddressData implements Serializable {

    private String firstName;
    private String LastName;
    private String address1;
    private String address2;
    private String phone;
    private String town;
    private String postalCode;
    private String region;
    private String country;
    private boolean isBillingAddress;
    private boolean isPrimaryBillingAddress;
    private boolean isDeliveryAddress;
    private boolean isPrimaryDeliveryAddress;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isBillingAddress() {
        return isBillingAddress;
    }

    public void setIsBillingAddress(boolean isBillingAddress) {
        this.isBillingAddress = isBillingAddress;
    }

    public boolean isPrimaryBillingAddress() {
        return isPrimaryBillingAddress;
    }

    public void setIsPrimaryBillingAddress(boolean isPrimaryBillingAddress) {
        this.isPrimaryBillingAddress = isPrimaryBillingAddress;
    }

    public boolean isDeliveryAddress() {
        return isDeliveryAddress;
    }

    public void setIsDeliveryAddress(boolean isDeliveryAddress) {
        this.isDeliveryAddress = isDeliveryAddress;
    }

    public boolean isPrimaryDeliveryAddress() {
        return isPrimaryDeliveryAddress;
    }

    public void setIsPrimaryDeliveryAddress(boolean isPrimaryDeliveryAddress) {
        this.isPrimaryDeliveryAddress = isPrimaryDeliveryAddress;
    }
}
