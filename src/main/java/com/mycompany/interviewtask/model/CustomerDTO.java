package com.mycompany.interviewtask.model;

import lombok.Data;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String status;
    private Integer numberOfPurchases;
    private Integer numberOfReturns;
    private String phoneNumber;

    public Integer rating() {
        int rating = this.numberOfPurchases - this.numberOfReturns;
        switch (this.status) {
            case "silver":
                rating = rating + 10;
                break;
            case "gold":
                rating = rating + 100;
                break;
            case "platinum":
                rating = rating + 1000;
                break;
        }

        return rating;
    }
}
