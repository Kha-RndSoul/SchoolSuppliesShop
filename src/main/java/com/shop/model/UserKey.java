package com.shop.model;

import java.sql.Timestamp;

public class UserKey {

    private int id;
    private int customerId;
    private String publicKey;
    private boolean isActive;       // true: đang dùng, false: đã hủy
    private String source;
    private String fileName;
    private Timestamp createdAt;
    private Timestamp reportedLostAt; // null = chưa báo mất

    public UserKey() {}

    public UserKey(int customerId, String publicKey, String source) {
        this.customerId = customerId;
        this.publicKey  = publicKey;
        this.source     = source;
        this.isActive   = true;
    }

    public int getId() {
        return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() {
        return customerId; }
    public void setCustomerId(int customerId) {
        this.customerId = customerId; }

    public String getPublicKey() {
        return publicKey; }
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey; }

    public boolean isActive() {
        return isActive; }
    public void setActive(boolean active) {
        isActive = active; }

    public String getSource() {
        return source; }
    public void setSource(String source) {
        this.source = source; }

    public String getFileName() {
        return fileName; }
    public void setFileName(String fileName) {
        this.fileName = fileName; }

    public Timestamp getCreatedAt() {
        return createdAt; }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt; }

    public Timestamp getReportedLostAt() {
        return reportedLostAt; }
    public void setReportedLostAt(Timestamp reportedLostAt) {
        this.reportedLostAt = reportedLostAt; }

    @Override
    public String toString() {
        return "UserKey{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", isActive=" + isActive +
                ", source='" + source + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createdAt=" + createdAt +
                ", reportedLostAt=" + reportedLostAt +
                '}';
    }
}