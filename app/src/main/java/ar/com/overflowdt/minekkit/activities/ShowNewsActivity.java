package ar.com.overflowdt.minekkit.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.adapters.NewsAdapter;
import ar.com.overflowdt.minekkit.interfaces.ImageLoadable;
import ar.com.overflowdt.minekkit.models.News;
import ar.com.overflowdt.minekkit.sync.JsonRequest;
import ar.com.overflowdt.minekkit.util.ApiUrls;
import ar.com.overflowdt.minekkit.util.BBCodeParser;

/**
 * Created by Fede on 26/12/2014.
 */
public class ShowNewsActivity extends ActionBarActivity {

    News news;
    private String tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        tid = getIntent().getStringExtra("tid");
        loadNews();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void loadNews() {
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
                Toast.makeText(ShowNewsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        Volley.newRequestQueue(this).add(postRequest);
    }

    private void loadData() {
        ((TextView) findViewById(R.id.shownews_title)).setText(news.getSubject());
        ((TextView) findViewById(R.id.shownews_message)).setText(Html.fromHtml(BBCodeParser.bbcode(news.getMessage())));
        ((TextView) findViewById(R.id.shownews_byuser)).setText("Por " + news.getUsername());
        ((TextView) findViewById(R.id.shownews_time)).setText(news.getDateline());
        ((ImageView) findViewById(R.id.shownews_userpic)).setImageResource(R.drawable.steve);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }
}
