package com.cfa.ctxfreqanalysis.service;

import com.cfa.ctxfreqanalysis.DTO.ContentResponseDto;
import com.cfa.ctxfreqanalysis.DTO.ContentRequestDto;
import com.cfa.ctxfreqanalysis.exceptions.ContextNotFoundException;
import com.cfa.ctxfreqanalysis.exceptions.LanguageNotFoundException;
import com.cfa.ctxfreqanalysis.mapper.impl.CfaMapperImpl;
import com.cfa.ctxfreqanalysis.model.Contents;
import com.cfa.ctxfreqanalysis.model.Contexts;
import com.cfa.ctxfreqanalysis.model.Language;
import com.cfa.ctxfreqanalysis.repository.ContentsRepository;
import com.cfa.ctxfreqanalysis.util.FrequencyAnalysisUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentsService {

    private final ContentsRepository contentsRepository;

    private final CfaMapperImpl cfaMapper;

    private final ContextsService contextsService;

    public ContentsService(ContentsRepository contentsRepository, CfaMapperImpl cfaMapper, ContextsService contextsService) {
        this.contentsRepository = contentsRepository;
        this.cfaMapper = cfaMapper;
        this.contextsService = contextsService;
    }

    public List<ContentResponseDto> getContentsByLanguage(String language) throws LanguageNotFoundException {
        try {
            return contentsRepository.findByLanguage(Language.valueOf(language.toUpperCase())).stream().map(cfaMapper::contentToDto).collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw new LanguageNotFoundException(ex.getLocalizedMessage());
        }
    }

    public Contents getByName(String name) {
        return contentsRepository.findByName(name).orElse(null);
    }


    public Contents addContent(ContentRequestDto contentDto) throws LanguageNotFoundException {
        Language language;
        try {
            language = Language.valueOf(contentDto.getLanguage());
        } catch (IllegalArgumentException ex) {
            throw new LanguageNotFoundException("Language not found");
        }

        Contexts ctx = contextsService.getById(contentDto.getContextId());
        if (ctx == null)
            throw new ContextNotFoundException("Context bulunamadi");

        if (getByName(contentDto.getName()) != null)
            return null;


        Contents content = new Contents();
        content.setContextId(ctx.getId());
        content.setLanguage(language);
        content.setName(contentDto.getName());
        if (contentDto.getFile() == null) {
            content.setStats(FrequencyAnalysisUtil.countLetters(contentDto.getText(), language));
        } else {
            try {
                InputStream inputStream = new BufferedInputStream(contentDto.getFile().getInputStream());
                String text = FrequencyAnalysisUtil.extractContentUsingParser(inputStream);
                content.setStats(FrequencyAnalysisUtil.countLetters(text, Language.valueOf(contentDto.getLanguage())));
                content.setStatistics(FrequencyAnalysisUtil.calculateStatisticsAsString(content.getStats()));
            } catch (Exception exception) {
                return null;
            }
        }
        ctx.setStats(FrequencyAnalysisUtil.updateStats(ctx.getStats(), content.getStats()));
        ctx.setStatistics(FrequencyAnalysisUtil.calculateStatisticsAsString(ctx.getStats()));
        contextsService.updateContext(ctx);
        updateParentContexts(ctx, content.getStats());
        return contentsRepository.save(content);
    }

    private void updateParentContexts(Contexts ctx, String stats) {
        while (ctx.getParentContexts() != null){
            ctx = ctx.getParentContexts();
            ctx.setStats(FrequencyAnalysisUtil.updateStats(ctx.getStats(),stats));
            ctx.setStatistics(FrequencyAnalysisUtil.calculateStatisticsAsString(ctx.getStats()));
            contextsService.updateContext(ctx);
        }
    }


}
