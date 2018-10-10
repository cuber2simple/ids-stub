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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenSqlRegionCode {
    private static final CSVParser csvParser = new CSVParser();
    private static final String CSV_PATH = "/work/2018/ae/country_new.csv";
    private static final String DESC_PATH = "/work/2018/ae/region_code.sql";

    private static final Logger logger = LoggerFactory.getLogger(GenSqlRegionCode.class);


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
            List<String> newValues = Arrays.stream(values)
                    .map(value->"'" + StringUtils.replace(value,"\'","\'\'") + "'")
                    .collect(Collectors.toList());
            result ="INSERT INTO T_COUNTRY_REGION(ID, COUNTRY_NUMBER, OFFICIAL_NAME_EN, REGION_CODE, IS_COUNTRY, OFFICIAL_NAME_CN, IS_SYS) values (nextval('SEQ_COUNTRY_REGION')," +  Joiner.on(",").join(newValues) + ",'true');";
        } catch (Exception e) {
            logger.error("转换出错",e);
        }
        return result;
    }
}
