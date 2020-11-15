package com.drbsimon.server.dao;

import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.UserGroup;

import java.util.List;
import java.util.Optional;

public interface UserGroupDao {
    List<UserGroup> findAll();

    Optional<UserGroup> findBy(Long id);

    Optional<UserGroup> findBy(String groupName);

    UserGroup getBy(Long id);

    UserGroup getBy(String groupName);

    boolean exists(String groupName);

    void save(UserGroup userGroup);

    UserGroup findByAppUser(AppUser appUser);
}
