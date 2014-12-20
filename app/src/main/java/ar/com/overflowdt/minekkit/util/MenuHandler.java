package ar.com.overflowdt.minekkit.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.activities.AcercaDeActivity;
import ar.com.overflowdt.minekkit.activities.ConfigActivity;
import ar.com.overflowdt.minekkit.activities.LoginActivity;
import ar.com.overflowdt.minekkit.activities.OnlineListActivity;
import ar.com.overflowdt.minekkit.activities.AllPmsActivity;
import ar.com.overflowdt.minekkit.activities.NewPMActivity;
import ar.com.overflowdt.minekkit.activities.ProfileActivity;

/**
 * Created by Juan on 23/02/14.
 */
public class MenuHandler {

    public boolean bindearLogica(MenuItem item, Activity activity) {
        Intent nuevaActivity;
        switch (item.getItemId()) {
            case R.id.menu_pm:
                nuevaActivity = new Intent(activity, AllPmsActivity.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_acercaDe:
                nuevaActivity = new Intent(activity, AcercaDeActivity.class);
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
                /*Intent i = new Intent(activity,Browser.class);
                i.putExtra("title","Jugadores Online");
                i.putExtra("direccion", "minekkit.com/WhosOnline/");
                activity.startActivity(i);*/
                break;
            case R.id.menu_perfil:
                nuevaActivity = new Intent(activity, ProfileActivity.class);
                activity.startActivity(nuevaActivity);
                break;
            case R.id.menu_logout:
                SharedPreferences preferencias = activity.getSharedPreferences("logindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("user", "");
                editor.putString("pass", "");
                editor.commit();
                Intent i2 = new Intent(activity.getApplicationContext(), LoginActivity.class);
                activity.startActivity(i2);
                activity.finish();
                break;
        }
        return true;

    }


}
