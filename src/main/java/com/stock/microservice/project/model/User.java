package com.stock.microservice.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String userName;
    private String company;

    public User() {}

    public User(String userName, String company) {
        this.userName = userName;
        this.company = company;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
