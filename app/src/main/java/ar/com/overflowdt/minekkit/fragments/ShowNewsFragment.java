package ar.com.overflowdt.minekkit.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.News;
import ar.com.overflowdt.minekkit.sync.JsonRequest;
import ar.com.overflowdt.minekkit.util.ApiUrls;
import ar.com.overflowdt.minekkit.util.BBCodeParser;

/**
 * Created by Fede on 26/12/2014.
 */
public class ShowNewsFragment extends Fragment {

    News news;
    private View rootView;
    private String tid;

    public ShowNewsFragment() {

    }

    @SuppressLint("ValidFragment")
    public ShowNewsFragment(String tid) {
        this.tid = tid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_news, container, false);
        loadNews();
        return rootView;
    }


    private void loadNews() {
        if (tid == null) return;
        String uri = String.format(ApiUrls.getInstance().getNewsURL() + "?tid=%1$s",
                tid);

        JsonRequest postRequest = new JsonRequest(Request.Method.GET, uri, new Response.Listener<JsonObject>() {

            @Override
            public void onResponse(JsonObject response) {
                news = new Gson().fromJson(response.getAsJsonObject("news"), News.class);
                loadData();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        if (getActivity() == null)
            Log.d("ShowNews", "es Null la activity");
        else if (postRequest == null)
            Log.d("ShowNes", "es null el postrequest");
        Volley.newRequestQueue(getActivity()).add(postRequest);
    }

    private void loadData() {
        ((TextView) rootView.findViewById(R.id.shownews_title)).setText(news.getSubject());
        ((TextView) rootView.findViewById(R.id.shownews_message)).setText(Html.fromHtml(BBCodeParser.bbcode(news.getMessage())));
        ((TextView) rootView.findViewById(R.id.shownews_message)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) rootView.findViewById(R.id.shownews_byuser)).setText("Por " + news.getUsername());
        ((TextView) rootView.findViewById(R.id.shownews_time)).setText(news.getDateline());
        Picasso.with(getActivity()).load(news.getAvatar()).placeholder(R.drawable.steve).into((ImageView) rootView.findViewById(R.id.shownews_userpic));
    }

    public void updateNewsView(String tid) {
        this.tid = tid;
        loadNews();
    }
}
