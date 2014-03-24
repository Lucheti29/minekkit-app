package ar.com.overflowdt.minekkit.recoxdia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
import ar.com.overflowdt.minekkit.util.Session;

/**
 * Created by Fede on 02/03/14.
 */
public class ClaimRecoplasActivity extends Activity{

    private static final String TAG_SUCCESS = "success";
    private static String url = "http://minekkit.com/api/claimRecoplas.php";
    private TextView tiempoRestante;
    private int segundosRestantes=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco_x_dia);
        tiempoRestante=(TextView) findViewById(R.id.recoxdia_txt_tiemporestante);
        new LoadClaimReco().execute();
        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");

        TextView recoxdia_txt_tiemporestante = (TextView)findViewById(R.id.recoxdia_txt_tiemporestante);
        recoxdia_txt_tiemporestante.setTypeface(mecha_Condensed_Bold);
        TextView btn_obtenerReco = (TextView)findViewById(R.id.btn_obtenerReco);
        btn_obtenerReco.setTypeface(mecha_Condensed_Bold);
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

    public void claim(View view){
        new LoadClaimReco().execute();
    }

    class LoadClaimReco extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        JSONObject json=null;
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
         * */
        protected String doInBackground(String... args) {

            try{
                json = new HttpHandler().post(url, Session.getInstance());

            }catch(Exception ex){
                ex.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch (success){
                    case -1:
                        showMessage(getString(R.string.claimReco_error_exceso));
                        break;
                    case 0:
                        showMessage(getString(R.string.claimReco_error_solicitud));
                        break;
                    case 1:
                        showMessage(getString(R.string.claimReco_ok_entregado));
                        break;
                    default:
                        showMessage(getString(R.string.claimReco_error_regresaMasTarde));
                        if(segundosRestantes==0){
                            segundosRestantes=success;
                            final Timer T=new Timer();
                            T.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            tiempoRestante.setText(formatTime(segundosRestantes));
                                            segundosRestantes--;
                                            if(segundosRestantes==0)
                                                T.cancel();
                                        }
                                    });
                                }
                            }, 1000, 1000);
                        }
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String formatTime(int segundosRestantes) {

        int horas=(int)Math.floor((double)segundosRestantes/3600);
        int minutos= (int)Math.floor((double)(segundosRestantes-horas*3600)/60);
        int segundos= segundosRestantes-minutos*60-horas*3600;
        return String.valueOf(horas) + " Hs " + String.valueOf(minutos) + " Min " + String.valueOf(segundos) + " Seg";
    }

    public void showMessage(String message) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Aviso");
        alerta.setMessage(message);
        alerta.setCancelable(false);
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        alerta.show();
    }
}
