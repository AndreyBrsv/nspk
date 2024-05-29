package com.mycompany.interviewtask.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("customers")
public class CustomerDO {
    @Id
    private Long id;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("phone_number")
    private String phoneNumber;
    @Column("rating")
    private Integer rating;
}
