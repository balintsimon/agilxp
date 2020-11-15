package com.drbsimon.server.dao;

import com.drbsimon.server.model.Application;
import com.drbsimon.server.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApplicationDaoDb implements ApplicationDao {
    private final ApplicationRepository repository;

    @Override
    public Application getBy(Long id) {
        return repository.getById(id);
    }

    @Override
    public void save(Application application) {
        repository.save(application);
    }
}
