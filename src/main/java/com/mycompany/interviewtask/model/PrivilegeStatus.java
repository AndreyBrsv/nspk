package com.mycompany.interviewtask.model;

import lombok.Getter;

@Getter
public enum PrivilegeStatus {
    SILVER(10),
    GOLD(100),
    PLATINUM(1000);

    private final int rating;

    PrivilegeStatus(int rating) {
        this.rating = rating;
    }
}
