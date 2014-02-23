package ar.com.overflowdt.minekkit.util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.acercade.AcercaDe;

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
        }
        return true;

    }


}
