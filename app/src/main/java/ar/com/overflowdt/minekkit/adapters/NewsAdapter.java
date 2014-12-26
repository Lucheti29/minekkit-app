package ar.com.overflowdt.minekkit.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.News;
import ar.com.overflowdt.minekkit.util.BBCodeParser;

/**
 * Created by Fede on 26/12/2014.
 */
public class NewsAdapter extends BaseAdapter {

    public List<News> news;
    public Context context;

    public NewsAdapter(Context context1, List<News> news) {
        this.context = context1;
        this.news = news;
    }

    @Override
    public int getCount() {

        return news.size();
    }

    @Override
    public Object getItem(int arg0) {

        return news.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list_item_news, arg2, false);
        }
        TextView title = (TextView) arg1.findViewById(R.id.news_subject);
        TextView date = (TextView) arg1.findViewById(R.id.news_dateline);
        TextView message = (TextView) arg1.findViewById(R.id.news_message);
        final News n = news.get(arg0);

        title.setText(n.getSubject());
        date.setText(n.getDateline());
        message.setText(Html.fromHtml(BBCodeParser.bbcode(n.getMessage().replace("\n", "").replace("\r", ""))));

        arg1.findViewById(R.id.news_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, n.getTid(), Toast.LENGTH_SHORT).show();
            }
        });
        return arg1;
    }
}
