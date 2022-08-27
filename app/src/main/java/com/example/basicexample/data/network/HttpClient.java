package com.example.basicexample.data.network;

import android.annotation.SuppressLint;

import com.example.basicexample.BuildConfig;
import com.example.basicexample.utilities.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private int connectTimeout = 120;
    private int writeTimeout = 60;
    private int readTimeout = 60;

    @SuppressWarnings("ConstantConditions")
    public Retrofit getConnection() throws Exception {
        OkHttpClient okHttpClient;
        if (!BuildConfig.BUILD_TYPE.equals("release")) {
            @SuppressLint("TrustAllX509TrustManager")
            TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
                }

                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
                }
            }};

            @SuppressLint("TrustAllX509TrustManager")
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
                }

                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            SecureRandom secureRandom = new SecureRandom();
            sslContext.init(null, trustManagers, secureRandom);
            SSLSocketFactory delegate = sslContext.getSocketFactory();
            HostnameVerifier hostnameVerifier = null;
            if (BuildConfig.BUILD_TYPE.equals("debug")) {
                hostnameVerifier = (hostname, session) -> hostname.equals(Constants.hostDevelopment);
            }
            else
            if (BuildConfig.BUILD_TYPE.equals("qatesting")) {
                hostnameVerifier = (hostname, session) -> hostname.equals(Constants.hostTesting);
            }
            else
            if (BuildConfig.BUILD_TYPE.equals("staging")) {
                hostnameVerifier = (hostname, session) -> hostname.equals(Constants.hostStaging);
            }

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                    .sslSocketFactory(delegate, x509TrustManager)
                    .hostnameVerifier(hostnameVerifier)
                    .build();
        }
        else {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                    .build();
        }

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

            return new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.getBaseUrl())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
//        return new Retrofit.Builder()
//                .baseUrl(Constants.getBaseUrl())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(okHttpClient)
//                .build();
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
