package com.accountant.service.accountant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadedFileDTO {
    private Long id;
    private String fileName;
    private String fileContent;
}
