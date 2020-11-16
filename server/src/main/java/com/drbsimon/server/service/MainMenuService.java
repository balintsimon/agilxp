package com.drbsimon.server.service;

import com.drbsimon.server.dao.AppUserDao;
import com.drbsimon.server.dao.IconDao;
import com.drbsimon.server.dao.MainMenuDao;
import com.drbsimon.server.dao.SubMenuDao;
import com.drbsimon.server.model.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Arrays;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class MainMenuService {
    private final MainMenuDao mainMenuDao;
    private final AppUserDao appUserDao;
    private final SubMenuDao subMenuDao;
    private final IconDao iconDao;

    @Transient
    public MainMenu addMainMenuToNewUser(AppUser appUser) {
        Background background = Background.builder().name(appUser.getName() + " default").build();
        SubMenu subMenu = SubMenu.builder().name(appUser.getName() + " default").build();
        Icon icon = Icon.builder().name(appUser.getName() + " default").build();
        subMenuDao.save(subMenu);
        iconDao.save(icon);

        MainMenu newMainMenu = MainMenu.builder()
                .name(appUser.getName() + " default")
                .background(background)
                .backgrounds(Arrays.asList(background))
                .subMenus(Arrays.asList(subMenu))
                .icons(Arrays.asList(icon))
                .appUser(appUser)
                .build();
        background.setMainMenu(newMainMenu);
        background.setChosenMainMenu(newMainMenu);
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
