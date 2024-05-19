package com.submodule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.submodule.enums.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Alarm {


    @Id
    @JsonIgnore
    private String id;
    private Date date;

    private Category category;

    private double cpuLoad;

    private double networkBandWith;

    private double memoryUsage;

    private double cpuTemperature;

    private double motherboardTemperature;

    private String ip;

    private String thresholdUUID;

    private String timePeriodInSec;

    public String getTimePeriodInSec() {
        return timePeriodInSec;
    }

    public void setTimePeriodInSec(String timePeriodInSec) {
        this.timePeriodInSec = timePeriodInSec;
    }

    public String getThresholdUUID() {
        return thresholdUUID;
    }

    public void setThresholdUUID(String thresholdUUID) {
        this.thresholdUUID = thresholdUUID;
    }

    private Boolean isActive = true;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

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
}
