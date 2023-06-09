package com.backend.jpaadvance.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String salary;
    private Boolean isActive;
    private String company;
    @Column(columnDefinition = "TEXT")
    private String about;
}
