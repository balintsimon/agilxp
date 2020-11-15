package com.drbsimon.server.dao;

import com.drbsimon.server.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserDao {
    List<AppUser> findAll();

    Optional<AppUser> findBy(Long id);

    Optional<AppUser> findBy(String userName);

    boolean exists(String userName);

    void save(AppUser appUser);
}
