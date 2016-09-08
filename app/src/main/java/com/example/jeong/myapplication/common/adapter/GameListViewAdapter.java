package com.example.jeong.myapplication.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeong.myapplication.R;
import com.example.jeong.myapplication.model.Game;
import com.example.jeong.myapplication.model.Report;

import java.util.ArrayList;

/**
 * Created by Jeong on 2016-08-25.
 */
public class GameListViewAdapter extends BaseAdapter {

    private ArrayList<Game> gameArrayList = new ArrayList<>();

    public GameListViewAdapter () {

    }

    @Override
    public int getCount() {
        return gameArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return gameArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int pos = i;
        final Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_game, viewGroup, false);
        }

        ImageView imageAwayTemaLogo = (ImageView) view.findViewById(R.id.imageAwayTeam);
        ImageView imageHomeTeamLogo = (ImageView) view.findViewById(R.id.imageHomeTeam);
        TextView textAwayScore = (TextView) view.findViewById(R.id.textAwayScore);
        TextView textHomeScore = (TextView) view.findViewById(R.id.textHomeScore);
        TextView textBallparkName = (TextView) view.findViewById(R.id.textBallparkName);
        TextView textGameTime =(TextView) view.findViewById(R.id.textGameTime);

        Game game = gameArrayList.get(i);

        textAwayScore.setText(String.format("%02d", game.getAwayScore()));
        textHomeScore.setText(String.format("%02d", game.getHomeScore()));

        textBallparkName.setText(game.getBallparkFullName());
        textGameTime.setText(game.getGameTime());

        int awayLogoResId = context.getResources().getIdentifier("@mipmap/logo_team_" + game.getCpAwayTeamId().toLowerCase(), "mipmap", context.getPackageName());
        int homeLogoResId = context.getResources().getIdentifier("@mipmap/logo_team_" + game.getCpHomeTeamId().toLowerCase(), "mipmap", context.getPackageName());

        imageAwayTemaLogo.setImageResource(awayLogoResId);
        imageHomeTeamLogo.setImageResource(homeLogoResId);

        return view;
    }

    public void addItem(Game game) {
        gameArrayList.add(game);
    }
}
