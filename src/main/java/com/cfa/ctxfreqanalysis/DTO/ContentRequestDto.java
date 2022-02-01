package com.cfa.ctxfreqanalysis.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequestDto {
    private String name;
    private String text;
    private MultipartFile file;
    private String contextName;
    private String lang;
}
