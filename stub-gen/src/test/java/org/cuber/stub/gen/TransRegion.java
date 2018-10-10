package org.cuber.stub.gen;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransRegion {

    public static final ThreadLocal<String> PRE_COUNTRY_CODE = new ThreadLocal<>();

    @Test
    public void test() throws Exception {
        Stream<String> lines = Files.lines(Paths.get("/work/2018/ae/2.csv"));
        List<String> regionLines = new ArrayList<>();
        List<String> newLines = lines.map(line -> {
            String empty_ = StringUtils.replace(line, ",", " ");
            String newLine = StringUtils.replace(empty_, " ", ",", 1);
            String reverseLine = StringUtils.reverse(newLine);
            String reverseLineNew = StringUtils.replace(reverseLine, " ", ",", 1);
            newLine = StringUtils.reverse(reverseLineNew);
            String[] values = StringUtils.split(newLine,",");
            regionLines.add(values[1]);
            String curCountryCode = StringUtils.substringBefore(line, " ");
            if (!curCountryCode.equals(PRE_COUNTRY_CODE.get())) {
                PRE_COUNTRY_CODE.set(curCountryCode);
                newLine = newLine + ",true";
            } else {
                newLine = newLine + ",false";
            }
            return newLine;
        }).collect(Collectors.toList());
        OpenOption[] options =
                new OpenOption[]{StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING};
        Files.write(Paths.get("/work/2018/ae/3.csv"), newLines, StandardCharsets.UTF_8, options);
        Files.write(Paths.get("/work/2018/ae/4.csv"), regionLines, StandardCharsets.UTF_8, options);
    }
}
