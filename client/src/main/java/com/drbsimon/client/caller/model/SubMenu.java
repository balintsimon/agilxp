package com.drbsimon.client.caller.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubMenu {
    private Long id;
    private String name;
    private List<MainMenu> mainMenus;
}
