package com.drbsimon.server.service;

import com.drbsimon.server.dao.AppUserDao;
import com.drbsimon.server.dao.UserGroupDao;
import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.Role;
import com.drbsimon.server.model.UserGroup;
import com.drbsimon.server.model.dto.NewGroupDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final AppUserDao appUserDao;
    private final UserGroupDao userGroupDao;

    public boolean tryRegisterGroup(NewGroupDto newGroupDto) {
        if (isGroupRegistrationRequestInvalid(newGroupDto)
                || appUserDao.exists(newGroupDto.getUserName())
                || userGroupDao.exists(newGroupDto.getGroupName())
        ) return false;
        saveNewGroup(newGroupDto);
        return true;
    }

    private boolean isGroupRegistrationRequestInvalid(NewGroupDto newGroupDto) {
        return newGroupDto.getGroupName() == null
                || newGroupDto.getUserName() == null
                || newGroupDto.getGroupName().isEmpty()
                || newGroupDto.getUserName().isEmpty();
    }

    private void saveNewGroup(NewGroupDto newGroupDto) {
        AppUser newAdmin = new AppUser().builder()
                .name(newGroupDto.getUserName())
                .role(Role.ADMIN)
                .build();

        UserGroup newGroup = new UserGroup().builder()
                .name(newGroupDto.getGroupName())
                .build();

        newAdmin.setUserGroup(newGroup);
        newGroup.setAppUsers(Arrays.asList(newAdmin));

        userGroupDao.save(newGroup);
    }

}
