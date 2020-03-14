package com.demo.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class Account implements Serializable{
    private long id;
    private String name;
    private String email;
    private String password;
}
