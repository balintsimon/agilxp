package com.drbsimon.client.service;

import com.drbsimon.client.caller.ServerCaller;
import com.drbsimon.client.caller.model.AppUser;
import com.drbsimon.client.caller.model.Theme;
import com.drbsimon.client.caller.model.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class ServerCallerService {
    private final ServerCaller serverCaller;

    public List<AppUser> getAllUsers() {
        return serverCaller.getAllAppUsers();
    }

    public GroupCreatedDto registerNewGroup(String groupName, String adminName) {
        NewGroupDto newGroupDto = NewGroupDto.builder()
                .groupName(groupName)
                .userName(adminName)
                .build();
        return serverCaller.registerNewGroup(newGroupDto);
    }

    public AppUser registerNewUser(String adminName, String userName, Long groupId) {
        NewUserDto newUserToGroup = NewUserDto.builder()
                .requesterName(adminName)
                .groupId(groupId)
                .newUserName(userName)
                .build();
        return serverCaller.registerNewUser(newUserToGroup);
    }

    public AppUser getUserBy(Long id) {
        return serverCaller.getUserById(id);
    }

    public void printAllUsers() {
        List<AppUser> allUsers = serverCaller.getAllAppUsers();
        for (AppUser currentUser : allUsers) {
            System.out.println(currentUser);
        }
    }

    public void changeUserName(Long userId, String name) {
        AppUserDto appUserDto = AppUserDto.builder()
                .userId(userId)
                .name(name)
                .build();
        serverCaller.changeUserName(appUserDto);
    }

    public void changeUserTheme(String userName, Long userId, Theme newTheme) {
        AppUserDto userDto = AppUserDto.builder()
                .name(userName)
                .theme(newTheme)
                .userId(userId)
                .build();
        serverCaller.changeUserTheme(userDto);
    }

    public boolean addNewBackground(Long userId, String backgroundName) {
        BackgroundRequestDto newBackground = BackgroundRequestDto.builder()
                .userId(userId)
                .BackgroundName(backgroundName)
                .build();
        return serverCaller.addNewBackground(newBackground);
    }

    public void changeBackground(Long userId, String backgroundName) {
        BackgroundRequestDto changeBackground = BackgroundRequestDto.builder()
                .BackgroundName(backgroundName)
                .userId(userId)
                .build();
        serverCaller.changeBackground(changeBackground);
    }

    public boolean addNewIcon(String iconName, Long userId) {
        IconDto newIcon = IconDto.builder()
                .newIconName(iconName)
                .oldIconName(iconName)
                .userId(userId)
                .build();
        return serverCaller.addNewIcon(newIcon);
    }

    public boolean addNewApplication(String appName, Long userId) {
        ApplicationRequestDto newApp = ApplicationRequestDto.builder()
                .applicationName(appName)
                .userId(userId)
                .build();
        return serverCaller.addNewApplication(newApp);
    }

    public String runApplication(Long userId, String applicationName, Long applicationId) {
        ApplicationRequestDto requestDto = ApplicationRequestDto.builder()
                .userId(userId)
                .applicationName(applicationName)
                .applicationId(applicationId)
                .build();
        return serverCaller.runApplication(requestDto);
    }
}
