package ar.com.overflowdt.minekkit.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.interfaces.Enviable;
import ar.com.overflowdt.minekkit.sync.JsonRequest;
import ar.com.overflowdt.minekkit.util.ApiUrls;

public class Session implements Enviable {

    private static final String TAG = "Session";
    public String user;
    public String pass;
    public String avatar;
    public String recoplas = "0";
    static Session instance;
    public String ver;
    public User userData;
    
    public static Session getInstance() {
        if (instance == null)
            instance = new Session();
        return instance;
    }

    public String pass64() {
        try {
            return new String(Base64.encode(pass.getBytes("CP1252"), Base64.DEFAULT), "CP1252");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ;
        return null;
    }

    @Override
    public ArrayList<Parametro> armarArrayDeParametros() {
        ArrayList<Parametro> list = new ArrayList<Parametro>();
        list.add(new Parametro().setValores("user", user));
        list.add(new Parametro().setValores("pass", pass64()));
        list.add(new Parametro().setValores("version", ver));
        return list;
    }

    public void logout(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences("logindata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear().apply();
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
        Map<String, String> mParams = new HashMap<String, String>();
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
        userp.setEmail(user.getEmail());


        userp.put("usernameForo", user.getUser());

        userp.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    parseLogin(user);
                }
            }
        });
        installation.put("userId", user.getUserID());
        installation.saveInBackground();
    }

    public void parseLogin(final User user) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //if is logged in do not do anything
            return;
        } else {
            //parse login
            if (user.getUserID() != 0)
                ParseUser.logInInBackground(String.valueOf(user.getUserID()), "minekkit", new LogInCallback() {


                    @Override
                    public void done(ParseUser userp, ParseException e) {
                        if (userp != null) {
                            userData = user;
                        } else {
                            // Signup failed. Look at the ParseException to see what happened.
                        }
                    }

                });
        }
    }


}
