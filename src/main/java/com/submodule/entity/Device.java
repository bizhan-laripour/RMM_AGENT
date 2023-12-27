//package com.submodule.entity;
//
//import jakarta.persistence.*;
//import org.rmm.enums.DeviceCategory;
//import org.rmm.enums.DeviceType;
//import org.rmm.enums.InstallWay;
//
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Table(name = "TBL_DEVICE")
//public class Device extends BaseEntity{
//
//
//    @ManyToMany
//    @JoinTable(name = "THRESHOLD_PAUSED_CONDITION" , joinColumns = @JoinColumn(name = "DEVICE_ID") , inverseJoinColumns = @JoinColumn(name = "THRESHOLD_ID"))
//    private List<Threshold> thresholds;
//
//    @OneToOne
//    @JoinColumn(name = "SITE_ID")
//    private Site siteOrSubSite;
//
//    @OneToOne
//    @JoinColumn(name = "CUSTOMER_ID")
//    private Customer customer;
//
//
//    @Enumerated(EnumType.STRING)
//    private DeviceCategory deviceCategory;
//
//    @Enumerated(EnumType.STRING)
//    private DeviceType deviceType;
//
//    private String version;
//
//    private String deviceName;
//
//    private String description;
//
//    private String hostName;
//
//    private String comunityString;
//
//    private String notes;
//
//    private Integer port;
//
//    @Enumerated(EnumType.STRING)
//    private InstallWay installWay;
//
//    private String username;
//
//    private String password;
//
//    private String rdpPort;
//
//    private String ipFQDN;
//
//    private Integer patchUpdate;
//
//    private Date dateTime;
//
//
//    public List<Threshold> getThresholds() {
//        return thresholds;
//    }
//
//    public void setThresholds(List<Threshold> thresholds) {
//        this.thresholds = thresholds;
//    }
//
//    public Site getSiteOrSubSite() {
//        return siteOrSubSite;
//    }
//
//    public void setSiteOrSubSite(Site siteOrSubSite) {
//        this.siteOrSubSite = siteOrSubSite;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public String getDeviceName() {
//        return deviceName;
//    }
//
//    public void setDeviceName(String deviceName) {
//        this.deviceName = deviceName;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getHostName() {
//        return hostName;
//    }
//
//    public void setHostName(String hostName) {
//        this.hostName = hostName;
//    }
//
//    public String getComunityString() {
//        return comunityString;
//    }
//
//    public void setComunityString(String comunityString) {
//        this.comunityString = comunityString;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
//
//    public Integer getPort() {
//        return port;
//    }
//
//    public void setPort(Integer port) {
//        this.port = port;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getRdpPort() {
//        return rdpPort;
//    }
//
//    public void setRdpPort(String rdpPort) {
//        this.rdpPort = rdpPort;
//    }
//
//    public String getIpFQDN() {
//        return ipFQDN;
//    }
//
//    public void setIpFQDN(String ipFQDN) {
//        this.ipFQDN = ipFQDN;
//    }
//
//    public Integer getPatchUpdate() {
//        return patchUpdate;
//    }
//
//    public void setPatchUpdate(Integer patchUpdate) {
//        this.patchUpdate = patchUpdate;
//    }
//
//    public Date getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(Date dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    public DeviceCategory getDeviceCategory() {
//        return deviceCategory;
//    }
//
//    public void setDeviceCategory(DeviceCategory deviceCategory) {
//        this.deviceCategory = deviceCategory;
//    }
//
//    public DeviceType getDeviceType() {
//        return deviceType;
//    }
//
//    public void setDeviceType(DeviceType deviceType) {
//        this.deviceType = deviceType;
//    }
//
//    public InstallWay getInstallWay() {
//        return installWay;
//    }
//
//    public void setInstallWay(InstallWay installWay) {
//        this.installWay = installWay;
//    }
//}
