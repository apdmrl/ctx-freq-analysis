package com.cfa.ctxfreqanalysis.mapper.impl;

import com.cfa.ctxfreqanalysis.DTO.ContentResponseDto;
import com.cfa.ctxfreqanalysis.DTO.ContextResponseDto;
import com.cfa.ctxfreqanalysis.constants.ProjectConstants;
import com.cfa.ctxfreqanalysis.mapper.CfaMapper;
import com.cfa.ctxfreqanalysis.model.Contents;
import com.cfa.ctxfreqanalysis.model.Contexts;
import com.cfa.ctxfreqanalysis.model.Language;
import com.cfa.ctxfreqanalysis.repository.ContextsRepository;
import com.cfa.ctxfreqanalysis.service.ContextsService;
import com.cfa.ctxfreqanalysis.util.FrequencyAnalysisUtil;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CfaMapperImpl implements CfaMapper {

    private final ContextsRepository contextsRepository;

    public CfaMapperImpl(ContextsRepository contextsRepository) {
        this.contextsRepository = contextsRepository;
    }


    @Override
    public ContextResponseDto contextToDto(Contexts ctx) {
        return ContextResponseDto.builder()
        .id(ctx.getId())
        .lang(ctx.getLanguage())
        .parentContextName(ctx.getParentContexts() != null ? ctx.getParentContexts().getName() : null)
        .name(ctx.getName())
        .statistics(ctx.getStatistics())
        .stats(ctx.getStats())
        .letterFrequencies(prepareLetterFrequencies(ctx.getStatistics(),ctx.getLanguage())).build();
    }

    @Override
    public ContentResponseDto contentToDto(Contents cnt) {
        return ContentResponseDto.builder()
                .id(cnt.getId())
                .contextName(getContextName(cnt.getContextId()))
                .statistics(cnt.getStatistics())
                .stats(cnt.getStats())
                .name(cnt.getName())
                .lang(cnt.getLanguage())
                .letterFrequencies(prepareLetterFrequencies(cnt.getStatistics(),cnt.getLanguage())).build();
    }

    private Map<Character,Double> prepareLetterFrequencies(String statistics, Language language){
        char[] alphabet = ProjectConstants.alphabets.get(language.toString()).toCharArray();
        double[] stats = Arrays.stream(statistics.split("-")).mapToDouble(Double::parseDouble).toArray();
        LinkedHashMap<Character,Double> letterFreqs = FrequencyAnalysisUtil.prepareAlphabetStats(alphabet,stats);
        FrequencyAnalysisUtil.orderByValue(letterFreqs, Comparator.comparingDouble(o ->  o *-1));
        return letterFreqs;
    }

    private String getContextName(Long id){
        return contextsRepository.getById(id).getName();
    }
}
