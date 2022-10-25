package com.lopez.julz.disconnection.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lopez.julz.disconnection.objects.DiscoGroup;

import java.util.List;

@Dao
public interface DisconnectionListDao {
    @Query("SELECT * FROM DisconnectionList WHERE (IsUploaded='No' OR IsUploaded='UPLOADABLE') AND GroupCode = :groupCode AND MeterReader = :meterReader AND Town = :town AND ServicePeriod = :period ORDER BY CAST(SequenceCode AS INT)")
    List<DisconnectionList> getAll(String groupCode, String meterReader, String town, String period);

    @Query("SELECT * FROM DisconnectionList WHERE (IsUploaded='No' OR IsUploaded='UPLOADABLE') AND GroupCode IS NULL AND MeterReader IS NULL AND Town = :town AND ServicePeriod = :period ORDER BY CAST(SequenceCode AS INT)")
    List<DisconnectionList> getAllBapa(String town, String period);

    @Insert
    void insertAll(DisconnectionList... disconnectionLists);

    @Update
    void updateAll(DisconnectionList... disconnectionLists);

    @Query("SELECT * FROM DisconnectionList WHERE AccountNumber = :accountNumber AND ServicePeriod = :servicePeriod LIMIT 1")
    DisconnectionList getOne(String accountNumber, String servicePeriod);

    @Query("SELECT * FROM DisconnectionList WHERE IsUploaded='UPLOADABLE'")
    List<DisconnectionList> getUploadable();

    @Query("SELECT * FROM DisconnectionList WHERE (ServiceAccountName LIKE :regex OR MeterNumber LIKE :regex OR OldAccountNo LIKE :regex) AND GroupCode = :groupCode AND MeterReader = :meterReader AND Town = :town AND ServicePeriod = :period AND (IsUploaded='No' OR IsUploaded='UPLOADABLE') ORDER BY CAST(SequenceCode AS INT)")
    List<DisconnectionList> getSearch(String regex, String groupCode, String meterReader, String town, String period);

    @Query("SELECT * FROM DisconnectionList WHERE (ServiceAccountName LIKE :regex OR MeterNumber LIKE :regex OR OldAccountNo LIKE :regex) AND GroupCode IS NULL AND MeterReader IS NULL AND Town = :town AND ServicePeriod = :period AND (IsUploaded='No' OR IsUploaded='UPLOADABLE') ORDER BY CAST(SequenceCode AS INT)")
    List<DisconnectionList> getSearchBapa(String regex, String town, String period);

    @Query("SELECT MeterReader, GroupCode, Town, ServicePeriod FROM DisconnectionList WHERE IsUploaded='No' OR IsUploaded='UPLOADABLE' GROUP BY MeterReader, GroupCode, Town, ServicePeriod")
    List<DiscoGroup> getGroupings();
}
