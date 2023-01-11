package org.example.TestLog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LogTest {
    private final Logger logger = LogManager.getLogger(org.example.TestLog.LogTest.class);
    @Test
    public void testLogToFile() throws Exception{
        logger.info("This is info.");
        logger.error("This is error.");
        FileInputStream fileStream = new FileInputStream("target/logs/test_log.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
        String strLine = br.readLine();
        Assertions.assertTrue(strLine.contains("INFO"));
        Assertions.assertTrue(strLine.contains("This is info."));
        strLine = br.readLine();
       Assertions.assertTrue(strLine.contains("ERROR"));
       Assertions.assertTrue(strLine.contains("This is error."));
        fileStream.close();
    }
}
