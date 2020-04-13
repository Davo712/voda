package com.company.voda.model;

import lombok.Data;

import javax.persistence.*;



@Entity
@Data
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean active;
    private String activationCode;


}
