package com.drbsimon.server.controller;

import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.MainMenu;
import com.drbsimon.server.model.dto.*;
import com.drbsimon.server.model.wrapper.AppUserWrapper;
import com.drbsimon.server.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final UserService userService;
    private final IconService iconService;
    private final MainMenuService mainMenuService;
    private final BackgroundService backgroundService;
    private final ApplicationService applicationService;

    @GetMapping
    public AppUserWrapper getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public AppUser registerNewUser(@RequestBody NewUserDto newUserDto) {
        return userService.tryRegisterNewUser(newUserDto);
    }

    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable Long id) {
        return userService.getBy(id);
    }

    @GetMapping("/menu/{id}")
    public MainMenu getUserMainMenu(@PathVariable Long id) {
        return mainMenuService.getMainMenuByUser(id);
    }

    @PostMapping("/group")
    public GroupCreatedDto registerNewGroup(@RequestBody NewGroupDto newGroupDto) {
        System.out.println("Got request");
        System.out.println(newGroupDto);
        return userService.tryRegisterGroup(newGroupDto);
    }

    @PostMapping("/icon")
    public boolean addNewIcon(@RequestBody IconDto iconDto) {
        return iconService.addNewIcon(iconDto);
    }

    @PostMapping("/background")
    public boolean addNewBackground(@RequestBody BackgroundRequestDto backgroundRequestDto) {
        return backgroundService.addNewBackground(backgroundRequestDto);
    }

    @PutMapping("/background")
    public void changeBackground(@RequestBody BackgroundRequestDto backgroundRequestDto) {
        backgroundService.setBackgroundToMainMenu(backgroundRequestDto);
    }

    @PutMapping
    public void modifyUserName(@RequestBody AppUserDto appUserDto) {
        userService.modifyUserName(appUserDto);
    }

    @PutMapping("/theme")
    public boolean changeUserTheme(@RequestBody AppUserDto appUserDto) {
        return userService.changeAppUserTheme(appUserDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/application")
    public boolean addNewApplication(@RequestBody ApplicationRequestDto applicationRequestDto) {
        return applicationService.addNewApplication(applicationRequestDto);
    }

    @PostMapping("/application/{id}")
    public String runApplication(@RequestBody ApplicationRequestDto applicationRequestDto) {
        return applicationService.runApplication(applicationRequestDto);
    }
}
