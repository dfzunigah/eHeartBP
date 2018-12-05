package com.nestor.eheartbp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class GoogleAccountsActivity extends Activity {
    Button b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        getWindow().setLayout((int) (w * .7), (int) (h * .7));

        b=findViewById(R.id.accountButton);
    }

    public void login2(View view) {
        b.setBackgroundColor(Color.argb(30,0,0,0));

        new Thread() {
            public void run() {

                startActivity(new Intent(GoogleAccountsActivity.this, ObtainPressureActivity.class));
            }
        }.start();
    }

}
