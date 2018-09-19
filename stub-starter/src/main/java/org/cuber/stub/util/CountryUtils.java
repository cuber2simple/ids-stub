package org.cuber.stub.util;

import org.cuber.stub.basic.Country;
import org.cuber.stub.conf.CacheFactory;

public class CountryUtils {

    public static Country findByAlphaCode2(String alphaCode2) {
        Country country = new Country();
        country.setAlphaCode2(alphaCode2);
        return CacheFactory.findBySearch(country, Country.class);
    }

    public static Country findByAlphaCode3(String alphaCode3) {
        Country country = new Country();
        country.setAlphaCode3(alphaCode3);
        return CacheFactory.findBySearch(country, Country.class);
    }

    public static Country findByNumberCode(String numberCode) {
        Country country = new Country();
        country.setNumberCode(numberCode);
        return CacheFactory.findBySearch(country, Country.class);
    }
}
