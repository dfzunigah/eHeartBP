package com.nestor.eheartbp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eralp.circleprogressview.CircleProgressView;
import com.eralp.circleprogressview.ProgressAnimationListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static com.nestor.eheartbp.Globals.diastolica;
import static com.nestor.eheartbp.Globals.sistolica;
import static com.nestor.eheartbp.Globals.pulso;

public class RiskPrediction extends AppCompatActivity {
    private CircleProgressView mCircleProgressView;

    private  RequestQueue requstQueue;
    private Button reloadButton;
    private Button badPatientButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_prediction);
        requstQueue = Volley.newRequestQueue(this);

        reloadButton = (Button) findViewById(R.id.button_logged_patient);
        badPatientButton = (Button) findViewById(R.id.button_bad_patient);

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleProgressView.setProgressWithAnimation(0, 100);
                update_logged_patient_risk();
            }
        });
        badPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleProgressView.setProgressWithAnimation(0, 100);
                display_unhealthy_patient_risk();
            }
        });
        mCircleProgressView = (CircleProgressView) findViewById(R.id.circle_progress_view);
        mCircleProgressView.setTextEnabled(true);
        mCircleProgressView.setInterpolator(new AccelerateDecelerateInterpolator());
        mCircleProgressView.setStartAngle(270);
        mCircleProgressView.getCircleColor();
        //mCircleProgressView.setProgressWithAnimation(5, 2000);
        mCircleProgressView.addAnimationListener(new ProgressAnimationListener() {
            @Override
            public void onValueChanged(float value) {

            }

            @Override
            public void onAnimationEnd() {
                Toast.makeText(RiskPrediction.this, "Based on our trained AI model and your BP record", Toast.LENGTH_LONG).show();
            }
        });
        update_logged_patient_risk();

    }
    public void update_logged_patient_risk(){

        JSONArray records = new JSONArray();
        JSONArray codes = new JSONArray();
        for (int j = 0; j< pulso.size(); j++) {
            JSONArray bp_pacient = new JSONArray();
            try {
                bp_pacient.put(Double.parseDouble(diastolica.get(j)));
                bp_pacient.put(Double.parseDouble(sistolica.get(j)));
                bp_pacient.put(Double.parseDouble(pulso.get(j)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            records.put(bp_pacient);
            codes.put(new JSONArray());
        }
        System.out.println("records: "+ records);
        System.out.println("codes: "+ codes);


        JSONObject body = new JSONObject();
        try {
            body =new JSONObject("{ \"signature_name\":\"predict_disease\", \"inputs\":{\"numeric_input\": ["+records.toString()+"], \"codes_input\":["+codes.toString()+"] } }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://35.202.149.195:80/v1/models/eHeart:predict";

        postData(url, body, new VolleyCallback() {
            @Override
            public void notifySuccess(JSONObject result) throws JSONException {
                System.out.println(result.toString());
                double out = result.getJSONArray("outputs").getJSONArray(0).getJSONArray(0).getDouble(0);
                mCircleProgressView.setProgressWithAnimation((float) (out*100), 2000);
                if(out > 0.5){
                    mCircleProgressView.setCircleColor(Color.RED);
                }else {
                    mCircleProgressView.setCircleColor(-13924746);
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                System.out.println("ERROR: " +error.toString());
            }
        });
    }

    public void display_unhealthy_patient_risk(){
        JSONObject body = new JSONObject();
        try {
            body =new JSONObject("{ \"signature_name\":\"predict_disease\", \"inputs\":{\"numeric_input\": [[[102.46265960976592, 161.84189726360853, 115.39472246221708], [106.2801928167619, 167.74015650997114, 111.20465593331818], [95.29400881447118, 170.94809228821745, 100.72956559377283], [99.16243317265884, 168.09953449994546, 107.04374832240916], [102.76409386803302, 169.57890467816887, 101.62492754919089], [98.91324652640172, 171.56279669060177, 103.69519025217396], [98.16321258143104, 162.80701742550076, 105.93718436218849], [92.28354669852581, 179.31458663745056, 103.57760785526487], [107.43176399679531, 164.16805439393883, 118.48160719314988]]], \"codes_input\":[[[],[],[],[],[],[],[],[],[]]] } }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://35.202.149.195:80/v1/models/eHeart:predict";

        postData(url, body, new VolleyCallback() {
            @Override
            public void notifySuccess(JSONObject result) throws JSONException {
                System.out.println(result.toString());
                double out = result.getJSONArray("outputs").getJSONArray(0).getJSONArray(0).getDouble(0);
                mCircleProgressView.setProgressWithAnimation((float) (out*100), 2000);
                if(out > 0.5){
                    mCircleProgressView.setCircleColor(Color.RED);
                }else {
                    mCircleProgressView.setCircleColor(-13924746);
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                System.out.println("ERROR: " +error.toString());
            }
        });
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

    public void postData(String url, JSONObject data, final VolleyCallback mResultCallback){

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(mResultCallback != null){
                            try {
                                mResultCallback.notifySuccess(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mResultCallback != null){
                            mResultCallback.notifyError(error);
                        }
                    }
                }
        ){
            //here I want to post data to sever
        };
        requstQueue.add(jsonobj);

    }
    public interface VolleyCallback {
        void notifySuccess(JSONObject result) throws JSONException;
        void notifyError (VolleyError error);
    }
}
