package com.drbsimon.server.repository;

import com.drbsimon.server.model.Application;
import com.drbsimon.server.model.MainMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainMenuRepository extends JpaRepository<MainMenu, Long> {
}
