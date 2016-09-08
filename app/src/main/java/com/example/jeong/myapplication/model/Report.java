package com.example.jeong.myapplication.model;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Jeong on 2016-08-24.
 */

@Data
@ToString
public class Report {
    private Long id;
    private Long gameId;
    private String cpGameId;
    private String btop;
    private Long teamId;
    private String cpTeamId;
    private String teamName;
    private String teamFullName;
    private int hit;
    private int hr;
    private int sb;
    private int err;
    private int gdp;
    private int so;
    private double avg;
    private int lob;
    private int bbhp;
}
