package com.example.jeong.myapplication.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.jeong.myapplication.MainActivity;
import com.example.jeong.myapplication.R;
import com.example.jeong.myapplication.common.MyMarkerView;
import com.example.jeong.myapplication.common.ServiceGenerator;
import com.example.jeong.myapplication.common.adapter.GameListViewAdapter;
import com.example.jeong.myapplication.model.Game;
import com.example.jeong.myapplication.model.Report;
import com.example.jeong.myapplication.service.GameService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    private static final String TAG = "GameFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Calendar mCalendar;
    private Calendar calendar;

    private ListView listViewGame;
    private TextView textViewYear;
    private TextView textViewMonth;
    private TextView textViewDay;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mCalendar Parameter 1.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(Calendar mCalendar) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, mCalendar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCalendar = (Calendar) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        textViewYear = (TextView) rootView.findViewById(R.id.textYear);
        textViewMonth = (TextView) rootView.findViewById(R.id.textMonth);
        textViewDay = (TextView) rootView.findViewById(R.id.textDay);
        listViewGame = (ListView) rootView.findViewById(R.id.listViewGame);


        listViewGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Game item = (Game) adapterView.getItemAtPosition(i) ;

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setGameId(item.getGameId());
                mainActivity.movePage(1);
            }
        });
        Button buttonDateUp = (Button) rootView.findViewById(R.id.buttonDayUp);
        Button buttonDateDown = (Button) rootView.findViewById(R.id.buttonDayDown);

        buttonDateUp.setOnClickListener(new OnDateUpClickListener());
        buttonDateDown.setOnClickListener(new OnDateDownClickListener());

        // 초기데이터 셋팅
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        if (mCalendar != null) {
            calendar = mCalendar;
        }

        setDate();
        callApi();

        return rootView;
    }

    private void callApi() {
        GameService gameService = ServiceGenerator.createService(GameService.class);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String searchDate = format.format(new Date(calendar.getTimeInMillis()));

        Call<List<Game>> call = gameService.listGame(searchDate);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {

                GameListViewAdapter gameListViewAdapter = new GameListViewAdapter();
                listViewGame.setAdapter(gameListViewAdapter);

                List<Game> gameList = response.body();

                for (Game game : gameList) {
                    gameListViewAdapter.addItem(game);
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {

            }
        });
    }

    private void upDownDate(int v) {
        calendar.add(Calendar.DATE, v);
        setDate();
    }

//    private Calendar getDate() {
//        int year = Integer.parseInt(textViewYear.getText().toString());
//        int month = Integer.parseInt(textViewMonth.getText().toString());
//        int day = Integer.parseInt(textViewDay.getText().toString());
//
//        calendar.set(year, month - 1, day);
//
//        return calendar;
//    }

    private void setDate() {
        String sYear = String.valueOf(calendar.get(Calendar.YEAR));
        String sMonth = String.format("%02d", calendar.get(Calendar.MONTH)+1);
        String sDay = String.format("%02d", calendar.get(Calendar.DATE));

        textViewYear.setText(sYear);
        textViewMonth.setText(sMonth);
        textViewDay.setText(sDay);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setCalendar(calendar);
    }

    class OnDateUpClickListener extends OnDateClickListener {
        @Override
        public void onClick(View view) {
            upDownDate(+1);
            super.onClick(view);
        }
    }

    class OnDateDownClickListener extends OnDateClickListener {
        @Override
        public void onClick(View view) {
            upDownDate(-1);
            super.onClick(view);
        }
    }

    class OnDateClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            callApi();
        }
    }

}
