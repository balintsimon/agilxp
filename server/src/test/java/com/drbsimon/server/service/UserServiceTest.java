package com.drbsimon.server.service;

import com.drbsimon.server.dao.AppUserDao;
import com.drbsimon.server.dao.UserGroupDao;
import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.Role;
import com.drbsimon.server.model.UserGroup;
import com.drbsimon.server.model.dto.NewGroupDto;
import com.drbsimon.server.model.dto.NewUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceTest {
    @MockBean
    private AppUserDao appUserDao;

    @MockBean
    private UserGroupDao userGroupDao;

    @MockBean
    private MainMenuService mainMenuService;

    @Autowired
    private UserService service;

    @Test
    public void initTest() {
        assertThat(appUserDao).isNotNull();
        assertThat(userGroupDao).isNotNull();
        assertThat(service).isNotNull();
    }

    @Test
    public void testRegisterGroupWithNullInRegistrationForm() {
        String userName = null;
        String groupName = "Group";
        boolean expected = false;

        NewGroupDto newGroupDto = NewGroupDto.builder()
                .userName(userName)
                .groupName(groupName)
                .build();

        boolean actual = service.tryRegisterGroup(newGroupDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testRegisterGroupWithEmptyStringInRegistrationForm() {
        String userName = "";
        String groupName = "Group";
        boolean expected = false;

        NewGroupDto newGroupDto = NewGroupDto.builder()
                .userName(userName)
                .groupName(groupName)
                .build();

        boolean actual = service.tryRegisterGroup(newGroupDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testRegisterGroupWithUserNameTaken() {
        String userName = "Username";
        String groupName = "Group";
        boolean expected = false;

        NewGroupDto newGroupDto = NewGroupDto.builder()
                .userName(userName)
                .groupName(groupName)
                .build();

        given(appUserDao.exists(userName)).willReturn(true);
        given(userGroupDao.exists(groupName)).willReturn(false);
        boolean actual = service.tryRegisterGroup(newGroupDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testRegisterGroupWithGroupNameTaken() {
        String userName = "Username";
        String groupName = "Group";
        boolean expected = false;

        NewGroupDto newGroupDto = NewGroupDto.builder()
                .userName(userName)
                .groupName(groupName)
                .build();

        given(appUserDao.exists(userName)).willReturn(false);
        given(userGroupDao.exists(groupName)).willReturn(true);
        boolean actual = service.tryRegisterGroup(newGroupDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testSuccessfulNewGroupRegister() {
        String userName = "Username";
        String groupName = "Group";
        boolean expected = true;

        NewGroupDto newGroupDto = NewGroupDto.builder()
                .userName(userName)
                .groupName(groupName)
                .build();

        given(appUserDao.exists(userName)).willReturn(false);
        given(userGroupDao.exists(groupName)).willReturn(false);
        boolean actual = service.tryRegisterGroup(newGroupDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testAddNewUserWithNullInForm() {
        String newUserName = null;
        String adminName = "Admin";
        Long groupId = 1L;
        boolean expected = false;

        NewUserDto newUserDto = NewUserDto.builder()
                .newUserName(newUserName)
                .requesterName(adminName)
                .groupId(groupId)
                .build();

        boolean actual = service.tryRegisterNewUser(newUserDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testRegisterNewUserWithEmptyStringInForm() {
        String newUserName = "";
        String adminName = "Admin";
        Long groupId = 1L;
        boolean expected = false;

        NewUserDto newUserDto = NewUserDto.builder()
                .newUserName(newUserName)
                .requesterName(adminName)
                .groupId(groupId)
                .build();

        boolean actual = service.tryRegisterNewUser(newUserDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testAddingNewUserByNotAdmin() {
        String newUserName = "New User";
        String adminName = "Admin";
        Long groupId = 1L;
        boolean expected = false;

        NewUserDto newUserDto = NewUserDto.builder()
                .newUserName(newUserName)
                .requesterName(adminName)
                .groupId(groupId)
                .build();

        given(appUserDao.getBy(adminName)).willReturn(AppUser.builder()
                .name(adminName)
                .role(Role.USER)
                .userGroup(UserGroup.builder()
                        .name("group")
                        .id(groupId)
                        .build())
                .build());
        given(appUserDao.exists(newUserName)).willReturn(false);

        boolean actual = service.tryRegisterNewUser(newUserDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testAddingNewUserByOtherGroupAdmin() {
        String newUserName = "New User";
        String adminName = "Admin";
        Long groupId = 1L;
        boolean expected = false;

        NewUserDto newUserDto = NewUserDto.builder()
                .newUserName(newUserName)
                .requesterName(adminName)
                .groupId(groupId)
                .build();

        given(appUserDao.getBy(adminName)).willReturn(AppUser.builder()
                .name(adminName)
                .role(Role.ADMIN)
                .userGroup(UserGroup.builder()
                        .name("group")
                        .id(groupId + 1L)
                        .build())
                .build());
        given(appUserDao.exists(newUserName)).willReturn(false);

        boolean actual = service.tryRegisterNewUser(newUserDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testNewUserNameTaken() {
        String newUserName = "New User";
        String adminName = "Admin";
        Long groupId = 1L;
        boolean expected = false;

        NewUserDto newUserDto = NewUserDto.builder()
                .newUserName(newUserName)
                .requesterName(adminName)
                .groupId(groupId)
                .build();

        given(appUserDao.getBy(adminName)).willReturn(AppUser.builder()
                .name(adminName)
                .role(Role.ADMIN)
                .userGroup(UserGroup.builder()
                        .name("group")
                        .id(groupId)
                        .build())
                .build());
        given(appUserDao.exists(newUserName)).willReturn(true);

        boolean actual = service.tryRegisterNewUser(newUserDto);

        assertThat(actual).isEqualTo(expected);
    }
}