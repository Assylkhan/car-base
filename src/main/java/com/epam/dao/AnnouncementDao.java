package com.epam.dao;

import com.epam.entity.Announcement;

import java.util.List;

public interface AnnouncementDao extends Dao {
    public List<Announcement> findLastWithLimit(int amount) throws DaoException;
}
