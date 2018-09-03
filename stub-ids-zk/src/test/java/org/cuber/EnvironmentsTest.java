package org.cuber;

import org.junit.Test;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Map;

public class EnvironmentsTest {
    @Test
    public void testGetFoobar() throws Exception {
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "SET");
        Map<String, String> env = pb.environment();
        env.put("SOME_VARIABLE_NAME", "VARIABLE_VALUE");
        Process p = pb.start();
        InputStreamReader isr = new InputStreamReader(p.getInputStream());
        char[] buf = new char[1024];
        while (!isr.ready()) {
            ;
        }
        while (isr.read(buf) != -1) {
            System.out.println(buf);
        }
    }

}
