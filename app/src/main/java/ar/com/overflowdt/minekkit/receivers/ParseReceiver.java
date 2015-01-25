package ar.com.overflowdt.minekkit.receivers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.parse.ParsePushBroadcastReceiver;

import ar.com.overflowdt.minekkit.activities.AllPmsActivity;
import ar.com.overflowdt.minekkit.activities.SinglePMActivity;

public class ParseReceiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");

        Log.d("Parse", intent.getStringExtra("com.parse.Data"));
        try {
            JsonObject data = new JsonParser().parse(intent.getStringExtra("com.parse.Data")).getAsJsonObject();
            if (data.has("extra")) {
                JsonObject extra = data.getAsJsonObject("extra");
                if (extra.has("pmId")) {
                    String id = extra.get("pmId").getAsString();
                    // Starting new intent
                    Intent in = new Intent(context, SinglePMActivity.class);
                    // sending pid to next activity
                    in.putExtra("pmid", id);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                    return;
                }
            }
            Intent i = new Intent(context, AllPmsActivity.class);
            i.putExtras(intent.getExtras());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            Intent i = new Intent(context, AllPmsActivity.class);
            i.putExtras(intent.getExtras());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
    }
}