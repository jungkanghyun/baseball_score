package com.example.jeong.myapplication.fragment;


import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeong.myapplication.MainActivity;
import com.example.jeong.myapplication.R;
import com.example.jeong.myapplication.common.MyMarkerView;
import com.example.jeong.myapplication.common.ServiceGenerator;
import com.example.jeong.myapplication.model.Game;
import com.example.jeong.myapplication.model.Report;
import com.example.jeong.myapplication.service.GameService;
import com.example.jeong.myapplication.service.ReportService;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    private static final String TAG = "ReportFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Long gameId;

    private HorizontalBarChart chart;
    private ImageView imageAwayTemaLogo;
    private ImageView imageHomeTeamLogo;


    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gameId Parameter 1.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(Long gameId) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, gameId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gameId = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        Button buttonHome = (Button) rootView.findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.movePage(0);
            }
        });
        chart = (HorizontalBarChart) rootView.findViewById(R.id.chart);
        initBarChart(chart);
        imageAwayTemaLogo = (ImageView) rootView.findViewById(R.id.imageAwayTeam);
        imageHomeTeamLogo = (ImageView) rootView.findViewById(R.id.imageHomeTeam);

        ReportService reportService = ServiceGenerator.createService(ReportService.class);

        Call<List<Report>> call = reportService.listReport(gameId);
        call.enqueue(new Callback<List<Report>>() {

            private void setColor(List<Integer> colors, int a, int b) {

                int green = Color.rgb(110, 190, 102);
                int red = Color.rgb(211, 74, 88);

                if (a > b) {
                    colors.add(red);
                    colors.add(green);
                }
                else if (a == b) {
                    colors.add(green);
                    colors.add(red);
                }
                else {
                    colors.add(red);
                    colors.add(red);
                }
            }

            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {


                ArrayList<BarEntry> data = new ArrayList<>();
                List<Integer> colors = new ArrayList<Integer>();

                List<Report> reportList = response.body();

                if (reportList.size() > 0) {
                    Report awayReport = reportList.get(0);
                    Report homeReport = reportList.get(1);

                    int awayLogoResId = getResources().getIdentifier("@mipmap/logo_team_" + awayReport.getCpTeamId().toLowerCase(), "mipmap", "com.example.jeong.myapplication");
                    int homeLogoResId = getResources().getIdentifier("@mipmap/logo_team_" + homeReport.getCpTeamId().toLowerCase(), "mipmap", "com.example.jeong.myapplication");

                    imageAwayTemaLogo.setImageResource(awayLogoResId);
                    imageHomeTeamLogo.setImageResource(homeLogoResId);

                    data.add(new BarEntry(5, new float[]{awayReport.getBbhp() * -1, homeReport.getBbhp()}));
                    setColor(colors, awayReport.getBbhp(), homeReport.getBbhp());
                    data.add(new BarEntry(10, new float[]{awayReport.getErr() * -1, homeReport.getErr()}));
                    setColor(colors, awayReport.getErr(), homeReport.getErr());
                    data.add(new BarEntry(15, new float[]{awayReport.getHit() * -1, homeReport.getHit()}));
                    setColor(colors, awayReport.getHit(), homeReport.getHit());
                    data.add(new BarEntry(20, new float[]{awayReport.getHr() * -1, homeReport.getHr()}));
                    setColor(colors, awayReport.getHr(), homeReport.getHr());
                    data.add(new BarEntry(25, new float[]{awayReport.getGdp() * -1, homeReport.getGdp()}));
                    setColor(colors, awayReport.getGdp(), homeReport.getGdp());
                    data.add(new BarEntry(30, new float[]{awayReport.getLob() * -1, homeReport.getLob()}));
                    setColor(colors, awayReport.getLob(), homeReport.getLob());
                    data.add(new BarEntry(35, new float[]{awayReport.getSb() * -1, homeReport.getSb()}));
                    setColor(colors, awayReport.getSb(), homeReport.getSb());
                    data.add(new BarEntry(40, new float[]{awayReport.getSo() * -1, homeReport.getSo()}));
                    setColor(colors, awayReport.getSo(), homeReport.getSo());

                    BarDataSet barDataSet = new BarDataSet(data, "# of Ex-Rates");

                    //                        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    barDataSet.setDrawValues(true);
                    //                        barDataSet.setStackLabels(new String[]{"yes", "no"});
                    barDataSet.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                            return String.valueOf(Math.abs(value));
                        }
                    });
                    barDataSet.setColors(colors);
                    barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);


                    BarData barData = new BarData(barDataSet);

                    barData.setBarWidth(4f);

                    chart.setData(barData); // set the data and list of lables into chart
                    chart.animateXY(2000, 2000); //애니메이션 기능 활성화
                    chart.invalidate();
                }
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {

            }
        });


        return rootView;
    }

    public void initBarChart(BarChart chart) {
        MarkerView mv = new MyMarkerView(this.getContext(), R.layout.custom_marker_view);
        chart.setMarkerView(mv);
        chart.setDrawMarkerViews(true);

        chart.setDrawGridBackground(false);
        chart.setDescription("");

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setHighlightFullBarEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setAxisMaxValue(25f);
        chart.getAxisRight().setAxisMinValue(-25f);
        chart.getAxisRight().setMaxWidth(25f);
        chart.getAxisRight().setMinWidth(-25f);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawZeroLine(true);
        chart.getAxisRight().setLabelCount(11, false);
        chart.getAxisRight().setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";//String.valueOf(Math.abs(value));
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        chart.getAxisRight().setTextSize(9f);
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);
        xAxis.setAxisMaxValue(45f);
        xAxis.setAxisMinValue(0f);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(10, true);
        xAxis.setGranularity(5f);
        xAxis.setValueFormatter(new AxisValueFormatter() {
            private DecimalFormat format = new DecimalFormat("###");

            private String[] label = new String[]{"", "BBHP", "ERR", "HIT", "HR", "GDP", "LOB", "SB", "SO"};
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int v = (int) (value / 5f);
                if (label.length <= v) return "";
                return label[Math.abs(v)];
//                return format.format(value) + "-" + format.format(value + 10);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
    }
}
