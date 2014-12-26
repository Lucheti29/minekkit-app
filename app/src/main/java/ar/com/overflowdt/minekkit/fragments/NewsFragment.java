package ar.com.overflowdt.minekkit.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.adapters.NewsAdapter;
import ar.com.overflowdt.minekkit.models.News;
import ar.com.overflowdt.minekkit.sync.JsonRequest;
import ar.com.overflowdt.minekkit.util.ApiUrls;


public class NewsFragment extends Fragment {
    public static final String TAG = "NewsFragment";
    View rootView;
    private ListView newslist;
    private ArrayList<News> news;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
        newslist = (ListView) rootView.findViewById(R.id.news_list);

        loadNews();

        return rootView;
    }

    private void loadNews() {
        JsonRequest postRequest = new JsonRequest(Request.Method.GET, ApiUrls.getInstance().getNewsURL(), new Response.Listener<JsonObject>() {

            @Override
            public void onResponse(JsonObject response) {
                Type type = new TypeToken<List<News>>() {
                }.getType();
                news = new Gson().fromJson(response.getAsJsonArray("posts"), type);
                newslist.setAdapter(new NewsAdapter(getActivity(), news));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        Volley.newRequestQueue(getActivity()).add(postRequest);
    }
}
