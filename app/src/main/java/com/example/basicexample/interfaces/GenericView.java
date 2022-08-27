package com.example.basicexample.interfaces;

import com.example.basicexample.data.enums.ErrorType;
public interface GenericView<T> {
    void showProgress();
    void hideProgress();
    void error(ErrorType errorType, String message);
    void success(T result);
}