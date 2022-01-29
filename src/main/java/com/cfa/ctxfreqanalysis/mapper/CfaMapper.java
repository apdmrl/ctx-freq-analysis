package com.cfa.ctxfreqanalysis.mapper;

import com.cfa.ctxfreqanalysis.DTO.ContentResponseDto;
import com.cfa.ctxfreqanalysis.DTO.ContextResponseDto;
import com.cfa.ctxfreqanalysis.model.Contents;
import com.cfa.ctxfreqanalysis.model.Contexts;

public interface CfaMapper {
    ContextResponseDto contextToDto(Contexts ctx);
    ContentResponseDto contentToDto(Contents cnt);
}
