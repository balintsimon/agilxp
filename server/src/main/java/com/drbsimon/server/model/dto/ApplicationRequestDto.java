package com.drbsimon.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDto {
    private Long userId;
    private String applicationName;
    private Long applicationId;
}
