package com.example.basicexample.ui.auth;

import androidx.annotation.Nullable;

public class LoginFormState {
    @Nullable
    private final Integer usernameError;
    @Nullable
    private final Integer passwordError;
    private final boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState() {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = true;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
