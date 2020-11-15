package com.drbsimon.server.repository;

import com.drbsimon.server.model.Icon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconRepository extends JpaRepository<Icon, Long> {
}
