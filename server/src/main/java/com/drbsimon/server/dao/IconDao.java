package com.drbsimon.server.dao;

import com.drbsimon.server.model.Icon;
import com.drbsimon.server.model.MainMenu;

import java.util.List;

public interface IconDao {
    List<Icon> getAll();

    Icon getBy(MainMenu mainMenu);

    Icon getBy(String name);

    void save(Icon icon);

    void remove(String name);
}
