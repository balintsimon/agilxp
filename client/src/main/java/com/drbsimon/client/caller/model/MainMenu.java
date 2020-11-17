package com.drbsimon.client.caller.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainMenu {
    private Long id;
    private String name;
    private AppUser appUser;
    private List<SubMenu> subMenus;
    private List<Icon> icons;
    private List<Application> applications;
    private Background background;
    private List<Background> backgrounds;
}
