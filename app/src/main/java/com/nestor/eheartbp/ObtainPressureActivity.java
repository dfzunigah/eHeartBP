package com.nestor.eheartbp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Iterator;

import static com.nestor.eheartbp.Globals.dia;
import static com.nestor.eheartbp.Globals.pul;
import static com.nestor.eheartbp.Globals.sys;
import static com.nestor.eheartbp.Globals.time_stamp;
import static com.nestor.eheartbp.Globals.pulso;
import static com.nestor.eheartbp.Globals.diastolica;
import static com.nestor.eheartbp.Globals.sistolica;
import static com.nestor.eheartbp.Globals.tiempo_toma;


public class ObtainPressureActivity extends AppCompatActivity {
    Button ob;
    // public TextView textView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtain_pressure);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        ob = findViewById(R.id.obtainButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.e_heart_menu, menu);
        return true;
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuActions.options(this, item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    public void obtain(View view) {
        ob.setBackgroundColor(Color.GRAY);
        ob.setText("Cargando...");

        DatabaseReference datos = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mensajeref = datos.child("Datos/Usuario_1/Pulso");

        ////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////

        for (Iterator<String> iterator = pulso.iterator(); iterator.hasNext();) {

            String dato = iterator.next();
            iterator.remove();;
        }
        for (Iterator<String> iterator = diastolica.iterator(); iterator.hasNext();) {

            String dato = iterator.next();
            iterator.remove();;
        }
        for (Iterator<String> iterator = sistolica.iterator(); iterator.hasNext();) {

            String dato = iterator.next();
            iterator.remove();;
        }
        for (Iterator<String> iterator = tiempo_toma.iterator(); iterator.hasNext();) {

            String dato = iterator.next();
            iterator.remove();;
        }


        /////////////////////////////////////////////////
        //////////////////////////////////////////7

        mensajeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ArrayList<String> pulso = new ArrayList<String>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    pulso.add(dataSnapshot1.getValue().toString());

                }
                pul = Integer.parseInt(pulso.get(pulso.size() - 1));


                startActivity(new Intent(ObtainPressureActivity.this, ViewMeasurementActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException();
                showToast("Error al obtener los datos");
            }
        });
///////////////////////////////////
        /////////////////////////////
        DatabaseReference mensajeref2 = datos.child("Datos/Usuario_1/Diastolica");

        mensajeref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ArrayList<String> diastolica = new ArrayList<String>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    diastolica.add(dataSnapshot1.getValue().toString());
                }
                dia = Integer.parseInt(diastolica.get(diastolica.size() - 1));
                startActivity(new Intent(ObtainPressureActivity.this, ViewMeasurementActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException();
                showToast("Error al obtener los datos");
            }
        });

/////////////////////////////////////////
/////////////////////////////////////
        DatabaseReference mensajeref3 = datos.child("Datos/Usuario_1/Sistolica");

        mensajeref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //  ArrayList<String> sistolica = new ArrayList<String>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    sistolica.add(dataSnapshot1.getValue().toString());
                }
                sys = Integer.parseInt(sistolica.get(sistolica.size() - 1));
                startActivity(new Intent(ObtainPressureActivity.this, ViewMeasurementActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException();
                showToast("Error al obtener los datos");
            }
        });
///////////////////////////////////
        /////////////////////////////

        DatabaseReference mensajeref4 = datos.child("Datos/Usuario_1/Tiempo");

        mensajeref4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ArrayList<String> tiempo_toma = new ArrayList<String>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    tiempo_toma.add(dataSnapshot1.getValue().toString());
                }
                time_stamp = Long.parseLong(tiempo_toma.get(tiempo_toma.size() - 1));
                //  startActivity(new Intent(ObtainPressureActivity.this, ViewMeasurementActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException();
                showToast("Error al obtener los datos");
            }
        });

/////////////////////////////////////////
/////////////////////////////////////
        //  startActivity(new Intent(ObtainPressureActivity.this, ViewMeasurementActivity.class));

    }
}
