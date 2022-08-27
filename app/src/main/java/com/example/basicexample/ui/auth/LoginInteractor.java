package com.example.basicexample.ui.auth;

import androidx.annotation.NonNull;

import com.example.basicexample.data.dto.AccountDto;
import com.example.basicexample.data.dto.UserDto;
import com.example.basicexample.data.enums.ErrorType;
import com.example.basicexample.data.network.HttpClient;
import com.example.basicexample.data.network.HttpHeader;
import com.example.basicexample.data.network.RequestError;
import com.example.basicexample.data.network.RequestResponse;
import com.example.basicexample.data.network.interfaces.Authentication;
import com.example.basicexample.interfaces.OnFinish;
import com.example.basicexample.utilities.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor {
    private final String responseBody = "Response body empty.";
    private final String email = "email";
    private final String value = "value";

    void authenticate(AccountDto accountDto, OnFinish<RequestResponse<UserDto>> listener) throws Exception {
        JsonObject credentials = new JsonObject();
        credentials.addProperty(email, accountDto.getUsername());
        credentials.addProperty(value, accountDto.getPassword());

        Authentication service = new HttpClient().getConnection().create(Authentication.class);
        Call<RequestResponse<UserDto>> request = service.authenticate();
        request.enqueue(new Callback<RequestResponse<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<RequestResponse<UserDto>> call, @NonNull Response<RequestResponse<UserDto>> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    RequestError requestError = null;
                    if (response.errorBody() != null) {
                        requestError = gson.fromJson(response.errorBody().charStream(), RequestError.class);
                    }

                    if (response.code() == 410) listener.error(ErrorType.HttpGone, requestError);
                    else if (response.code() == 404) listener.error(ErrorType.HttpNotFound, requestError);
                    else listener.error(ErrorType.HttpFailed, requestError);
                }
                else
                if (response.body() == null) listener.error(ErrorType.NotJsonObject, responseBody);
                else listener.success(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RequestResponse<UserDto>> call, @NonNull Throwable t) {
                listener.error(ErrorType.HttpFailed, t.getMessage());
            }
        });
    }
}
