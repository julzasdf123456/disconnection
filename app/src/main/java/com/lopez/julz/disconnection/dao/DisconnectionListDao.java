package com.lopez.julz.disconnection.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DisconnectionListDao {
    @Query("SELECT * FROM DisconnectionList WHERE IsUploaded='No' OR IsUploaded='Uploadable'")
    List<DisconnectionList> getAll();

    @Insert
    void insertAll(DisconnectionList... disconnectionLists);

    @Update
    void updateAll(DisconnectionList... disconnectionLists);

    @Query("SELECT * FROM DisconnectionList WHERE AccountNumber = :accountNumber AND ServicePeriod = :servicePeriod LIMIT 1")
    DisconnectionList getOne(String accountNumber, String servicePeriod);

    @Query("SELECT * FROM DISCONNECTIONLIST WHERE IsUploaded='Uploadable'")
    List<DisconnectionList> getUploadable();
}
