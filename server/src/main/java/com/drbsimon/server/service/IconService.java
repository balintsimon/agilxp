package com.drbsimon.server.service;

import com.drbsimon.server.dao.IconDao;
import com.drbsimon.server.model.Icon;
import com.drbsimon.server.model.MainMenu;
import com.drbsimon.server.model.dto.IconDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class IconService {
    private final IconDao iconDao;
    private final MainMenuService mainMenuService;

    public boolean addNewIcon(IconDto iconDto) {
        if (iconDto.getUserId() == null
                || iconDto.getNewIconName() == null
                || iconDto.getNewIconName().isEmpty()
        ) return false;
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(iconDto.getUserId());
        if (userMainMenu == null || userMainMenu.getId() == null) return false;
        List<Icon> icons = userMainMenu.getIcons();

        
        Icon newIcon = Icon.builder()
                .name(iconDto.getNewIconName())
                .mainMenus(Arrays.asList(userMainMenu))
                .build();
        iconDao.save(newIcon);

        icons.add(newIcon);
        userMainMenu.setIcons(icons);
        mainMenuService.save(userMainMenu);
        return true;
    }

    public boolean modifyIcon(IconDto iconDto) {
        if (iconDto.getNewIconName() == null
                || iconDto.getOldIconName() == null
                || iconDto.getNewIconName().isEmpty()
                || iconDto.getOldIconName().isEmpty()
        ) return false;
        Icon iconToModify = iconDao.getBy(iconDto.getOldIconName());
        if (iconToModify == null || iconToModify.getId() == null) return false;
        iconToModify.setName(iconDto.getNewIconName());
        iconDao.save(iconToModify);
        return true;
    }

    public void removeIcon(String name) {
        iconDao.remove(name);
    }
}
