package com.mycompany.interviewtask.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

    @JsonCreator
    public static CustomerDTO forValues(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("status") String status,
            @JsonProperty("numberOfPurchases") Integer numberOfPurchases,
            @JsonProperty("numberOfReturns") Integer numberOfReturns,
            @JsonProperty("phoneNumber") String phoneNumber
    ) {
        return new CustomerDTO(
                firstName,
                lastName,
                PrivilegeStatus.getStatus(status),
                numberOfPurchases,
                numberOfReturns,
                phoneNumber
        );
    }
}
