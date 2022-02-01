package com.cfa.ctxfreqanalysis.DTO;

import com.cfa.ctxfreqanalysis.model.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContextResponseDto implements Serializable {
    private Long id;

    private String name;

    private String stats;

    private Language lang;

    private String parentContextName;

    private String statistics;

    private Map<Character,Double> letterFrequencies;
}
