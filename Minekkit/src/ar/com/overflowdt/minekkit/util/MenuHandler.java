package ar.com.overflowdt.minekkit.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.acercade.AcercaDe;
import ar.com.overflowdt.minekkit.login.LoginActivity;

/**
 * Created by Juan on 23/02/14.
 */
public class MenuHandler {

    public boolean bindearLogica (MenuItem item, Activity activity) {
        switch (item.getItemId()) {
            case R.id.menu_acercaDe:
                Intent nuevaActivity = new Intent(activity, AcercaDe.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_salir:
                activity.finish();
                break;
            case R.id.menu_logout:

                SharedPreferences preferencias=activity.getSharedPreferences("logindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("user", "");
                editor.putString("pass", "");
                editor.commit();
                Intent i = new Intent(activity.getApplicationContext(), LoginActivity.class);
                activity.startActivity(i);
                activity.finish();
                break;
        }
        return true;

    }


}
