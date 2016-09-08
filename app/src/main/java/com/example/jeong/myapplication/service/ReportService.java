package com.example.jeong.myapplication.service;

import com.example.jeong.myapplication.model.Game;
import com.example.jeong.myapplication.model.Report;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jeong on 2016-08-24.
 */
public interface ReportService {

    @GET("/report/game/{id}")
    Call<List<Report>> listReport(@Path("id") Long gameId);

}
