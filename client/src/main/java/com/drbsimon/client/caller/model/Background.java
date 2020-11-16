package com.drbsimon.client.caller.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Background {
    private Long id;
    private String name;
    private MainMenu chosenMainMenu;
    private MainMenu mainMenu;
}
