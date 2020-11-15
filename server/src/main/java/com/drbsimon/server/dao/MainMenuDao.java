package com.drbsimon.server.dao;

import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.MainMenu;

public interface MainMenuDao {
    MainMenu getBy(AppUser appUser);

    void save(MainMenu mainMenu);
}
