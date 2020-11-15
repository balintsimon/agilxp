package com.drbsimon.server.service;

import com.drbsimon.server.dao.AppUserDao;
import com.drbsimon.server.dao.MainMenuDao;
import com.drbsimon.server.model.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class MainMenuService {
    private final MainMenuDao mainMenuDao;
    private final AppUserDao appUserDao;

    public MainMenu addMainMenuToNewUser(AppUser appUser) {
        MainMenu newMainMenu = MainMenu.builder()
                .name("default")
                .background(Background.builder().name(appUser.getName() + " default").build())
                .theme(Theme.builder().name(appUser.getName() + " default").build())
                .subMenus(Arrays.asList(SubMenu.builder().name(appUser.getName() + " default").build()))
                .icons(Arrays.asList(Icon.builder().name(appUser.getName() + " default").build()))
                .appUser(appUser)
                .build();
        mainMenuDao.save(newMainMenu);
        return newMainMenu;
    }

    public MainMenu getMainMenuByUser(Long id) {
        AppUser user = appUserDao.getBy(id);
        return mainMenuDao.getBy(user);
    }

    public void save(MainMenu mainMenu) {
        mainMenuDao.save(mainMenu);
    }
}
