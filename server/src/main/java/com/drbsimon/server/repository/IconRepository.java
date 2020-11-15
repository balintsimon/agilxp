package com.drbsimon.server.repository;

import com.drbsimon.server.model.Icon;
import com.drbsimon.server.model.MainMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IconRepository extends JpaRepository<Icon, Long> {
    List<Icon> getAll();

    Icon getByMainMenus(MainMenu mainMenu);

    Icon getByName(String name);

    void removeByName(String name);
}
