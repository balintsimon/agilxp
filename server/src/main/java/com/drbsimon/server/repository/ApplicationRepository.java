package com.drbsimon.server.repository;

import com.drbsimon.server.model.Application;
import com.drbsimon.server.model.MainMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAll();

    Application getById(Long id);
}
