package com.example.basicexample.data.network;

import android.util.Base64;
import android.util.Log;

import com.example.basicexample.BuildConfig;
import com.example.basicexample.utilities.Connectivity;
import com.google.gson.JsonObject;

public class HttpHeader {
    private int userId;
    private int workStationId;

    public HttpHeader() { }

    public HttpHeader(int userId, int workStationId) {
        this.userId = userId;
        this.workStationId = workStationId;
    }

    public int getUserId() {
        return userId;
    }

    public int getWorkStationId() {
        return workStationId;
    }

    public String getDataBase64(boolean addUser, boolean addWorkstation) {
        JsonObject json = new JsonObject();
        json.addProperty("componentId", 6);
        json.addProperty("ipAddress", Connectivity.getIPAddress());
        json.addProperty("userAgent", BuildConfig.APPLICATION_ID);
        if (addUser) {
            json.addProperty("userId", userId);
        }
        if (addWorkstation) {
            json.addProperty("workStationId", workStationId);
        }
        Log.d("Header", json.toString());
        return Base64.encodeToString(json.toString().getBytes(), Base64.DEFAULT).replaceAll("\n","");
    }
}
