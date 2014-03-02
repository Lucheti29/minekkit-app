package ar.com.overflowdt.minekkit.recoxdia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
            pDialog.setMessage("Conectando con el servidor...");
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
                        showMessage("Tienes más de 50 Recoplas. Gasta unos cuantos en la tienda y volvé a solicitar tu Reco Gratis!");
                        break;
                    case 0:
                        showMessage("Ha habido un error en la solicitud.");
                        break;
                    case 1:
                        showMessage("Has obtenido 1 Recoplas. Volvé mañana para pedir otro!");
                        break;
                    default:
                        showMessage("Todavía no pasaron 24 horas. Volvé más tarde!");
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
