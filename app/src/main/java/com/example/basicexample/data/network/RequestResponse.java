package com.example.basicexample.data.network;

import com.google.gson.annotations.SerializedName;

public class RequestResponse<T> {
    @SerializedName("code")
    public int code;

    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public T data;
}
