package com.drbsimon.server.dao;

import com.drbsimon.server.model.Icon;
import com.drbsimon.server.model.MainMenu;
import com.drbsimon.server.repository.IconRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IconDaoDb implements IconDao {
    private final IconRepository repository;

    @Override
    public List<Icon> getAll() {
        return repository.getAll();
    }

    @Override
    public Icon getBy(MainMenu mainMenu) {
        return repository.getByMainMenus(mainMenu);
    }

    @Override
    public Icon getBy(String name) {
        return repository.getByName(name);
    }

    @Override
    public void save(Icon icon) {
        repository.save(icon);
    }

    @Override
    public void remove(String name) {
        repository.removeByName(name);
    }
}
