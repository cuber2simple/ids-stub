package org.cuber.stub.gen;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class TransCurrency {

    private static final Logger logger = LoggerFactory.getLogger(TransCurrency.class);
    private static final String DESC_PATH = "/work/2018/ae/currency.sql";


    @Test
    public void testInsert() throws Exception {
        Document document = Jsoup.connect("https://www.currency-iso.org/dam/downloads/lists/list_one.xml").get();
        List<String[]> list = new ArrayList<>();
        Set<String> currencySet = new HashSet<>();
        for(Element el: document.select("CcyNtry")){
            String currency = el.select("Ccy").text();
            if(!currencySet.contains(currency) && StringUtils.isNotEmpty(currency)){
                currencySet.add(currency);
                String[] oneCurrency = new String[5];
                oneCurrency[0] = currency;
                oneCurrency[1] = el.select("CcyNbr").text();
                oneCurrency[2] = el.select("CcyNm").text();
                String unit = el.select("CcyMnrUnts").text();
                if(!StringUtils.isNumeric(unit)){
                    unit = "0";
                }
                oneCurrency[3] = unit;
                oneCurrency[4] = null;
                try{
                    Currency currencyInstance = Currency.getInstance(currency);
                    oneCurrency[4] = currencyInstance.getDisplayName(Locale.CHINA);
                }catch (Exception e){
                    logger.error("错误");
                }
                list.add(oneCurrency);
                System.out.println(Arrays.asList(oneCurrency));
            }

            List<String> sqls = list.stream()
                    .map(line -> tran2Sql(line))
                    .collect(Collectors.toList());
            OpenOption[] options =
                    new OpenOption[] {StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING};
            Files.write(Paths.get(DESC_PATH),sqls,StandardCharsets.UTF_8, options);
        }
    }

    private static String tran2Sql(String[] values) {
        String result = null;
        try {
            List<String> newValues = Arrays.stream(values)
                    .map(value->"'" + StringUtils.replace(value,"\'","\'\'") + "'")
                    .collect(Collectors.toList());
            result ="INSERT INTO T_CURRENCY_4217(ID, ALPHA_CODE, NUMBER_CODE, OFFICIAL_NAME_EN, MINOR_UNIT, OFFICIAL_NAME_CN, STATUS) values ( nextval('SEQ_CURRENCY_4217')," +  Joiner.on(",").join(newValues) + ",'available');";
        } catch (Exception e) {
            logger.error("转换出错",e);
        }
        return result;
    }
}
