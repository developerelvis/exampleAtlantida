package com.example.basicexample.interfaces;

import com.example.basicexample.data.enums.ErrorType;
import com.example.basicexample.data.network.RequestError;

public interface OnFinish<T> {
    void error(ErrorType errorType, String message);
    void error(ErrorType errorType, RequestError requestError);
    void success(T result);
}
