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
public class ServerCaller {
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

    public GroupCreatedDto registerNewGroup(NewGroupDto newGroupDto) {
        System.out.println("Base URL");
        System.out.println(baseUrl);
        return restTemplate.postForObject(baseUrl + "/user/group", newGroupDto, GroupCreatedDto.class);
    }

    public AppUser registerNewUser(NewUserDto newUserDto) {
        return restTemplate.postForObject(baseUrl + "/user", newUserDto, AppUser.class);
    }

    public boolean addNewIcon(IconDto iconDto) {
        return restTemplate.postForObject(baseUrl + "/user/icon", iconDto, Boolean.class);
    }

    public boolean addNewBackground(BackgroundRequestDto backgroundRequestDto) {
        return restTemplate.postForObject(baseUrl + "/user/background", backgroundRequestDto, Boolean.class);
    }

    public void changeBackground(BackgroundRequestDto backgroundRequestDto) {
        restTemplate.put(baseUrl + "/user/background", backgroundRequestDto);
    }

    public void changeUserTheme(AppUserDto appUserDto) {
        restTemplate.put(baseUrl + "/user/theme", appUserDto);
    }

    public boolean addNewApplication(ApplicationRequestDto applicationRequestDto) {
        return restTemplate.postForObject(baseUrl + "/user/application", applicationRequestDto, Boolean.class);
    }

    public String runApplication(ApplicationRequestDto applicationRequestDto) {
        String requestUrl = baseUrl + "/user/application/" + applicationRequestDto.getApplicationId();
        return restTemplate.postForObject(requestUrl, applicationRequestDto, String.class);
    }
}
