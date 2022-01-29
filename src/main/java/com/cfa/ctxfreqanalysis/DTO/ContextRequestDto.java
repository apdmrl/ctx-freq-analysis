package com.cfa.ctxfreqanalysis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContextRequestDto implements Serializable {
    private String name;
    private String lang;
    private String parentContextName;
}
