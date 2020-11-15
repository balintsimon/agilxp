package com.drbsimon.server.dao;

import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AppUserDaoDb implements AppUserDao {
    private final AppUserRepository repository;

    @Override
    public List<AppUser> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AppUser> findBy(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<AppUser> findBy(String userName) {
        return repository.findByName(userName);
    }

    @Override
    public boolean exists(String userName) {
        return findBy(userName).isPresent();
    }

    @Override
    public void save(AppUser appUser) {
        repository.save(appUser);
    }
}
