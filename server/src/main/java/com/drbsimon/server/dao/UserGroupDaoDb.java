package com.drbsimon.server.dao;

import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.UserGroup;
import com.drbsimon.server.repository.UserGroupRepository;
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
public class UserGroupDaoDb implements UserGroupDao {
    private final UserGroupRepository repository;

    @Override
    public List<UserGroup> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserGroup> findBy(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<UserGroup> findBy(String groupName) {
        return repository.findByName(groupName);
    }

    @Override
    public UserGroup getBy(Long id) {
        return repository.getById(id);
    }

    @Override
    public UserGroup getBy(String groupName) {
        return repository.getByName(groupName);
    }

    @Override
    public boolean exists(String groupName) {
        return findBy(groupName).isPresent();
    }

    @Override
    public void save(UserGroup userGroup) {
        repository.save(userGroup);
    }

    @Override
    public UserGroup findByAppUser(AppUser appUser) {
        return repository.findByAppUsers(appUser);
    }
}
