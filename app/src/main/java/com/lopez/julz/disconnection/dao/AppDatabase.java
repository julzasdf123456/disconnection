package com.lopez.julz.disconnection.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        Users.class,
        DisconnectionList.class
}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersDao usersDao();

    public abstract DisconnectionListDao disconnectionListDao();
}
