package com.nestor.eheartbp;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public class MenuActions {

    public static boolean options(Activity act, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.get_pressure:
                act.startActivity(new Intent(act, ObtainPressureActivity.class));
                return true;
            case R.id.record:
                act.startActivity(new Intent(act, RecordsActivity.class));
                return true;
            case R.id.statistics:
                act.startActivity(new Intent(act, StatisticsActivity.class));
                return true;
            case R.id.risk:
                act.startActivity(new Intent(act, RiskPrediction.class));
                return true;
            case R.id.close_session:
                act.startActivity(new Intent(act, SignInActivity.class));
                return true;
            default:

                return act.getParent().onOptionsItemSelected(item);
        }
    }
}
