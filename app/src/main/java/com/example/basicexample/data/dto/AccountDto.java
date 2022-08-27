package com.example.basicexample.data.dto;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

public class AccountDto extends BaseObservable {
    @SerializedName("email")
    private String username;

    @SerializedName("mobile")
    private String password;

    public AccountDto() {}

    public AccountDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

}
