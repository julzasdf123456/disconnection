package com.lopez.julz.disconnection.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        Users.class,
        DisconnectionList.class,
        Settings.class
}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersDao usersDao();

    public abstract DisconnectionListDao disconnectionListDao();

    public abstract SettingsDao settingsDao();
}
