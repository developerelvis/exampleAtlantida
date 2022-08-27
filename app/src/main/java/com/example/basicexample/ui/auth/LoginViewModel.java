package com.example.basicexample.ui.auth;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basicexample.R;
import com.example.basicexample.data.dto.AccountDto;
import com.example.basicexample.data.dto.UserDto;
import com.example.basicexample.data.enums.ErrorType;
import com.example.basicexample.data.network.RequestError;
import com.example.basicexample.data.network.RequestResponse;
import com.example.basicexample.interfaces.GenericView;
import com.example.basicexample.interfaces.OnFinish;

public class LoginViewModel extends ViewModel implements OnFinish<RequestResponse<UserDto>> {
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final GenericView<RequestResponse<UserDto>> loginView;
    private final LoginInteractor loginInteractor;
    private AccountDto accountDto;

    LoginViewModel(GenericView<RequestResponse<UserDto>> loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    void authenticate(AccountDto accountDto) {
        try {
            loginView.showProgress();
            this.accountDto = accountDto;
            loginInteractor.authenticate(accountDto, this);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.getMessage());
            loginView.error(ErrorType.RetrofitURL, e.getMessage());
        }
    }

    @Override
    public void error(ErrorType errorType, String message) {
        loginView.error(errorType, message);
    }

    @Override
    public void error(ErrorType errorType, RequestError requestError) {
        if(requestError != null)
        {
            loginView.error(errorType,
                    TextUtils.isEmpty(requestError.getError()) ?
                            TextUtils.isEmpty(requestError.getMessage()) ? requestError.getErrors().toString() : requestError.getMessage()
                            : requestError.getError());
        }
        else loginView.error(errorType,"There is no connection to the server.");
    }

    @Override
    public void success(RequestResponse<UserDto> result) {
        if (loginView != null) {
            try {
                if (!result.success) {
                    loginView.error(ErrorType.Forbidden, result.message);
                    return;
                }

                loginView.hideProgress();
                loginView.success(result);
            }
            catch (Exception e) {
                loginView.error(ErrorType.ParseJson, e.getMessage());
            }
        }
    }

    public void userNameChanged(String username) {
        if (!isUserNameValid(username))
            loginFormState.setValue(new LoginFormState(R.string.login_invalid_username, null));
        else
            loginFormState.setValue(new LoginFormState());
    }

    public void passwordChanged(String password) {
        if (!isPasswordValid(password))
            loginFormState.setValue(new LoginFormState(null, R.string.login_invalid_password));
        else
            loginFormState.setValue(new LoginFormState());
    }

    private boolean isUserNameValid(String username) {
        if (TextUtils.isEmpty(username)) return false;
        if (username.contains("@")) return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        else return false;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
