package com.drbsimon.server.dao;

import com.drbsimon.server.model.SubMenu;
import com.drbsimon.server.repository.SubMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubMenuDaoDb implements SubMenuDao {
    private final SubMenuRepository repository;

    public void save(SubMenu subMenu) {
        repository.save(subMenu);
    }
}
