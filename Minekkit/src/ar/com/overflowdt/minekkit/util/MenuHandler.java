package ar.com.overflowdt.minekkit.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.acercade.AcercaDe;
import ar.com.overflowdt.minekkit.configuracion.ConfigActivity;
import ar.com.overflowdt.minekkit.login.LoginActivity;
import ar.com.overflowdt.minekkit.onlineList.OnlineListActivity;
import ar.com.overflowdt.minekkit.pms.AllPmsActivity;
import ar.com.overflowdt.minekkit.pms.NewPMActivity;
import ar.com.overflowdt.minekkit.profile.ProfileActivity;

/**
 * Created by Juan on 23/02/14.
 */
public class MenuHandler {

    public boolean bindearLogica (MenuItem item, Activity activity) {
        Intent nuevaActivity;
        switch (item.getItemId()) {
            case R.id.menu_pm:
                nuevaActivity = new Intent(activity, AllPmsActivity.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_acercaDe:
                nuevaActivity = new Intent(activity, AcercaDe.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_opciones:
                nuevaActivity = new Intent(activity, ConfigActivity.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_sendPM:
                nuevaActivity = new Intent(activity, NewPMActivity.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_salir:
                activity.finish();
                break;
            case R.id.menu_players_on:
                nuevaActivity = new Intent(activity, OnlineListActivity.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_perfil:
                nuevaActivity = new Intent(activity, ProfileActivity.class);
                activity.startActivity(nuevaActivity);
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
