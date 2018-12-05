package com.nestor.eheartbp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nestor.eheartbp.objetos.FirebaseReferences;
import com.nestor.eheartbp.objetos.Usuario;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.nestor.eheartbp.Globals.dia;
import static com.nestor.eheartbp.Globals.pul;
import static com.nestor.eheartbp.Globals.sys;

public class ViewMeasurementActivity extends AppCompatActivity {

    View.OnFocusChangeListener listener;

    public TextView editSys;
    public TextView editPul;
    public TextView editDia;
    private TextView fecha;
    private TextView time;
    private TextView time_stamp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_measurement);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //
        editPul =  findViewById(R.id.editPul);
        editSys =  findViewById(R.id.editSys);
        editDia =  findViewById(R.id.editDia);

        fecha =  findViewById(R.id.textView4);
        time =  findViewById(R.id.textView5);
        time_stamp =  findViewById(R.id.textView7);
        //
        editPul.setText(pul.toString());
        editDia.setText(dia.toString());
        editSys.setText(sys.toString());


        Calendar date = Calendar.getInstance();

        date.setTimeInMillis(Globals.time_stamp);

        date.add(Calendar.MONTH, 1);
        time_stamp.setText(date.get(Calendar.DAY_OF_MONTH)+
                "/"+date.get(Calendar.MONTH)+"/"+date.get(Calendar.YEAR)+"  "
                +convertDate(date.get(Calendar.HOUR))+":"+convertDate(date.get(Calendar.MINUTE)));
        //out.println(mydate.get(Calendar.DAY_OF_MONTH)+"."+mydate.get(Calendar.MONTH)+"."+mydate.get(Calendar.YEAR));
        //
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int dia = today.monthDay;
        int mes = today.month;
        int ano = today.year;
        int hora = today.hour;
        int min = today.minute;

        mes = mes + 1;
        fecha.setText(mes+" / "+dia+" / "+ano);
        time.setText(convertDate(hora)+" : "+convertDate(min) + "  Ultima visualización");


    }
    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
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
