package com.drbsimon.server.service;

import com.drbsimon.server.dao.SubMenuDao;
import com.drbsimon.server.model.MainMenu;
import com.drbsimon.server.model.wrapper.SubMenuWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class SubMenuService {
    private final SubMenuDao subMenuDao;
    private final MainMenuService mainMenuService;

    public SubMenuWrapper getAllSubMenusOfUser(Long userId) {
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(userId);
        return new SubMenuWrapper(userMainMenu.getSubMenus());
    }
}
