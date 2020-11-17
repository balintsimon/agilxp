package com.drbsimon.client.caller.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    private Long id;
    private String name;
    private Role role;
    private UserGroup userGroup;
    private MainMenu mainMenu;
    private Theme theme;
    private String lastRunAppName;
}
