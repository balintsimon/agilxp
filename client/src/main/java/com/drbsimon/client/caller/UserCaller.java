package com.drbsimon.client.caller;

import com.drbsimon.client.caller.model.AppUser;
import com.drbsimon.client.caller.model.dto.*;
import com.drbsimon.client.caller.model.wrapper.AppUserWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@Service
@Slf4j
@RequiredArgsConstructor
public class UserCaller {
    private final RestTemplate restTemplate;

    @Value("${backend.url}")
    private String baseUrl;

    public List<AppUser> getAllAppUsers() {
        AppUserWrapper appUsers = restTemplate.getForObject(baseUrl + "/user", AppUserWrapper.class);
        return appUsers.getAppUsers();
    }

    public AppUser getUserById(Long id) {
        return restTemplate.getForObject(baseUrl + "/user/" + id, AppUser.class);
    }

    public void changeUserName(AppUserDto appUserDto) {
        restTemplate.put(baseUrl + "/user", appUserDto);
    }

    public void deleteUser(Long id) {
        restTemplate.delete(baseUrl + "/user/" + id);
    }

    public boolean registerNewGroup(NewGroupDto newGroupDto) {
        return restTemplate.postForObject(baseUrl + "/user/group", newGroupDto, Boolean.class);
    }

    public boolean registerNewUser(NewUserDto newUserDto) {
        return restTemplate.postForObject(baseUrl + "/user", newUserDto, Boolean.class);
    }

    public boolean addNewIcon(IconDto iconDto) {
        return restTemplate.postForObject(baseUrl + "/user/icon", iconDto, Boolean.class);
    }

    
}
