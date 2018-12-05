package com.nestor.eheartbp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.ProxyFileDescriptorCallback;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String MY_PREFS_NAME = "MyPrefsFile";


    Button botonregistro, button_login;
    EditText login_pass, login_mail;
    FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progresdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        button_login = findViewById(R.id.button_login);
        botonregistro = findViewById(R.id.botonregistro);
        login_pass = findViewById(R.id.login_pass);
        login_mail = findViewById(R.id.login_mail);
        progresdialog = new ProgressDialog(this);

        botonregistro.setOnClickListener(this);
        button_login.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.i("SESION", "Sesión iniciada con email: " + user.getEmail());
                    // acceso();
                } else {
                    Log.i("SESION", "Sesión cerrada");
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();


        List<String> emailList = new ArrayList<String>();
        for(Map.Entry<String, ?> entry : allEntries.entrySet()) {
            emailList.add(entry.getKey());
        }

        String [] savedEmails = new String[emailList.size()];

        savedEmails = emailList.toArray(savedEmails);

        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, savedEmails);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.login_mail);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

    }

    private void registrar(String email, String pass) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("SESION", "Usuario creado correctamente");
                    showToast("Usuario creado correctamente");

                } else {
                    Log.e("SESION", task.getException().getMessage() + "");
                    showToast("No se creo el usuario, verifique datos");
                }
                progresdialog.dismiss();
            }
        });
    }

    private void iniciar_sesion(String email, String pass) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("SESION", "Sesión iniciada");
                    acceso();
                } else {
                    String errorString = task.getException().getMessage();
                    Log.e("SESION", errorString + "");
                    if (errorString.equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred.")){
                        showToast("No hay conexión.");
                    }else{
                        showToast("El usuario no está registrado");
                    }
                }
                progresdialog.dismiss();
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void acceso() {
        startActivity(new Intent(this, ObtainPressureActivity.class));
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:

                String emailInicio = login_mail.getText().toString();
                String passInicio = login_pass.getText().toString();

                if (TextUtils.isEmpty(passInicio)) {
                    showToast("Se debe ingresar un email");
                    return;
                }
                if (TextUtils.isEmpty(emailInicio)) {
                    showToast("Se debe ingresar un contraseña");
                    return;
                }
                progresdialog.setMessage("Iniciando sesión...");
                progresdialog.show();
                iniciar_sesion(emailInicio, passInicio);
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString(emailInicio,"email");
                editor.apply();
                break;
            case R.id.botonregistro:
                String emailReg = login_mail.getText().toString();
                String passReg = login_pass.getText().toString();

                if (TextUtils.isEmpty(emailReg)) {
                    showToast("Se debe ingresar un email");
                    return;
                }
                if (TextUtils.isEmpty(passReg)) {
                    showToast("Se debe ingresar un contraseña");
                    return;
                }
                progresdialog.setMessage("Realizando registro...");
                progresdialog.show();
                registrar(emailReg, passReg);
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        } else {
            showToast("Error al leer los campos");
        }
    }
}
