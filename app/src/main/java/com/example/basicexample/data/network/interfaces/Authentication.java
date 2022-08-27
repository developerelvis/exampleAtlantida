package com.example.basicexample.data.network.interfaces;

import com.example.basicexample.data.dto.UserDto;
import com.example.basicexample.data.network.RequestResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Authentication {
    @POST("474d568c-95a8-4976-b10a-fce64a9ebd80")
    Call<RequestResponse<UserDto>> authenticate();
}
