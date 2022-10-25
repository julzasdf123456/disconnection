package com.lopez.julz.disconnection.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DisconnectionList {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "AccountNumber")
    private String AccountNumber;

    @ColumnInfo(name = "ServiceAccountName")
    private String ServiceAccountName;

    @ColumnInfo(name = "Address")
    private String Address;

    @ColumnInfo(name = "IsUploaded")
    private String IsUploaded;

    @ColumnInfo(name = "Latitude")
    private String Latitude;

    @ColumnInfo(name = "Longitude")
    private String Longitude;

    @ColumnInfo(name = "ServicePeriod")
    private String ServicePeriod;

    @ColumnInfo(name = "AreaCode")
    private String AreaCode;

    @ColumnInfo(name = "ConsumerType")
    private String ConsumerType;

    @ColumnInfo(name = "OldAccountNo")
    private String OldAccountNo;

    @ColumnInfo(name = "MeterNumber")
    private String MeterNumber;

    @ColumnInfo(name = "SequenceCode")
    private String SequenceCode;

    @ColumnInfo(name = "TicketId")
    private String TicketId;

    @ColumnInfo(name = "Arrears")
    private String Arrears;

    @ColumnInfo(name = "ArrearsNoOfMonths")
    private String ArrearsNoOfMonths;

    @ColumnInfo(name = "GroupCode")
    private String GroupCode;

    @ColumnInfo(name = "MeterReader")
    private String MeterReader;

    @ColumnInfo(name = "Town")
    private String Town;

    /**
     * DISCONNECTION HISTORY FIELDS
     */
    @ColumnInfo(name = "LatitudeCaptured")
    private String LatitudeCaptured;

    @ColumnInfo(name = "LongitudeCaptured")
    private String LongitudeCaptured;

    @ColumnInfo(name = "DateDisconnected")
    private String DateDisconnected;

    @ColumnInfo(name = "TimeDisconnected")
    private String TimeDisconnected;

    @ColumnInfo(name = "LastReading")
    private String LastReading;

    @ColumnInfo(name = "UserId")
    private String UserId;

    public DisconnectionList() {
    }

    public DisconnectionList(@NonNull String id, String accountNumber, String serviceAccountName, String address, String isUploaded, String latitude, String longitude, String servicePeriod, String areaCode, String consumerType, String oldAccountNo, String meterNumber, String sequenceCode, String ticketId, String arrears, String arrearsNoOfMonths, String groupCode, String meterReader, String town, String latitudeCaptured, String longitudeCaptured, String dateDisconnected, String timeDisconnected, String lastReading, String userId) {
        this.id = id;
        AccountNumber = accountNumber;
        ServiceAccountName = serviceAccountName;
        Address = address;
        IsUploaded = isUploaded;
        Latitude = latitude;
        Longitude = longitude;
        ServicePeriod = servicePeriod;
        AreaCode = areaCode;
        ConsumerType = consumerType;
        OldAccountNo = oldAccountNo;
        MeterNumber = meterNumber;
        SequenceCode = sequenceCode;
        TicketId = ticketId;
        Arrears = arrears;
        ArrearsNoOfMonths = arrearsNoOfMonths;
        GroupCode = groupCode;
        MeterReader = meterReader;
        Town = town;
        LatitudeCaptured = latitudeCaptured;
        LongitudeCaptured = longitudeCaptured;
        DateDisconnected = dateDisconnected;
        TimeDisconnected = timeDisconnected;
        LastReading = lastReading;
        UserId = userId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getServiceAccountName() {
        return ServiceAccountName;
    }

    public void setServiceAccountName(String serviceAccountName) {
        ServiceAccountName = serviceAccountName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIsUploaded() {
        return IsUploaded;
    }

    public void setIsUploaded(String isUploaded) {
        IsUploaded = isUploaded;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getServicePeriod() {
        return ServicePeriod;
    }

    public void setServicePeriod(String servicePeriod) {
        ServicePeriod = servicePeriod;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getConsumerType() {
        return ConsumerType;
    }

    public void setConsumerType(String consumerType) {
        ConsumerType = consumerType;
    }

    public String getMeterNumber() {
        return MeterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        MeterNumber = meterNumber;
    }

    public String getSequenceCode() {
        return SequenceCode;
    }

    public void setSequenceCode(String sequenceCode) {
        SequenceCode = sequenceCode;
    }

    public String getTicketId() {
        return TicketId;
    }

    public void setTicketId(String ticketId) {
        TicketId = ticketId;
    }

    public String getLatitudeCaptured() {
        return LatitudeCaptured;
    }

    public void setLatitudeCaptured(String latitudeCaptured) {
        LatitudeCaptured = latitudeCaptured;
    }

    public String getLongitudeCaptured() {
        return LongitudeCaptured;
    }

    public void setLongitudeCaptured(String longitudeCaptured) {
        LongitudeCaptured = longitudeCaptured;
    }

    public String getDateDisconnected() {
        return DateDisconnected;
    }

    public void setDateDisconnected(String dateDisconnected) {
        DateDisconnected = dateDisconnected;
    }

    public String getTimeDisconnected() {
        return TimeDisconnected;
    }

    public void setTimeDisconnected(String timeDisconnected) {
        TimeDisconnected = timeDisconnected;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getOldAccountNo() {
        return OldAccountNo;
    }

    public void setOldAccountNo(String oldAccountNo) {
        OldAccountNo = oldAccountNo;
    }

    public String getLastReading() {
        return LastReading;
    }

    public void setLastReading(String lastReading) {
        LastReading = lastReading;
    }

    public String getArrears() {
        return Arrears;
    }

    public void setArrears(String arrears) {
        Arrears = arrears;
    }

    public String getArrearsNoOfMonths() {
        return ArrearsNoOfMonths;
    }

    public void setArrearsNoOfMonths(String arrearsNoOfMonths) {
        ArrearsNoOfMonths = arrearsNoOfMonths;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }

    public String getMeterReader() {
        return MeterReader;
    }

    public void setMeterReader(String meterReader) {
        MeterReader = meterReader;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }
}
