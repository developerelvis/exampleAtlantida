package com.example.basicexample.ui.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.content.ContextCompat;

import com.example.basicexample.R;

public class SplashScreen extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		getWindow().setStatusBarColor(ContextCompat.getColor(getApplication(), R.color.white));

		int SPLASH_TIME_OUT = 300;
		new Handler().postDelayed(() ->
		{
			Intent i = new Intent(this, LoginActivity.class);

			startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
	}
}
