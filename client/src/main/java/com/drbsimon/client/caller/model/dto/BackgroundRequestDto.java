package com.drbsimon.client.caller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BackgroundRequestDto {
    private Long userId;
    private String BackgroundName;
}
