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
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(backgroundRequestDto.getUserId());
        List<Background> backgrounds = userMainMenu.getBackgrounds();

        Background newBackground = Background.builder()
                .name(backgroundRequestDto.getBackgroundName())
                .mainMenu(userMainMenu)
                .build();

        backgrounds.add(newBackground);

        userMainMenu.setBackgrounds(backgrounds);

        mainMenuService.save(userMainMenu);
//        backgroundDao.save(newBackground);
        return true;
    }

    public boolean setBackgroundToMainMenu(BackgroundRequestDto backgroundRequestDto) {
        MainMenu userMainMenu = mainMenuService.getMainMenuByUser(backgroundRequestDto.getUserId());
        Background background = backgroundDao.getBy(backgroundRequestDto.getBackgroundName());

        userMainMenu.setBackground(background);
        background.setChosenMainMenu(userMainMenu);

        mainMenuService.save(userMainMenu);
        backgroundDao.save(background);
        return true;
    }
}
