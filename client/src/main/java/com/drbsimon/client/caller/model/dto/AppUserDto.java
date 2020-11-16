package com.drbsimon.client.caller.model.dto;

import com.drbsimon.client.caller.model.Theme;
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
