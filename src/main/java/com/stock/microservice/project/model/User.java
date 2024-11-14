package com.stock.microservice.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private int id;
    private String userName;
    private String company;

    public User(int id, String userName, String company) {
        this.id = id;
        this.userName = userName;
        this.company = company;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}