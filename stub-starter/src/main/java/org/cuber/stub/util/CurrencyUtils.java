package org.cuber.stub.util;

import org.cuber.stub.basic.Currency;
import org.cuber.stub.conf.CacheFactory;

public class CurrencyUtils {

    public static Currency findByAlphaCode(String alphaCode) {
        Currency currency = new Currency();
        currency.setAlphaCode(alphaCode);
        return CacheFactory.findBySearch(currency, Currency.class);
    }

    public static Currency findByNumberCode(String numberCode) {
        Currency currency = new Currency();
        currency.setNumberCode(numberCode);
        return CacheFactory.findBySearch(currency, Currency.class);
    }
}
