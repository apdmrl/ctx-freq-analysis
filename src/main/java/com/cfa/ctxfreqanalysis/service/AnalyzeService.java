package com.cfa.ctxfreqanalysis.service;

import com.cfa.ctxfreqanalysis.DTO.AnalyzeRequestDto;
import com.cfa.ctxfreqanalysis.DTO.AnalyzeResponseDto;
import com.cfa.ctxfreqanalysis.DTO.ContextResponseDto;
import com.cfa.ctxfreqanalysis.constants.ProjectConstants;
import com.cfa.ctxfreqanalysis.exceptions.LanguageNotFoundException;
import com.cfa.ctxfreqanalysis.model.Language;
import com.cfa.ctxfreqanalysis.util.FrequencyAnalysisUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class AnalyzeService {

    private final ContextsService contextsService;

    public AnalyzeService(ContextsService contextsService) {
        this.contextsService = contextsService;
    }

    public AnalyzeResponseDto analyze(AnalyzeRequestDto requestDto) throws LanguageNotFoundException {
        AnalyzeResponseDto responseDto = new AnalyzeResponseDto();
        char[] alphabet = ProjectConstants.alphabets.get(requestDto.getLang().toUpperCase()).toCharArray();
        responseDto.setStatsOfText(FrequencyAnalysisUtil.countLetters(requestDto.getText(), Language.valueOf(requestDto.getLang().toUpperCase())));
        double[] statisticsOfText = FrequencyAnalysisUtil.calculateStatistics(responseDto.getStatsOfText());
        LinkedHashMap<Character,Double> letterFreqs = FrequencyAnalysisUtil.prepareAlphabetStats(alphabet,statisticsOfText);
        FrequencyAnalysisUtil.orderByValue(letterFreqs, Comparator.comparingDouble(o ->  o *-1));
        responseDto.setLetterFrequencies(letterFreqs);
        List<ContextResponseDto> contextsList = contextsService.getContextsByLang(requestDto.getLang().toUpperCase());
        contextsList.sort((o1, o2) -> {
            double[] o1Array = Arrays.stream(o1.getStatistics().split("-")).mapToDouble(Double::parseDouble).toArray();
            double[] o2Array = Arrays.stream(o2.getStatistics().split("-")).mapToDouble(Double::parseDouble).toArray();
            double o1Sum = IntStream.range(0, o1Array.length).mapToDouble(i -> Math.abs(statisticsOfText[i] - o1Array[i])).sum();
            double o2Sum = IntStream.range(0, o2Array.length).mapToDouble(i -> Math.abs(statisticsOfText[i] - o2Array[i])).sum();
            return Double.compare(o1Sum, o2Sum);
        });

        responseDto.setContextsList(contextsList);

        return responseDto;
    }
}
