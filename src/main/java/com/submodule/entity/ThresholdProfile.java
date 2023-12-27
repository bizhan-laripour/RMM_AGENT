//package com.submodule.entity;
//
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class ThresholdProfile extends BaseEntity{
//
//
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "PROFILE_ID")
//    private List<Threshold> thresholds;
//
//
//    @ManyToMany
//    @JoinTable(name = "SITE_THRESHOLD" , joinColumns = @JoinColumn(name = "PROFILE_ID") , inverseJoinColumns = @JoinColumn(name = "SITE_ID"))
//    private List<Site> sites;
//
//    @ManyToMany
//    @JoinTable(name = "CUSTOMER_THRESHOLD" , joinColumns = @JoinColumn(name = "PROFILE_ID") , inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID"))
//    private List<Customer> customers;
//
//    @ManyToMany
//    @JoinTable(name = "AGENT_THRESHOLD" , joinColumns = @JoinColumn(name = "PROFILE_ID") , inverseJoinColumns = @JoinColumn(name = "DEVICE_ID"))
//    private List<Device> devices;
//
//
//    public List<Site> getSites() {
//        return sites;
//    }
//
//    public void setSites(List<Site> sites) {
//        this.sites = sites;
//    }
//
//    public List<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(List<Customer> customers) {
//        this.customers = customers;
//    }
//
//    public List<Device> getDevices() {
//        return devices;
//    }
//
//    public void setDevices(List<Device> devices) {
//        this.devices = devices;
//    }
//
//    public List<Threshold> getThresholds() {
//        return thresholds;
//    }
//
//    public void setThresholds(List<Threshold> thresholds) {
//        this.thresholds = thresholds;
//    }
//}
