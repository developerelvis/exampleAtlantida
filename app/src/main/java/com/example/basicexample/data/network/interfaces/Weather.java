package com.example.basicexample.data.network.interfaces;

import com.example.basicexample.data.dto.WeatherDto;
import com.example.basicexample.data.network.RequestResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Weather {
    @GET("https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={key}")
    Call<RequestResponse<List<WeatherDto>>> getWeather(@Path("lat") int lat, @Path("lon") int lon, @Path("lon") int key);

}
