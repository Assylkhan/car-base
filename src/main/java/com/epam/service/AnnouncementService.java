package com.epam.service;

import com.epam.dao.AnnouncementDao;
import com.epam.dao.DaoFactory;
import com.epam.dao.DaoManager;
import com.epam.entity.Announcement;

import java.util.List;

public class AnnouncementService {
    private DaoFactory daoFactory;

    public AnnouncementService(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public List<Announcement> findRecentWithLimit(int amount){
        try(DaoManager daoManager = daoFactory.getDaoManager()){
            AnnouncementDao announcementDao = daoManager.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findLastWithLimit(amount);
            return announcements;
        }
    }

    public List<Announcement> findAll(){
        try(DaoManager daoManager = daoFactory.getDaoManager()){
            AnnouncementDao announcementDao = daoManager.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findAll();
            return announcements;
        }
    }

    public Announcement insert(Announcement announcement){
        /*try (DaoManager daoManager = daoFactory.getDaoManager()) {
            AnnouncementDao announcementDao = daoManager.getAnnouncementDao();
            Announcement insertedAnnouncement = announcementDao.insert(announcement);
            return insertedAnnouncement;
        }*/
        return null;
    }
}
