package ar.com.overflowdt.minekkit.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ar.com.overflowdt.minekkit.interfaces.Enviable;

public class Session implements Enviable {

    private static final String TAG = "Session";
    public String user;
    public String pass;
    public String avatar;
    public String recoplas = "0";
    static Session instance;
    public String ver;
    public User userData;
    private static Context context;

    public Session() {
        SharedPreferences prefe = context.getSharedPreferences("logindata", Context.MODE_PRIVATE);
        if (!prefe.getString("user", "").equals("") && !prefe.getString("pass", "").equals("")) {
            this.user = prefe.getString("user", "");
            this.pass = prefe.getString("pass", "");
        }
    }

    public static Session getInstance() {
        if (instance == null)
            instance = new Session();
        return instance;
    }

    public static void setContext(Context context) {
        Session.context = context;
    }

    public String pass64() {
        try {
            return new String(Base64.encode(pass.getBytes("CP1252"), Base64.DEFAULT), "CP1252");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> list = new ArrayList<>();
        list.add(new Parametro().setValores("user", user));
        list.add(new Parametro().setValores("pass", pass64()));
        list.add(new Parametro().setValores("version", ver));
        return list;
    }

    public void logout(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("logindata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear().apply();
        removeUserFromParse();
    }

    private void removeUserFromParse() {
        ParseUser.logOut();
        //Parse push notifications unsubscribe user data
        ParsePush.unsubscribeInBackground("users");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("userId", "");
        installation.saveInBackground();
    }

    public void saveUserData(final Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("logindata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("user", Session.getInstance().user);
        editor.putString("pass", Session.getInstance().pass);
        editor.commit();
/*        JsonRequest postRequest = new JsonRequest(Request.Method.POST, ApiUrls.getInstance().getProfileURL(), new Response.Listener<JsonObject>() {

            @Override
            public void onResponse(JsonObject response) {
                avatar=response.get("avatar").getAsString();
                recoplas=response.get("recoplas").getAsString();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        postRequest.setParams(getAuthParams());
        Volley.newRequestQueue(context).add(postRequest);*/
    }

    public Map<String, String> getAuthParams() {
        Map<String, String> mParams = new HashMap<>();
        mParams.put("user", Session.getInstance().user);
        mParams.put("pass", Session.getInstance().pass64());
        return mParams;
    }

    public void addUserToParse(final User user) {
        //Parse push notifications subscribe user data
        ParsePush.subscribeInBackground("users");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        Log.d(TAG, "User registered: " + user.getUserID());
        ParseUser userp = new ParseUser();
        userp.setUsername(String.valueOf(user.getUserID()));
        userp.setPassword("minekkit");
        if (user.getEmail() != null)
            userp.setEmail(user.getEmail());
        else
            userp.setEmail("");

        userp.put("usernameForo", user.getUser());

        userp.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                if (e != null) parseLogin(user);
            }
        });
        installation.put("userId", user.getUserID());
        installation.saveInBackground();
    }

    public void parseLogin(final User user) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            //parse login
            if (user.getUserID() != 0)
                ParseUser.logInInBackground(String.valueOf(user.getUserID()), "minekkit", new LogInCallback() {
                    @Override
                    public void done(ParseUser userp, ParseException e) {
                        if (userp != null) userData = user;
                        // Else -> Signup failed. Look at the ParseException to see what happened.
                    }
                });
        }
    }
}
