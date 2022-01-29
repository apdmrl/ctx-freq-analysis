package com.cfa.ctxfreqanalysis.util;

import com.cfa.ctxfreqanalysis.constants.ProjectConstants;
import com.cfa.ctxfreqanalysis.model.Language;
import org.apache.commons.math3.util.Precision;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FrequencyAnalysisUtil {

    public static String countLetters(String text, Language language){
        char[] alphabet = ProjectConstants.alphabets.get(language.toString()).toCharArray();
        Map<Character,Integer> statMap = new LinkedHashMap<>();
        for (char value : alphabet) {
            statMap.put(value, 0);
        }
        char[] textArray = text.toLowerCase().toCharArray();
        for (char c : textArray) {
            if(statMap.containsKey(c))
                statMap.merge(c, 1, Integer::sum);
        }

        return statMap.values().stream().map(Object::toString).collect(Collectors.joining("-"));
    }

    public static double[] calculateStatistics(String stats){
        int[] statsArray = Arrays.stream(stats.split("-")).mapToInt(Integer::parseInt).toArray();
        int sumOfStats = IntStream.of(statsArray).sum();
        if(sumOfStats != 0){
            return Arrays.stream(statsArray).mapToDouble(s -> Precision.round((s * 100.0 / sumOfStats),2)).toArray();
        }else{
            return new double[statsArray.length];
        }
    }

    public static String calculateStatisticsAsString(String stats){
        return Arrays.stream(FrequencyAnalysisUtil.calculateStatistics(stats)).mapToObj(String::valueOf).collect(Collectors.joining("-"));
    }

    public static String updateStats(String contextStats, String stats){
        int[] contextStatsInteger = Arrays.stream(contextStats.split("-")).mapToInt(Integer::parseInt).toArray();
        int[] statsInteger = Arrays.stream(stats.split("-")).mapToInt(Integer::parseInt).toArray();
        int[] updatedStats = IntStream.range(0, contextStatsInteger.length)
                .map(i -> Integer.sum(contextStatsInteger[i],statsInteger[i])).toArray();

        return Arrays.stream(updatedStats).mapToObj(String::valueOf).collect(Collectors.joining("-"));
    }

    public static String extractContentUsingParser(InputStream inputStream) throws TikaException, IOException, SAXException {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(inputStream,handler,metadata,context);

        return handler.toString();
    }

    public static void orderByValue(
            LinkedHashMap<Character, Double> m, Comparator<Double> c) {
        List<Map.Entry<Character, Double>> entries = new ArrayList<>(m.entrySet());
        m.clear();
        entries.stream()
                .sorted(Map.Entry.comparingByValue(c))
                .forEachOrdered(e -> m.put(e.getKey(), e.getValue()));
    }

    public static LinkedHashMap<Character,Double> prepareAlphabetStats(char[] alphabet,double[] statisticsOfText){
        LinkedHashMap<Character,Double> letterFreqs = new LinkedHashMap<>();
        for(int i = 0; i < alphabet.length; i++){
            letterFreqs.put(alphabet[i], statisticsOfText[i]);
        }
        return letterFreqs;
    }


}
