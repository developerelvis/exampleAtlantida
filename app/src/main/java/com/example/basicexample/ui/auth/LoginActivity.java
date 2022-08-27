package com.example.basicexample.ui.auth;

import static com.example.basicexample.utilities.Commons.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.basicexample.BuildConfig;
import com.example.basicexample.databinding.ActivityLoginBinding;
import com.example.basicexample.ui.Dashboard.MainActivity;
import com.example.basicexample.R;
import com.example.basicexample.data.dto.AccountDto;
import com.example.basicexample.data.dto.UserDto;
import com.example.basicexample.data.enums.ErrorType;
import com.example.basicexample.data.network.RequestResponse;
import com.example.basicexample.interfaces.GenericView;
import com.example.basicexample.utilities.Commons;
import com.example.basicexample.utilities.Connectivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements
        GenericView<RequestResponse<UserDto>> {
    private ActivityLoginBinding binding;
    private LoginViewModel presenter;
    private AlertDialog loading;
    private String message;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100,REQUEST_PERMISSION_SETTING = 101;
    private String[] permissionsRequired = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.setAccount(new AccountDto());
        presenter = new LoginViewModel(this, new LoginInteractor());
        presenter.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) return;
            if (loginFormState.getUsernameError() != null)
                binding.tvInputLayoutUsername.setError(getString(loginFormState.getUsernameError()));
            else binding.tvInputLayoutUsername.setError("");

            if (loginFormState.getPasswordError() != null)
                binding.tvInputLayoutPassword.setError(getString(loginFormState.getPasswordError()));
            else binding.tvInputLayoutPassword.setError("");

            binding.btnLogin.setEnabled(loginFormState.isDataValid());
            if (loginFormState.isDataValid()) {
                if (binding.getAccount() != null) {
                    binding.getAccount().setUsername(Objects.requireNonNull(binding.loginInputUsername.getText()).toString());
                    binding.getAccount().setPassword(Objects.requireNonNull(binding.loginInputPassword.getText()).toString());
                }
            }
        });

        binding.loginInputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)){
                    presenter.userNameChanged(s.toString());
                }
            }
        });
        binding.loginInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) presenter.passwordChanged(s.toString());
            }
        });

        binding.btnLogin.setOnClickListener(v ->
                //goToMenu());
                authOnline());
        //binding.btnLogin.setEnabled(false);

        binding.loginInputUsername.setText("appocbtest@gmail.com");
        binding.loginInputPassword.setText("1234567890");

        permissions();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showProgress() {
        //binding.btnLogin.setEnabled(false);
        loading = Commons.showLoading(this, message, false);
        loading.show();
    }

    @Override
    public void hideProgress() {
        if (loading != null) {
            if (loading.isShowing()) loading.dismiss();
        }
        //binding.btnLogin.setEnabled(true);
    }

    @Override
    public void error(ErrorType errorType, String message) {
        if (loading != null) {
            if (loading.isShowing()) loading.dismiss();
        }
        goToMenu();
       // alertDialog(this, "Warning!", message, getString(R.string.accept));
       // binding.btnLogin.setEnabled(true);
    }

    @Override
    public void success(RequestResponse<UserDto> result) {
        if (result == null) {
            alertDialog(this, "Attention", "Invalid result information.", "Accept");
        }
        else if (result.success) {
            goToMenu();
        }
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void authOnline() {
        message = "Authenticating...";
        if (TextUtils.isEmpty(Connectivity.isConnected(this))) {
            alertDialog(this,"Warning","Please check your internet connection and try again","Retry");
        }
        else
            if (binding.getAccount() == null) {
                goToMenu();
           // alertDialog(this, "Warning", "Account is null", "Accept");
        }
        else if (TextUtils.isEmpty(binding.loginInputUsername.getText())) {
            alertDialog(this, "Attention", "Email address incomplete.", "Accept");
            binding.loginInputUsername.requestFocus();
        }
        else if (TextUtils.isEmpty(binding.loginInputPassword.getText())) {
            alertDialog(this, "Attention", "Password incomplete.", "Accept");
            binding.loginInputPassword.requestFocus();
        }
        else presenter.authenticate(binding.getAccount());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    public void permissions()
    {
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[0]))
            {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this);
                builder.setCancelable(false);
                builder.setTitle(R.string.title_permisos);
                builder.setMessage(R.string.mensaje_permisos);
                builder.setPositiveButton(R.string.action_conceder, (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                });
                builder.setNegativeButton(R.string.action_cancelar, (dialog, which) -> dialog.cancel());
                builder.show();
            } else
            {
                ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }
        }
    }

}