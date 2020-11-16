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

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class ApplicationService {
    private final ApplicationDao applicationDao;
    private final MainMenuService mainMenuService;
    private final UserService userService;

    public boolean addNewApplication(ApplicationRequestDto applicationRequestDto) {
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(applicationRequestDto.getUserId());
        List<Application> applications = userMainMenu.getApplications();

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
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(applicationRequestDto.getUserId());
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

        ApplicationWrapper applicationWrapper = new ApplicationWrapper();
        applicationWrapper.setApplications(applicationDtos);
        return applicationWrapper;
    }

    public String runApplication(ApplicationRequestDto applicationRequestDto) {
        Application app = applicationDao.getBy(applicationRequestDto.getApplicationId());
        AppUser appUser = userService.getBy(applicationRequestDto.getUserId());
        appUser.setLastRunAppName(app.getName());
        return applicationDao.getBy(app.getId()).getName();
    }
}
