package org.cuber.stub.gen;

import com.google.common.base.Joiner;
import com.opencsv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountrySql {
    private static final CSVParser csvParser = new CSVParser();
    private static final String CSV_PATH = "/work/home_work/country-codes/data/country-codes.csv";
    private static final String DESC_PATH = "/work/2018/ae/country.sql";

    private static final Logger logger = LoggerFactory.getLogger(GenSqlMCC.class);


    @Test
    public void testInsert() throws Exception {
        Stream<String> lines = Files.lines(Paths.get(CSV_PATH), StandardCharsets.UTF_8);
        List<String> sqls = lines
                .map(line -> tran2Sql(line))
                .collect(Collectors.toList());
        OpenOption[] options =
                new OpenOption[] {StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING};
        Files.write(Paths.get(DESC_PATH),sqls,StandardCharsets.UTF_8, options);
    }

    private static String tran2Sql(String line) {
        String result = null;
        try {
            String[] values = csvParser.parseLine(line);
            List<String> colValues = new ArrayList<>();
            colValues.add(values[0]);//FIFA
            colValues.add(values[1]);//DIAL
            colValues.add(values[2]);//ALPHA_CODE_3
            colValues.add(values[3]);//MARC
            colValues.add(values[4]);//IS_INDEPENDENT
            colValues.add(values[5]);//NUMBER_CODE
            colValues.add(values[6]);//GAUL
            colValues.add(values[7]);//FIPS
            colValues.add(values[8]);//WMO
            colValues.add(values[9]);//ALPHA_CODE_2
            colValues.add(values[10]);//ITU
            colValues.add(values[11]);//IOC
            colValues.add(values[12]);//DS
            colValues.add(values[14]);//GLOBAL_CODE
            colValues.add(values[15]);//INTERMEDIATE_REGION_CODE
            colValues.add(values[19]);//DEVELOP_TYPE
            colValues.add(values[22]);//UNIVERSAL_CURRENCY
            colValues.add(values[23]);//SMALL_ISLAND_DEVELOPING_STATUS
            colValues.add(values[29]);//M49
            colValues.add(values[30]);//SUB_REGION_CODE
            colValues.add(values[31]);//REGION_CODE
            colValues.add(values[36]);//LAND_LOCKED_DEVELOPING_COUNTRIES
            colValues.add(values[37]);//INTERMEDIATE_REGION_NAME
            colValues.add(values[40]);//OFFICIAL_NAME_CN
            colValues.add(values[41]);//OFFICIAL_NAME_EN
            colValues.add(values[42]);//ISO_3166_NAME
            colValues.add(values[43]);//LASTED_DEVELOPED_COUNTRIES
            colValues.add(values[44]);//REGION_NAME
            colValues.add(values[46]);//SUB_REGION_NAME
            colValues.add(values[48]);//GLOBAL_NAME
            colValues.add(values[49]);//CAPITAL
            colValues.add(values[50]);//CONTINENT
            colValues.add(values[51]);//TLD
            colValues.add(values[52]);//LANGUAGES
            colValues.add(values[53]);//GEONAME_ID
            colValues.add(values[54]);//CLDR_DISPLAY_NAME
            colValues.add(values[55]);//DEVELOP_TYPE



            List<String> newValues = colValues.stream()
                    .map(value->"'" + StringUtils.replace(value,"\'","\'\'") + "'")
                    .collect(Collectors.toList());
            result ="INSERT INTO T_COUNTRY_3166" +
                    "(ID, FIFA, DIAL, ALPHA_CODE_3, MARC, IS_INDEPENDENT, NUMBER_CODE, GAUL, FIPS, WMO, ALPHA_CODE_2, ITU, IOC, DS, GLOBAL_CODE, INTERMEDIATE_REGION_CODE, DEVELOP_TYPE," +
                    "UNIVERSAL_CURRENCY, SMALL_ISLAND_DEVELOPING_STATUS, M49, SUB_REGION_CODE, REGION_CODE, LAND_LOCKED_DEVELOPING_COUNTRIES, INTERMEDIATE_REGION_NAME, OFFICIAL_NAME_CN," +
                    "OFFICIAL_NAME_EN,  ISO_3166_NAME, LASTED_DEVELOPED_COUNTRIES, REGION_NAME, SUB_REGION_NAME, GLOBAL_NAME, CAPITAL, CONTINENT, TLD, LANGUAGES, GEONAME_ID, CLDR_DISPLAY_NAME, EDGAR, STATUS) " +
                    "values ( nextval('SEQ_COUNTRY_3166')," +  Joiner.on(",").join(newValues) + ",'available');";
        } catch (Exception e) {
            logger.error("转换出错",e);
        }
        return result;
    }
}
