package com.drbsimon.server.dao;

import com.drbsimon.server.model.Background;

public interface BackgroundDao {
    void save(Background background);
    Background getBy(String name);
}
