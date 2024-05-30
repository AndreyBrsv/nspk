package com.mycompany.interviewtask.model;

import lombok.Getter;

@Getter
public enum PrivilegeStatus {
    NO_CARD(0),
    SILVER(10),
    GOLD(100),
    PLATINUM(1000);

    private final int rating;

    PrivilegeStatus(int rating) {
        this.rating = rating;
    }

    public static PrivilegeStatus getStatus(String status) {
        try {
            return PrivilegeStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return PrivilegeStatus.NO_CARD;
        }
    }
}
