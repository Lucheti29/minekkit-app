package ar.com.overflowdt.minekkit.configuracion;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import ar.com.overflowdt.minekkit.R;

/**
 * Created by Juan on 01/05/14.
 */
public class ConfigActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
