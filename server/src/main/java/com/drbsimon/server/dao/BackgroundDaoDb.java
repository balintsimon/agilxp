package com.drbsimon.server.dao;

import com.drbsimon.server.model.Background;
import com.drbsimon.server.repository.BackgroundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BackgroundDaoDb implements BackgroundDao {
    private final BackgroundRepository repository;

    @Override
    public Background getBy(String name) {
        return repository.getByName(name);
    }

    @Override
    public void save(Background background) {
        repository.save(background);
    }
}
