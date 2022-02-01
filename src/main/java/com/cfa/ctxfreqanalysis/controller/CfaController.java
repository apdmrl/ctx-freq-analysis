package com.cfa.ctxfreqanalysis.controller;

import com.cfa.ctxfreqanalysis.DTO.*;
import com.cfa.ctxfreqanalysis.exceptions.LanguageNotFoundException;
import com.cfa.ctxfreqanalysis.service.AnalyzeService;
import com.cfa.ctxfreqanalysis.service.ContentsService;
import com.cfa.ctxfreqanalysis.service.ContextsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cfa")
@CrossOrigin("*")
public class CfaController {

    private final ContextsService contextsService;
    private final ContentsService contentsService;
    private final AnalyzeService analyzeService;

    public CfaController(ContextsService contextsService, ContentsService contentsService,AnalyzeService analyzeService) {
        this.contextsService = contextsService;
        this.contentsService = contentsService;
        this.analyzeService = analyzeService;
    }

    @GetMapping(value = "/getAllContexts")
    public ResponseEntity<List<ContextResponseDto>> getAllContexts(){
        return new ResponseEntity<>(contextsService.getAllContexts(), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllContents")
    public ResponseEntity<List<ContentResponseDto>> getAllContents(){
        return new ResponseEntity<>(contentsService.getAllContents(), HttpStatus.OK);
    }

    @GetMapping(value = "/getContexts")
    public ResponseEntity<List<ContextResponseDto>> getContexts(@RequestParam("lang") String language) throws LanguageNotFoundException {
        return new ResponseEntity<>(contextsService.getContextsByLang(language), HttpStatus.OK);
    }

    @GetMapping(value = "/getParentContexts")
    public ResponseEntity<List<ContextResponseDto>> getParentContexts(@RequestParam("lang") String language) throws LanguageNotFoundException {
        return new ResponseEntity<>(contextsService.getParentContexts(language), HttpStatus.OK);
    }

    @GetMapping(value = "/getContents")
    public ResponseEntity<List<ContentResponseDto>> getContents(@RequestParam("lang") String language) throws LanguageNotFoundException {
        return new ResponseEntity<>(contentsService.getContentsByLanguage(language),HttpStatus.OK);
    }

    @PostMapping(value = "/addContent")
    public ResponseEntity<ContentResponseDto> addContent(@ModelAttribute ContentRequestDto requestDto) throws LanguageNotFoundException {
        return new ResponseEntity<>(contentsService.addContent(requestDto), HttpStatus.OK);
    }

    @PostMapping(value = "/addContext")
    public ResponseEntity<ContextResponseDto> addContext(@RequestBody ContextRequestDto requestDto) throws LanguageNotFoundException {
        return new ResponseEntity<>(contextsService.addContext(requestDto), HttpStatus.OK);
    }


    @PostMapping(value = "/analyze")
    public ResponseEntity<AnalyzeResponseDto> analyze(@RequestBody AnalyzeRequestDto requestDto) throws LanguageNotFoundException {
        return new ResponseEntity<>(analyzeService.analyze(requestDto),HttpStatus.OK);
    }
}
