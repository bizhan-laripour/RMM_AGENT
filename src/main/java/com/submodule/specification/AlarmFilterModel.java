package com.submodule.specification;


import com.submodule.annotation.SearchField;
import com.submodule.enums.Category;
import com.submodule.enums.SearchType;


import java.util.Date;

public class AlarmFilterModel {


    @SearchField(type = SearchType.GREATER_THAN)
    private Date date;

    @SearchField(type = SearchType.EQUAL_TO)
    private Category category;

    @SearchField(type = SearchType.GREATER_THAN)
    private double cpuLoad;
    @SearchField(type = SearchType.GREATER_THAN)
    private double networkBandWith;

    @SearchField(type = SearchType.GREATER_THAN)
    private double memoryUsage;

    @SearchField(type = SearchType.GREATER_THAN)
    private double cpuTemperature;

    @SearchField(type = SearchType.GREATER_THAN)
    private double motherboardTemperature;

    @SearchField(type = SearchType.EQUAL_TO)
    private String ip;

    @SearchField(type = SearchType.EQUAL_TO)
    private String thresholdUUID;

    @SearchField(type = SearchType.GREATER_THAN)
    private String timePeriodInSec;

    private Integer pageNumber;

    private Integer pageSize;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public double getNetworkBandWith() {
        return networkBandWith;
    }

    public void setNetworkBandWith(double networkBandWith) {
        this.networkBandWith = networkBandWith;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public double getCpuTemperature() {
        return cpuTemperature;
    }

    public void setCpuTemperature(double cpuTemperature) {
        this.cpuTemperature = cpuTemperature;
    }

    public double getMotherboardTemperature() {
        return motherboardTemperature;
    }

    public void setMotherboardTemperature(double motherboardTemperature) {
        this.motherboardTemperature = motherboardTemperature;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getThresholdUUID() {
        return thresholdUUID;
    }

    public void setThresholdUUID(String thresholdUUID) {
        this.thresholdUUID = thresholdUUID;
    }

    public String getTimePeriodInSec() {
        return timePeriodInSec;
    }

    public void setTimePeriodInSec(String timePeriodInSec) {
        this.timePeriodInSec = timePeriodInSec;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
