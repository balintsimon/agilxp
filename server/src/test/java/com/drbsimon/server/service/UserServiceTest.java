package com.drbsimon.server.service;

import com.drbsimon.server.dao.AppUserDao;
import com.drbsimon.server.dao.UserGroupDao;
import com.drbsimon.server.model.dto.NewGroupDto;
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

    @Autowired
    private UserService service;

    @Test
    public void initTest() {
        assertThat(appUserDao).isNotNull();
        assertThat(userGroupDao).isNotNull();
        assertThat(service).isNotNull();
    }

    @Test
    public void testRegisterWithNullInRegistrationForm() {
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
    public void testRegisterWithEmptyStringInRegistrationForm() {
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
    public void testRegisterWithUserNameTaken() {
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
    public void testRegisterWithGroupNameTaken() {
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
    public void testSuccessfulRegister() {
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
}