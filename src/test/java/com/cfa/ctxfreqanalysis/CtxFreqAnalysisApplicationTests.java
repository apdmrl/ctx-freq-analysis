package com.cfa.ctxfreqanalysis;

import com.cfa.ctxfreqanalysis.model.Language;
import com.cfa.ctxfreqanalysis.util.FrequencyAnalysisUtil;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import java.io.*;

@SpringBootTest
class CtxFreqAnalysisApplicationTests {


    @Test
    void contextLoads() {
        String stats = FrequencyAnalysisUtil.countLetters("Now when the form is submitted, the message is alerted. This happens prior to the actual submission, so we can cancel the submit action by calling",Language.EN);
        String updatedStats = FrequencyAnalysisUtil.updateStats("9-4-6-2-13-1-2-7-0-0-0-5-5-8-7-3-0-4-12-12-4-0-3-0-1-0","9-4-6-2-13-1-2-7-0-0-0-5-5-8-7-3-0-4-12-12-4-0-3-0-1-0");
        String calculateStatistics = FrequencyAnalysisUtil.calculateStatisticsAsString("9-4-6-2-13-1-2-7-0-0-0-5-5-8-7-3-0-4-12-12-4-0-3-0-1-0");
        System.out.println(updatedStats);
    }

    @Test
    void fileReadWithTika() throws IOException, TikaException, SAXException {
        File file = new File("C:\\Users\\apdmr\\Downloads\\greatgatsby.pdf");
        FileInputStream inputStream = new FileInputStream(file);
        String s  =FrequencyAnalysisUtil.extractContentUsingParser(inputStream);
        System.out.println(s);
    }
}
