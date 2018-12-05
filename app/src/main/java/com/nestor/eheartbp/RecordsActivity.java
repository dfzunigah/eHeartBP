package com.nestor.eheartbp;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

import static com.nestor.eheartbp.Globals.diastolica;
import static com.nestor.eheartbp.Globals.pul;
import static com.nestor.eheartbp.Globals.sistolica;
import static com.nestor.eheartbp.Globals.tiempo_toma;
import static com.nestor.eheartbp.Globals.pulso;
import static com.nestor.eheartbp.Globals.time_stamp;

public class RecordsActivity extends AppCompatActivity {

    LinearLayout grafo;
    TableLayout tabla;
    TableRow row;
    Calendar date = Calendar.getInstance();
    Long fecha;
    String time1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records2);
        grafo =  findViewById(R.id.chart_layout);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

         tabla = new TableLayout(this);
         row=new TableRow(this);


        displayTable();
    }

    public  void displayTable(){
        tabla.setLayoutParams( new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        for (int count=0;count<pulso.size();count++){
            TableRow row = new TableRow(this);
            row.setLayoutParams((new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT)));

            fecha = Long.parseLong(tiempo_toma.get(count));
            date.setTimeInMillis(fecha);
            date.add(Calendar.MONTH, 1);
            time1 =((date.get(Calendar.DAY_OF_MONTH))+"/"+date.get(Calendar.MONTH));

            TextView value = new TextView(this);
            value.setText("           "+time1+"                    "+sistolica.get(count)+
                    "                     "+diastolica.get(count)+"                  "
                    + pulso.get(count));
            //value.setText(""+count);
            value.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            row.addView(value);
            tabla.addView(row);
        }
        /*
        for (int count=0;count<3;count++){
           // TableRow row = new TableRow(this);
            row.setLayoutParams((new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT)));

            TextView value = new TextView(this);
            value.setText("Text1 "+ count);
            //value.setTextColor(Color BA);
            value.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            row.addView(value);
            tabla.addView(row);
        }
        */

        grafo.addView(tabla);
    }
/*
    public  void displayTable2(){
        TextView text1=new TextView(this);
        TextView text2=new TextView(this);
        TextView text3=new TextView(this);

        text1.setText("1");
        row.addView(text1);
        text2.setText("2");
        row.addView(text2);
        text3.setText("3");
        row.addView(text3);
        tabla.addView(row);
        for (int i=0;i<10;i++)
        {
            row=new TableRow(this);
            text1=new TextView(this);
            text2=new TextView(this);
            text3=new TextView(this);


            text1.setText("a");
            row.addView(text1);
            text2.setText("b");
            row.addView(text2);
            text3.setText("c");
            row.addView(text3);
            tabla.addView(row);
        }

        grafo.addView(tabla);
    }
*/
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
