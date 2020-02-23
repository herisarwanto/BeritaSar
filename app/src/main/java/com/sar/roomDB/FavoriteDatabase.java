package com.sar.roomDB;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sar.dao.FavoriteDao;

@Database(entities={FavoriteList.class},version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();


}
