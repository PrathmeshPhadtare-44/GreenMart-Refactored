package com.greenmart.model;

import java.sql.Timestamp;

import lombok.Data;
@Data
public class User {
 
    private int userid;

    private String userName;
    private String email;

    private String password;
    private String phoneNumber;
    

    private String address;

    private String role;  

    private Timestamp created_at;
}