package com.drbsimon.server.repository;

import com.drbsimon.server.model.Background;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, Long> {
}
