package com.drbsimon.server.service;

import com.drbsimon.server.dao.AppUserDao;
import com.drbsimon.server.dao.UserGroupDao;
import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.Role;
import com.drbsimon.server.model.UserGroup;
import com.drbsimon.server.model.dto.NewGroupDto;
import com.drbsimon.server.model.dto.NewUserDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public boolean tryRegisterNewUser(NewUserDto newUserDto) {
        if (isNewUserRegistrationRequestInvalid(newUserDto)
                || appUserDao.exists(newUserDto.getNewUserName())
                || !isRequesterGroupAdmin(newUserDto.getRequesterName(), newUserDto.getGroupId())
        ) return false;
        saveNewUser(newUserDto);
        return true;
    }

    private boolean isGroupRegistrationRequestInvalid(NewGroupDto newGroupDto) {
        return newGroupDto.getGroupName() == null
                || newGroupDto.getUserName() == null
                || newGroupDto.getGroupName().isEmpty()
                || newGroupDto.getUserName().isEmpty();
    }

    private boolean isNewUserRegistrationRequestInvalid(NewUserDto newUserDto) {
        return newUserDto.getGroupId() == null
                || newUserDto.getNewUserName() == null
                || newUserDto.getRequesterName() == null
                || newUserDto.getNewUserName().isEmpty()
                || newUserDto.getRequesterName().isEmpty();
    }

    protected boolean isRequesterGroupAdmin(String requesterName, Long groupId) {
        AppUser requester = appUserDao.getBy(requesterName);
        return requester == null
                && requester.getRole() == Role.ADMIN
                && requester.getUserGroup().getId().equals(groupId);
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

    private void saveNewUser(NewUserDto newUserDto) {
        AppUser newUser = new AppUser().builder()
                .name(newUserDto.getNewUserName())
                .role(Role.USER)
                .build();

        UserGroup existingGroup = userGroupDao.getBy(newUserDto.getGroupId());
        List<AppUser> existingUsers = existingGroup.getAppUsers();

        newUser.setUserGroup(existingGroup);

        existingUsers.add(newUser);
        existingGroup.setAppUsers(existingUsers);

        userGroupDao.save(existingGroup);
    }

}
