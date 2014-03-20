package ar.com.overflowdt.minekkit.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.pms.AllPmsActivity;
import ar.com.overflowdt.minekkit.pms.PM;
import ar.com.overflowdt.minekkit.recoxdia.ClaimRecoplasActivity;
import ar.com.overflowdt.minekkit.util.HttpHandler;
import ar.com.overflowdt.minekkit.util.Session;
import ar.com.overflowdt.minekkit.util.ShowAlertMessage;

/**
 * Created by Fede on 20/03/14.
 */
public class NotificationService extends Service {


    private PowerManager.WakeLock mWakeLock;

    /**
     * Simply return null, since our Service will not be communicating with
     * any other components. It just does its work silently.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * This is where we initialize. We call this when onStart/onStartCommand is
     * called by the system. We won't do anything with the intent here, and you
     * probably won't, either.
     */
    private void handleIntent(Intent intent) {
        // obtain the wake lock
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Minekkit");
        mWakeLock.acquire();

        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getBackgroundDataSetting()) {
            //stopSelf();
            //return;
        }

        // do the actual work, in a separate thread
        new PollTask().execute();
    }

    private class PollTask extends AsyncTask<Void, Void, Void> {
        // JSON Node names
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_PRODUCTS = "pms";
        private static final String TAG_TITLE = "title";
        private static final String TAG_ID = "pmid";
        private static final String TAG_READ = "read";
        private static final String TAG_CONTENIDO = "contenido";
        int i=0;
        String titles="";
        String bigMessage="";
        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler handler = new HttpHandler();
            JSONObject json = null;
            String url = "http://minekkit.com/api/listPms.php";
            try{
                json = handler.post(url, Session.getInstance());
            }catch(Exception ex){
                ex.printStackTrace();
            }


            // Check your log cat for JSON response
            Log.d("All PMs: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                switch(success) {
                    case 1:
                        // pms found
                        // Getting Array of Pm
                        JSONArray pms = json.getJSONArray(TAG_PRODUCTS);

                        // looping through All Pm
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(NotificationService.this);
                        int lastPM = prefs.getInt("lastPM",0);

                        for (i = 0; i < pms.length() && pms.getJSONObject(i).getInt(TAG_READ)==0 && pms.getJSONObject(i).getInt(TAG_ID)>lastPM; i++){
                            titles+=pms.getJSONObject(i).getString(TAG_TITLE);
                        }

                        if(pms.length()>1 && pms.getJSONObject(0).getInt(TAG_READ)==0){
                            bigMessage=pms.getJSONObject(i).getString(TAG_CONTENIDO);
                            SharedPreferences.Editor editor=prefs.edit();
                            editor.putInt("lastPM", pms.getJSONObject(0).getInt(TAG_ID));
                            editor.commit();
                        }
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        /**
         * In here you should interpret whatever you fetched in doInBackground
         * and push any notifications you need to the status bar, using the
         * NotificationManager. I will not cover this here, go check the docs on
         * NotificationManager.
         *
         * What you HAVE to do is call stopSelf() after you've pushed your
         * notification(s). This will:
         * 1) Kill the service so it doesn't waste precious resources
         * 2) Call onDestroy() which will release the wake lock, so the device
         *    can go to sleep again and save precious battery.
         */
        @Override
        protected void onPostExecute(Void result) {
            notiTest();
            if(i>0)
                setNotification();
            stopSelf();
        }
        public void notiTest(){
            // prepare intent which is triggered if the
            // notification is selected

            Intent intent = new Intent(NotificationService.this, ClaimRecoplasActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(NotificationService.this, 0, intent, 0);

            // build notification
            // the addAction re-use the same intent to keep the example short
            Notification n  = new Notification.Builder(NotificationService.this)
                    .setContentTitle("Recoplas Disponible")
                    .setContentText("Ya puedes reclamar tu Recoplas del día")
                    .setSmallIcon(R.drawable.ic_mkapp)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                            //.addAction(R.drawable.icon, "Call", pIntent)
                            //.addAction(R.drawable.icon, "More", pIntent)
                            //.addAction(R.drawable.icon, "And more", pIntent)
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }
        private void setNotification() {
            // prepare intent which is triggered if the
            // notification is selected

            Intent intent = new Intent(NotificationService.this, AllPmsActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(NotificationService.this, 0, intent, 0);

            // build notification
            // the addAction re-use the same intent to keep the example short
            Notification n  = new Notification.Builder(NotificationService.this)
                    .setContentTitle(String.valueOf(i) + " Nuevos Mensajes Privados")
                    .setContentText("Ya puedes reclamar tu Recoplas del día")
                    .setSmallIcon(R.drawable.ic_mkapp)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle().bigText(bigMessage))
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, n);
        }
    }

    /**
     * This is deprecated, but you have to implement it if you're planning on
     * supporting devices with an API level lower than 5 (Android 2.0).
     */
    @Override
    public void onStart(Intent intent, int startId) {
        handleIntent(intent);
    }

    /**
     * This is called on 2.0+ (API level 5 or higher). Returning
     * START_NOT_STICKY tells the system to not restart the service if it is
     * killed because of poor resource (memory/cpu) conditions.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    /**
     * In onDestroy() we release our wake lock. This ensures that whenever the
     * Service stops (killed for resources, stopSelf() called, etc.), the wake
     * lock will be released.
     */
    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }
}