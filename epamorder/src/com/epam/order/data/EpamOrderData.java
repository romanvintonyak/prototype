package com.epam.order.data;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamOrderData {
    private String code;
    private EpamCurrencyData currency;
    private String createdDate;
    private String deliveryAddressId;
    private String deliveryStatus;
    private String orderStatus;
    private EpamPaymentInfoData epamPaymentInfoData;
    private String totalPrice;
    private Boolean isFraudulent;
    private Boolean isPotentiallyFraudulent;
    private String displayOrderStatus;
    private String store;
    private String salesApplication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EpamCurrencyData getCurrency() {
        return currency;
    }

    public void setCurrency(EpamCurrencyData currency) {
        this.currency = currency;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(String deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public EpamPaymentInfoData getEpamPaymentInfoData() {
        return epamPaymentInfoData;
    }

    public void setEpamPaymentInfoData(EpamPaymentInfoData epamPaymentInfoData) {
        this.epamPaymentInfoData = epamPaymentInfoData;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsFraudulent() {
        return isFraudulent;
    }

    public void setIsFraudulent(Boolean isFraudulent) {
        this.isFraudulent = isFraudulent;
    }

    public Boolean getIsPotentiallyFraudulent() {
        return isPotentiallyFraudulent;
    }

    public void setIsPotentiallyFraudulent(Boolean isPotentiallyFraudulent) {
        this.isPotentiallyFraudulent = isPotentiallyFraudulent;
    }

    public String getDisplayOrderStatus() {
        return displayOrderStatus;
    }

    public void setDisplayOrderStatus(String displayOrderStatus) {
        this.displayOrderStatus = displayOrderStatus;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getSalesApplication() {
        return salesApplication;
    }

    public void setSalesApplication(String salesApplication) {
        this.salesApplication = salesApplication;
    }
}
