package com.drbsimon.server.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private Long id;
    private String name;
}
