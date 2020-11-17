package com.drbsimon.server.service;

import com.drbsimon.server.dao.ApplicationDao;
import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.Application;
import com.drbsimon.server.model.MainMenu;
import com.drbsimon.server.model.dto.ApplicationDto;
import com.drbsimon.server.model.dto.ApplicationRequestDto;
import com.drbsimon.server.model.wrapper.ApplicationWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class ApplicationService {
    private final ApplicationDao applicationDao;
    private final MainMenuService mainMenuService;
    private final UserService userService;

    public boolean addNewApplication(ApplicationRequestDto applicationRequestDto) {
        if (applicationRequestDto.getUserId() == null
                || applicationRequestDto.getApplicationName() == null
                || applicationRequestDto.getApplicationName().isEmpty()
        ) return false;

        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(applicationRequestDto.getUserId());
        if (userMainMenu == null || userMainMenu.getId() == null) return false;

        List<Application> applications = userMainMenu.getApplications();
        if (isNewItemNameDatabase(applications, applicationRequestDto.getApplicationName())) return false;

        Application newApplication = Application.builder()
                .name(applicationRequestDto.getApplicationName())
                .mainMenus(Arrays.asList(userMainMenu))
                .build();
        applications.add(newApplication);
        userMainMenu.setApplications(applications);
        applicationDao.save(newApplication);
        mainMenuService.save(userMainMenu);
        return true;
    }

    public ApplicationWrapper getAllApplicationsOfUser(ApplicationRequestDto applicationRequestDto) {
        ApplicationWrapper applicationWrapper = new ApplicationWrapper();
        if (applicationRequestDto.getUserId() == null) return applicationWrapper;

        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(applicationRequestDto.getUserId());
        if (userMainMenu == null || userMainMenu.getId() == null) return applicationWrapper;

        List<Application> applications = userMainMenu.getApplications();
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        for (Application currentApplication : applications) {
                ApplicationDto currentDto = ApplicationDto.builder()
                        .id(currentApplication.getId())
                        .name(currentApplication.getName())
                        .mainMenuId(userMainMenu.getId())
                        .build();
                applicationDtos.add(currentDto);
        }
        applicationWrapper.setApplications(applicationDtos);
        return applicationWrapper;
    }

    public String runApplication(ApplicationRequestDto applicationRequestDto) {
        if (applicationRequestDto.getUserId() == null
                || applicationRequestDto.getApplicationId() == null
        ) return null;

        Application app = applicationDao.getBy(applicationRequestDto.getApplicationId());
        AppUser appUser = userService.getBy(applicationRequestDto.getUserId());
        appUser.setLastRunAppName(app.getName());
        return applicationDao.getBy(app.getId()).getName();
    }

    private boolean isNewItemNameDatabase(List<Application> inDatabase, String newName) {
        List<Application> foundWithName = inDatabase.stream()
                .filter(actualItem -> actualItem.getName().equals(newName))
                .collect(Collectors.toList());
        return foundWithName.size() != 0;
    }
}
