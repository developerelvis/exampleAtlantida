package com.example.basicexample.data.network.interfaces;

import com.example.basicexample.data.enums.ErrorType;
import com.example.basicexample.data.network.RequestError;

public interface HttpResponse<T> {
    void error(ErrorType errorType, String message);
    void error(ErrorType errorType, RequestError requestError);
    void success(T result);
}
