package com.example.basicexample.data.enums;

public enum ErrorType {
    // HttpClient
    HttpRequest,
    RetrofitURL,
    ParseJson,
    HttpFailed,
    NotJsonObject,
    EmptyValue,
    Forbidden,
    Unauthorized,
    HttpGone,
    HttpNotFound,

    // Repository
    NotSaved,
    NotDeleted,
}
