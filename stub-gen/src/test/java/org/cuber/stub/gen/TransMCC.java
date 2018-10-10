package org.cuber.stub.gen;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TransMCC {

    @Test
    public void merge() throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("/work/2018/ae/3.csv"), StandardCharsets.UTF_8);
        List<String> zh_lines = Files.readAllLines(Paths.get("/work/2018/ae/4.txt"), StandardCharsets.UTF_8);
        List<String> newLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String newLine = lines.get(i) + "," + zh_lines.get(i);
            newLines.add(newLine);
        }
        OpenOption[] options =
                new OpenOption[] {StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING};
        Files.write(Paths.get("/work/2018/ae/country_new.csv"),newLines,StandardCharsets.UTF_8, options);
    }
}
