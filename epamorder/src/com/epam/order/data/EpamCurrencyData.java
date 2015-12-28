package com.epam.order.data;

import java.util.Currency;

/**
 * @author Iaroslav_Bezhenar
 */
public class EpamCurrencyData {
    private String isoCode;
    private String symbol;
    private String displayName;

    private void initDisplayNameAfterIsoCodeSet() {
        displayName = Currency.getInstance(isoCode).getDisplayName();
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
        initDisplayNameAfterIsoCodeSet();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
