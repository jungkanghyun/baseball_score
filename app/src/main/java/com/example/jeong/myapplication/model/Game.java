package com.example.jeong.myapplication.model;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Jeong on 2016-08-22.
 */

@Data
@ToString
public class Game {

    private Long gameId;
    private int awayScore;
    private String awaySunbalFirstKname;
    private String awaySunbalFullKname;
    private Long awaySunbalId;
    private String awaySunbalLastKname;
    private String awayTeamFullName;
    private Long awayTeamId;
    private String awayTeamName;
    private String ballparkFullName;
    private Long ballparkId;
    private String ballparkName;
    private String broadId;
    private String broadName;
    private String categoryCode;
    private String confirmId;
    private String cpAwayTeamId;
    private String cpGameId;
    private String cpHomeTeamId;
    private String crud;
    private String dheader;
    private String gameDate;
    private String gameDay;
    private String gameMonth;
    private String gameTime;
    private String gameWeek;
    private String gameWeekday;
    private String gameWeekDate;
    private String grandCategoryCode;
    private int homeScore;
    private String homeSunbalFirstKname;
    private String homeSunbalFullKname;
    private Long homeSunbalId;
    private String homeSunbalLastKname;
    private String homeTeamFullName;
    private Long homeTeamId;
    private String homeTeamName;
    private String seasonId;
    private Long seriesId;
    private String seriesName;
    private String status;
    private String stReason;
    private String weather;
}
