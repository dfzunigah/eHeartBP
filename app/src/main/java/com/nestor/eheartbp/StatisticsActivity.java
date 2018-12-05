package com.nestor.eheartbp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import static com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE;
import static com.github.mikephil.charting.components.Legend.LegendPosition.BELOW_CHART_CENTER;
import static com.github.mikephil.charting.components.Legend.LegendPosition.PIECHART_CENTER;
import static com.nestor.eheartbp.Globals.diastolica;
import static com.nestor.eheartbp.Globals.sistolica;
import static com.nestor.eheartbp.Globals.tiempo_toma;
import static com.nestor.eheartbp.Globals.time_stamp;
import static com.nestor.eheartbp.Globals.pulso;

import java.util.ArrayList;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {

    LineChart lineChart;
    private TextView time_stamp;

    boolean showDay;
    ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //showDay = true;
        //i = findViewById(R.id.statisticsView);

        lineChart = findViewById(R.id.lineChart);

        final ArrayList<String> xAxes = new ArrayList<>();
        ArrayList<Entry> yAxesPulse = new ArrayList<>();
        ArrayList<Entry> yAxesDyastolic = new ArrayList<>();
        ArrayList<Entry> yAxesSystolic = new ArrayList<>();

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Globals.time_stamp);

        Long aux;
        String aux2;
        for (int j = 0; j< pulso.size(); j++){

            aux = Long.parseLong(tiempo_toma.get(j));
            date.setTimeInMillis(aux);
            aux2 = date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH);
            xAxes.add(aux2);

            yAxesPulse.add(new Entry(j,Integer.parseInt(pulso.get(j))));
            yAxesDyastolic.add(new Entry(j,Integer.parseInt(diastolica.get(j))));
            yAxesSystolic.add(new Entry(j,Integer.parseInt(sistolica.get(j))));

        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAxesDyastolic, "Diastólica");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.GREEN);
        lineDataSet1.setFormLineWidth(14);

        lineDataSet1.setFormSize(20);

        LineDataSet lineDataSet2 = new LineDataSet(yAxesPulse, "Pulso");
        lineDataSet2.setFormLineWidth(14);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setFormSize(20);

        LineDataSet lineDataSet3 = new LineDataSet(yAxesSystolic, "Sistólica");
        lineDataSet3.setFormLineWidth(14);

        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setColor(Color.BLUE);
        lineDataSet3.setFormSize(20);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);
        lineDataSets.add(lineDataSet3);


        lineChart.setData(new LineData(lineDataSets));
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setAxisLineWidth(5);
        lineChart.getAxisLeft().setAxisLineColor(Color.BLACK);
        lineChart.getXAxis().setAxisLineWidth(5);
        lineChart.getXAxis().setAxisLineColor(Color.BLACK);
        lineChart.getXAxis().setAxisMinimum(-0.2f);
        lineChart.getDescription().setText("");
        lineChart.getLegend().setPosition(BELOW_CHART_CENTER);
        lineChart.getLegend().setXEntrySpace(30f);
        lineChart.getLegend().setForm(CIRCLE);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxes.get((int)value);
            }
        });

        lineChart.setExtraOffsets(0, 0,0,20);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.e_heart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuActions.options(this, item);
    }

}
