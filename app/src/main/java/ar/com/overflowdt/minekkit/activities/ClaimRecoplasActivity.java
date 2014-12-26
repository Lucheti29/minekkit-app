package ar.com.overflowdt.minekkit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.MenuHandler;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * Created by Fede on 02/03/14.
 */
public class ClaimRecoplasActivity extends ActionBarActivity {

    private static final String TAG_SUCCESS = "success";
    public static final int SEGUNDOS_ENTRE_CLAIM = 24 * 60 * 60;
    public static final String TAG = "Recoplas";
    private static String url = "http://minekkit.com/api/claimRecoplas.php";
    private TextView tiempoRestante;
    private int segundosRestantes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco_x_dia);
        tiempoRestante = (TextView) findViewById(R.id.recoxdia_txt_tiemporestante);
        new LoadClaimReco().execute();
        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");

        TextView recoxdia_txt_tiemporestante = (TextView) findViewById(R.id.recoxdia_txt_tiemporestante);
        recoxdia_txt_tiemporestante.setTypeface(mecha_Condensed_Bold);
        TextView btn_obtenerReco = (TextView) findViewById(R.id.btn_obtenerReco);
        btn_obtenerReco.setTypeface(mecha_Condensed_Bold);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }


    public void claim(View view) {
        new LoadClaimReco().execute();
    }

    class LoadClaimReco extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        JSONObject json = null;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ClaimRecoplasActivity.this);
            pDialog.setMessage(getString(R.string.login_conectando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting a products from url
         */
        protected String doInBackground(String... args) {

            try {
                json = new HttpHandler().post(url, Session.getInstance());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success) {
                    case -1:
                        ShowAlertMessage.showMessage(getString(R.string.claimReco_error_exceso), ClaimRecoplasActivity.this);
                        tiempoRestante.setText("Eres muy rico");//todo: agregar mensajes random
                        break;
                    case 0:
                    case -100:
                        ShowAlertMessage.showMessage(getString(R.string.claimReco_error_solicitud), ClaimRecoplasActivity.this);
                        tiempoRestante.setText("Error");
                        break;
                    case 1:
                        ShowAlertMessage.showMessage(getString(R.string.claimReco_ok_entregado), ClaimRecoplasActivity.this);
                        if (segundosRestantes == 0) {
                            setTimer(SEGUNDOS_ENTRE_CLAIM);
                        }
                        break;
                    default:
                        ShowAlertMessage.showMessage(getString(R.string.claimReco_error_regresaMasTarde), ClaimRecoplasActivity.this);
                        if (segundosRestantes == 0) {
                            setTimer(success);
                        }
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void setTimer(int segundos) {
            final Timer T = new Timer();
            segundosRestantes = segundos;
            T.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tiempoRestante.setText(formatTime(segundosRestantes));
                            segundosRestantes--;
                            if (segundosRestantes == 0)
                                T.cancel();
                        }
                    });
                }
            }, 1000, 1000);
        }
    }

    private String formatTime(int segundosRestantes) {

        int horas = (int) Math.floor((double) segundosRestantes / 3600);
        int minutos = (int) Math.floor((double) (segundosRestantes - horas * 3600) / 60);
        int segundos = segundosRestantes - minutos * 60 - horas * 3600;
        return String.valueOf(horas) + " Hs " + String.valueOf(minutos) + " Min " + String.valueOf(segundos) + " Seg";
    }

}
