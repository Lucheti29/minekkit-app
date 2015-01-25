package ar.com.overflowdt.minekkit.sync;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class JsonRequest extends Request<JsonObject> {

    private Listener<JsonObject> listener;
    private Map<String, String> mParams;
    private int method;
    private String cookies;


    public JsonRequest(int method, String url, Listener<JsonObject> listener, ErrorListener errorListener) {
        super((method == Request.Method.PUT || method == Method.DELETE) ? Request.Method.POST : method, url, errorListener);
        Log.d("JsonRequest", method == Request.Method.PUT ? "PUT" : (method == Method.DELETE ? "DELETE" : (method == Method.POST ? "POST" : "GET")) + " URL: " + url);
        this.method = method;
        this.listener = listener;
        int socketTimeout = 30000;//30000ms =  30 seconds - change to what you want 0 = no timeout
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        this.setParams(new HashMap<String, String>());
        this.setRetryPolicy(policy);
    }


    @Override
    protected Response<JsonObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonResponse = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("Objeto json", jsonResponse);
            return Response.success(new JsonParser().parse(jsonResponse).getAsJsonObject(), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new VolleyError(response));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Response.error(new VolleyError(response));
        }

    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        String jsonResponse = null;
        try {
            jsonResponse = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
            Log.d("Error Response Body", jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(JsonObject response) {
        listener.onResponse(response);
    }

    public void setParams(Map<String, String> mParams) {
        this.mParams = mParams;
        if (method == Method.PUT) {
            this.mParams.put("_method", "PUT");
        }
        if (method == Method.DELETE) {
            this.mParams.put("_method", "DELETE");
        }
        //debug
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            Log.d("JsonRequest Params:", entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        if (this.cookies != "") {
            headers.put("Cookie", this.cookies);
        }

        return headers;
    }

}