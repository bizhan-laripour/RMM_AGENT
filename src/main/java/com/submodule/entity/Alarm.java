//package com.submodule.entity;
//
//import com.submodule.enums.Category;
//import jakarta.persistence.*;
//import org.rmm.enums.Category;
//
//import java.util.Date;
//
//@Entity
//public class Alarm extends BaseEntity{
//
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date date;
//
//    @Enumerated(EnumType.STRING)
//    private Category category;
//
//    private double cpuLoad;
//
//    private double networkBandWith;
//
//    private double memoryUsage;
//
//    private double cpuTemperature;
//
//    private double motherboardTemperature;
//
//
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public double getCpuLoad() {
//        return cpuLoad;
//    }
//
//    public void setCpuLoad(double cpuLoad) {
//        this.cpuLoad = cpuLoad;
//    }
//
//    public double getNetworkBandWith() {
//        return networkBandWith;
//    }
//
//    public void setNetworkBandWith(double networkBandWith) {
//        this.networkBandWith = networkBandWith;
//    }
//
//    public double getMemoryUsage() {
//        return memoryUsage;
//    }
//
//    public void setMemoryUsage(double memoryUsage) {
//        this.memoryUsage = memoryUsage;
//    }
//
//    public double getCpuTemperature() {
//        return cpuTemperature;
//    }
//
//    public void setCpuTemperature(double cpuTemperature) {
//        this.cpuTemperature = cpuTemperature;
//    }
//
//    public double getMotherboardTemperature() {
//        return motherboardTemperature;
//    }
//
//    public void setMotherboardTemperature(double motherboardTemperature) {
//        this.motherboardTemperature = motherboardTemperature;
//    }
//}
