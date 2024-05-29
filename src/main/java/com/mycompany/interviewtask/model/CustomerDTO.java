package com.mycompany.interviewtask.model;

import lombok.Data;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private PrivilegeStatus status;
    private Integer numberOfPurchases;
    private Integer numberOfReturns;
    private String phoneNumber;

    public Integer rating() {
        int rating = this.numberOfPurchases - this.numberOfReturns;

        if (rating < 0) {
            return status.getRating();
        } else {
            return rating + status.getRating();
        }
    }
}
