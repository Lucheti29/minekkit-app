package ar.com.overflowdt.minekkit.updater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ar.com.overflowdt.minekkit.R;
/**
 * Created by Fede on 28/10/2014.
 */
public class UpdateAppAsyncTask extends AsyncTask<Object,String,String> {
    String updateURL ="http://minekkit.com/UpdateApk/Minekkit.apk";
    public Context context;
    ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getString(R.string.login_actualizando));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Object... args) {
        String path = "/sdcard/Minekkit.apk";
        try {
            URL url = new URL(updateURL);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(path);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress(String.valueOf((int) (total * 100 / fileLength))+"% Descargado");
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("YourApp", "Well that didn't work out so well...");
            Log.e("YourApp", e.getMessage());
        }
        return path;
    }

    // begin the installation by opening the resulting file
    @Override
    protected void onPostExecute(String path) {
        pDialog.dismiss();
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive" );
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("Lofting", "About to install new .apk");
        this.context.startActivity(i);
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        pDialog.setMessage(progress[0]);
    }

    public AsyncTask setContext(Context con) {
        context=con;
        return this;
    }
}
