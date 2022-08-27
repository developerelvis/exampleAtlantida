package com.example.basicexample.data.dto;

public class UserDto {
    private String message;
    private long userID;
    private String name;
    private String email;
    private int mobile;

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public long getUserID() { return userID; }
    public void setUserID(long value) { this.userID = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public long getMobile() { return mobile; }
    public void setMobile(int value) { this.mobile = value; }
}
