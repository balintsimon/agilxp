package com.drbsimon.server.service;

import com.drbsimon.server.dao.BackgroundDao;
import com.drbsimon.server.model.Background;
import com.drbsimon.server.model.MainMenu;
import com.drbsimon.server.model.dto.BackgroundRequestDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class BackgroundService {
    private final BackgroundDao backgroundDao;
    private final MainMenuService mainMenuService;

    public boolean addNewBackground(BackgroundRequestDto backgroundRequestDto) {
        if (backgroundRequestDto.getUserId() == null
                || backgroundRequestDto.getBackgroundName() == null
                || backgroundRequestDto.getBackgroundName().isEmpty()
        ) return false;
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(backgroundRequestDto.getUserId());
        if (userMainMenu == null || userMainMenu.getId() == null) return false;
        List<Background> backgrounds = userMainMenu.getBackgrounds();

        Background newBackground = Background.builder()
                .name(backgroundRequestDto.getBackgroundName())
                .mainMenu(userMainMenu)
                .build();

        backgrounds.add(newBackground);

        userMainMenu.setBackgrounds(backgrounds);

        backgroundDao.save(newBackground);
        return true;
    }

    public boolean setBackgroundToMainMenu(BackgroundRequestDto backgroundRequestDto) {
        if (backgroundRequestDto.getUserId() == null
                || backgroundRequestDto.getBackgroundName() == null
                || backgroundRequestDto.getBackgroundName().isEmpty()
        ) return false;
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(backgroundRequestDto.getUserId());
        if (userMainMenu == null || userMainMenu.getId() == null) return false;
        Background background = backgroundDao.getBy(backgroundRequestDto.getBackgroundName());
        if (background == null || background.getId() == null) return false;

        userMainMenu.setBackground(background);
        background.setChosenMainMenu(userMainMenu);

        mainMenuService.save(userMainMenu);
        backgroundDao.save(background);
        return true;
    }
}
