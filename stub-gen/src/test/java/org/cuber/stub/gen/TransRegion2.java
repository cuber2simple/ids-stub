package org.cuber.stub.gen;

import com.opencsv.CSVParser;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class TransRegion2 {
    private static final CSVParser csvParser = new CSVParser();

    @Test
    public void leftRegion() throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("/work/2018/ae/2.csv"), StandardCharsets.UTF_8);
        List<String> newLines = lines.stream().map(line->{
            String result = null;
            try{
                String[] values = csvParser.parseLine(line);
                result = values[1];
            }catch (Exception e){

            }
            return result;
        }).collect(Collectors.toList());

        OpenOption[] options =
                new OpenOption[] {StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING};
        Files.write(Paths.get("/work/2018/ae/region.txt"),newLines,StandardCharsets.UTF_8, options);
    }
}
