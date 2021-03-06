package com.drbsimon.server.model.dto;

import com.drbsimon.server.model.Theme;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private Long userId;
    private String name;
    private Theme theme;
}
