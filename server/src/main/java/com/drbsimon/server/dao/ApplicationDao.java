package com.drbsimon.server.dao;

import com.drbsimon.server.model.Application;

public interface ApplicationDao {
    Application getBy(Long id);

    Application getBy(String name);

    void save(Application application);
}
