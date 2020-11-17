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

    public boolean registerNewGroup(String groupName, String adminName) {
        NewGroupDto newGroupDto = NewGroupDto.builder()
                .groupName("New Group")
                .userName("New Admin")
                .build();
        return serverCaller.registerNewGroup(newGroupDto);
    }

    public boolean registerNewUser(String adminName, String userName, Long groupId) {
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
}
