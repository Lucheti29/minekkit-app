package ar.com.overflowdt.minekkit.ruleta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.gesture.GestureOverlayView;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import ar.com.overflowdt.minekkit.R;

import ar.com.overflowdt.minekkit.util.MenuHandler;

import ar.com.overflowdt.minekkit.util.customView.RotationView;

/**
 * Created by Fede on 29/04/14.
 */
public class RuletaActivity extends Activity {

    RotationView logo;
    TextView titulo;
    TextView costo;
    TextView desc;
    String id;
    private RotationView mRotateImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);

        desc = (TextView) findViewById(R.id.descripcion);
        costo = (TextView) findViewById(R.id.cost);
        titulo = (TextView) findViewById(R.id.titulo);
        mRotateImage = (RotationView) findViewById(R.id.ruleta_logo);


        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");


        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(mecha_Condensed_Bold);
        TextView cost = (TextView) findViewById(R.id.cost);
        cost.setTypeface(mecha_Condensed_Bold);
        TextView btn_compra = (TextView) findViewById(R.id.btn_compra);
        btn_compra.setTypeface(mecha_Condensed_Bold);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.bindearLogica(item, this);
    }

    public void toggle(View view) {
        mRotateImage.toggle();

    }





}
