package com.cfa.ctxfreqanalysis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzeResponseDto implements Serializable {

    private String statsOfText;

    private Map<Character,Double> letterFrequencies;

    private List<ContextResponseDto> contextsList;
}
