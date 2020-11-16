package com.drbsimon.server.controller;

import com.drbsimon.server.model.dto.AppUserDto;
import com.drbsimon.server.model.dto.NewGroupDto;
import com.drbsimon.server.model.dto.NewUserDto;
import com.drbsimon.server.model.wrapper.AppUserWrapper;
import com.drbsimon.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public AppUserWrapper getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public boolean registerNewUser(@RequestBody NewUserDto newUserDto) {
        return userService.tryRegisterNewUser(newUserDto);
    }

    @PostMapping("/group")
    public boolean registerNewGroup(@RequestBody NewGroupDto newGroupDto) {
        return userService.tryRegisterGroup(newGroupDto);
    }

    @PutMapping
    public boolean modifyUserName(@RequestBody AppUserDto appUserDto) {
        return userService.modifyUserName(appUserDto);
    }

    @PutMapping("/theme")
    public boolean changeUserTheme(@RequestBody AppUserDto appUserDto) {
        return userService.changeAppUserTheme(appUserDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
