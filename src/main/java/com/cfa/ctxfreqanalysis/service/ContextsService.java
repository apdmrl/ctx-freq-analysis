package com.cfa.ctxfreqanalysis.service;

import com.cfa.ctxfreqanalysis.DTO.ContextResponseDto;
import com.cfa.ctxfreqanalysis.DTO.ContextRequestDto;
import com.cfa.ctxfreqanalysis.exceptions.ContextNotFoundException;
import com.cfa.ctxfreqanalysis.exceptions.LanguageNotFoundException;
import com.cfa.ctxfreqanalysis.mapper.impl.CfaMapperImpl;
import com.cfa.ctxfreqanalysis.model.Contexts;
import com.cfa.ctxfreqanalysis.model.Language;
import com.cfa.ctxfreqanalysis.repository.ContextsRepository;
import com.cfa.ctxfreqanalysis.util.FrequencyAnalysisUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ContextsService {

    private final ContextsRepository contextsRepository;

    private final CfaMapperImpl cfaMapper;

    public ContextsService(ContextsRepository contextsRepository, CfaMapperImpl cfaMapper) {
        this.contextsRepository = contextsRepository;
        this.cfaMapper = cfaMapper;
    }

    public List<ContextResponseDto> getAllContexts(){
        return contextsRepository.findAll().stream().map(cfaMapper::contextToDto).collect(Collectors.toList());
    }

    public Contexts getById(Long contextId){
        return contextsRepository.findById(contextId).orElse(null);
    }

    public void updateContext(Contexts context){
        contextsRepository.save(context);
    }

    public Contexts getByName(String name){
        return contextsRepository.findByName(name).orElse(null);
    }

    public Contexts getByNameAndLang(String name, Language lang){
        return contextsRepository.findByNameAndLanguage(name,lang).orElse(null);
    }


    public ContextResponseDto addContext(ContextRequestDto requestDto) throws LanguageNotFoundException {
        Contexts ctx = getByNameAndLang(requestDto.getName(),Language.valueOf(requestDto.getLang().toUpperCase()));
        if(ctx != null && ctx.getLanguage().equals(Language.valueOf(requestDto.getLang().toUpperCase()))){
            throw new ContextNotFoundException("there is a context with name  " + requestDto.getName() + " and lang :"+ requestDto.getLang());
        }
        Contexts context = new Contexts();
        context.setName(requestDto.getName());
        Language lang;
        try {
            //for the first time adding context
            lang =  Language.valueOf(requestDto.getLang().toUpperCase());
            context.setLanguage(lang);
            context.setStats(FrequencyAnalysisUtil.countLetters("",lang));
            context.setStatistics(context.getStats());
        }catch (IllegalArgumentException ex){
            throw new LanguageNotFoundException("Language bulunamadi");
        }
        if(requestDto.getParentContextName() != null){
            Contexts parentContext = getByNameAndLang(requestDto.getParentContextName(),lang);
            if(parentContext == null){
                throw new ContextNotFoundException("there is no context with name :" + requestDto.getParentContextName());
            }
            if(parentContext.getLanguage() != lang){
                throw new LanguageNotFoundException("Parent Context and Sub Context language must be same. Parent Context Language is :" + parentContext.getLanguage());
            }
            context.setParentContexts(parentContext);
        }
        return cfaMapper.contextToDto(contextsRepository.save(context));
    }

    public List<ContextResponseDto> getContextsByLang(String language) throws LanguageNotFoundException {
        try{
            return contextsRepository.findByLanguage(Language.valueOf(language.toUpperCase())).stream().map(cfaMapper::contextToDto).collect(Collectors.toList());
        }catch (IllegalArgumentException exception){
            throw new LanguageNotFoundException("Language is not found");
        }
    }

    public List<ContextResponseDto> getParentContexts(String language) throws LanguageNotFoundException{
        try {
            return contextsRepository.findByLanguageAndParentContexts(Language.valueOf(language.toUpperCase()),null).stream().map(cfaMapper::contextToDto).collect(Collectors.toList());
        } catch (IllegalArgumentException exception){
            throw new LanguageNotFoundException("Language is not found");
        }
    }
}
