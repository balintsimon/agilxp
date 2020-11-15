package com.drbsimon.server.repository;

import com.drbsimon.server.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findById(Long id);

    Optional<AppUser> findByName(String name);

    AppUser getById(Long id);

    AppUser getByName(String name);

    void removeById(Long id);
}
