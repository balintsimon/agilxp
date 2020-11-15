package com.drbsimon.server.dao;

import com.drbsimon.server.model.MainMenu;
import com.drbsimon.server.repository.MainMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MainMenuDaoDb implements MainMenuDao {
    private final MainMenuRepository repository;

    public void save(MainMenu mainMenu) {
        repository.save(mainMenu);
    }
}
