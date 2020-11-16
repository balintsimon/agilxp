package com.drbsimon.server.service;

import com.drbsimon.server.dao.AppUserDao;
import com.drbsimon.server.dao.UserGroupDao;
import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.Role;
import com.drbsimon.server.model.Theme;
import com.drbsimon.server.model.UserGroup;
import com.drbsimon.server.model.dto.AppUserDto;
import com.drbsimon.server.model.dto.NewGroupDto;
import com.drbsimon.server.model.dto.NewUserDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final AppUserDao appUserDao;
    private final UserGroupDao userGroupDao;
    private final MainMenuService mainMenuService;

    private static final String USER_ID_NOT_FOUND = "The given userId does not exist!";

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

    @Transactional
    public boolean modifyUserName(AppUserDto appUserDto) {
        if (isAppUserDtoInvalid(appUserDto)) return false;
        AppUser modifiedUser = appUserDao.findBy(appUserDto.getId()).orElseThrow(
                () -> new EntityNotFoundException(USER_ID_NOT_FOUND)
        );
        if (modifiedUser.getName().equals(appUserDto.getName())) return true;
        modifiedUser.setName(appUserDto.getName());
        appUserDao.save(modifiedUser);
        return true;
    }

    @Transactional
    public boolean deleteUser(Long id) {
        AppUser userToDelete = appUserDao.findBy(id).orElseThrow(
                () -> new EntityNotFoundException(USER_ID_NOT_FOUND)
        );
        appUserDao.removeBy(id);
        return true;
    }

    @Transactional
    public boolean changeAppUserTheme(AppUserDto appUserDto) {
        if (isAppUserDtoInvalid(appUserDto) || appUserDto.getTheme() == null) return false;
        AppUser modifiedUser = appUserDao.findBy(appUserDto.getId()).orElseThrow(
                () -> new EntityNotFoundException(USER_ID_NOT_FOUND)
        );
        if (modifiedUser.getName().equals(appUserDto.getName())) return true;
        modifiedUser.setTheme(appUserDto.getTheme());
        appUserDao.save(modifiedUser);
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

    private boolean isAppUserDtoInvalid(AppUserDto appUserDto) {
        return appUserDto.getId() == null
                || appUserDto.getName() == null
                || appUserDto.getName().isEmpty();
    }

    protected boolean isRequesterGroupAdmin(String requesterName, Long groupId) {
        AppUser requester = appUserDao.getBy(requesterName);
        return requester == null
                && requester.getRole() == Role.ADMIN
                && requester.getUserGroup().getId().equals(groupId);
    }

    private void saveNewGroup(NewGroupDto newGroupDto) {
        AppUser newAdmin = AppUser.builder()
                .name(newGroupDto.getUserName())
                .role(Role.ADMIN)
                .theme(Theme.DEFAULT)
                .build();

        mainMenuService.addMainMenuToNewUser(newAdmin);

        UserGroup newGroup = UserGroup.builder()
                .name(newGroupDto.getGroupName())
                .build();

        newAdmin.setUserGroup(newGroup);
        newGroup.setAppUsers(Arrays.asList(newAdmin));

        userGroupDao.save(newGroup);
    }

    private void saveNewUser(NewUserDto newUserDto) {
        AppUser newUser = AppUser.builder()
                .name(newUserDto.getNewUserName())
                .role(Role.USER)
                .theme(Theme.DEFAULT)
                .build();

        mainMenuService.addMainMenuToNewUser(newUser);

        UserGroup existingGroup = userGroupDao.getBy(newUserDto.getGroupId());
        List<AppUser> existingUsers = existingGroup.getAppUsers();

        newUser.setUserGroup(existingGroup);

        existingUsers.add(newUser);
        existingGroup.setAppUsers(existingUsers);

        userGroupDao.save(existingGroup);
    }
}
