package com.epam.order.facades;

import java.io.Serializable;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamOrderSearchCriteria implements Serializable {
    private String orderCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String town;
    private String region;
    private String postalCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "EpamOrderSearchCriteria{" +
                "orderCode='" + orderCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", town='" + town + '\'' +
                ", region='" + region + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
