package com.drbsimon.server.repository;

import com.drbsimon.server.model.AppUser;
import com.drbsimon.server.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Optional<UserGroup> findById(Long id);

    Optional<UserGroup> findByName(String name);

    UserGroup findByAppUsers(AppUser appUser);
}
