package ar.com.overflowdt.minekkit.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import ar.com.overflowdt.minekkit.R;

public class AcercaDeActivity extends Activity {

    private Button btn_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        btn_volver = (Button) findViewById(R.id.btn_volver);

        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");

        btn_volver.setTypeface(mecha_Condensed_Bold);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void cerrar(View view) {
        finish();
    }

}
