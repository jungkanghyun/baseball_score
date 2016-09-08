package com.example.jeong.myapplication.service;

import com.example.jeong.myapplication.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jeong on 2016-08-22.
 */
public interface GameService {

    @GET("/game/date/{date}")
    Call<List<Game>> listGame(@Path("date") String date);

}
