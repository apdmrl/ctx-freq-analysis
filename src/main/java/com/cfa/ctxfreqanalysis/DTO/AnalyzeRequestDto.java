package com.cfa.ctxfreqanalysis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzeRequestDto implements Serializable {
    private String text;
    private String lang;
}
