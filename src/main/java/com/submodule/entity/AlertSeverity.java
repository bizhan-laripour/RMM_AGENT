//package com.submodule.entity;
//
//
//import jakarta.persistence.*;
//import org.rmm.enums.Color;
//import org.rmm.enums.Criteria;
//import org.rmm.enums.CustomerSendType;
//import org.rmm.enums.SendType;
//
//@Entity
//public class AlertSeverity extends BaseEntity{
//
//    @Enumerated(EnumType.STRING)
//    private Color color;
//
//    @OneToOne
//    @JoinColumn(name = "THRESHOLD_ID")
//    private Threshold threshold;
//
//    private Integer delayToCreateTicket;
//
//    @Enumerated(EnumType.STRING)
//    private Criteria criteria;
//
//    private String emailText;
//
//    private String smsText;
//
//    private String ticketText;
//
//    @Enumerated(EnumType.STRING)
//    private SendType sendType;
//
//    @Enumerated(EnumType.STRING)
//    private CustomerSendType sendBySms;
//
//    @Enumerated(EnumType.STRING)
//    private CustomerSendType sendByEmail;
//
//
//    public Color getColor() {
//        return color;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
//
//    public Integer getDelayToCreateTicket() {
//        return delayToCreateTicket;
//    }
//
//    public void setDelayToCreateTicket(Integer delayToCreateTicket) {
//        this.delayToCreateTicket = delayToCreateTicket;
//    }
//
//    public Criteria getCriteria() {
//        return criteria;
//    }
//
//    public void setCriteria(Criteria criteria) {
//        this.criteria = criteria;
//    }
//
//    public String getEmailText() {
//        return emailText;
//    }
//
//    public void setEmailText(String emailText) {
//        this.emailText = emailText;
//    }
//
//    public String getSmsText() {
//        return smsText;
//    }
//
//    public void setSmsText(String smsText) {
//        this.smsText = smsText;
//    }
//
//    public String getTicketText() {
//        return ticketText;
//    }
//
//    public void setTicketText(String ticketText) {
//        this.ticketText = ticketText;
//    }
//
//    public SendType getSendType() {
//        return sendType;
//    }
//
//    public void setSendType(SendType sendType) {
//        this.sendType = sendType;
//    }
//
//    public CustomerSendType getSendBySms() {
//        return sendBySms;
//    }
//
//    public void setSendBySms(CustomerSendType sendBySms) {
//        this.sendBySms = sendBySms;
//    }
//
//    public CustomerSendType getSendByEmail() {
//        return sendByEmail;
//    }
//
//    public void setSendByEmail(CustomerSendType sendByEmail) {
//        this.sendByEmail = sendByEmail;
//    }
//
//    public Threshold getThreshold() {
//        return threshold;
//    }
//
//    public void setThreshold(Threshold threshold) {
//        this.threshold = threshold;
//    }
//}
