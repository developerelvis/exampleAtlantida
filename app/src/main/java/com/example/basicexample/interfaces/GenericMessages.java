package com.example.basicexample.interfaces;

import com.example.basicexample.data.enums.ErrorType;

public interface GenericMessages {
    void showProgress();
    void hideProgress();
    void error(ErrorType errorType, String message);
    void success(int id);
}
