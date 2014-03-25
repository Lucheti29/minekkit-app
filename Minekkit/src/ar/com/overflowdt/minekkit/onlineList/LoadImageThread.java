package ar.com.overflowdt.minekkit.onlineList;

import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Fede on 24/03/14.
 */
public class LoadImageThread  implements Runnable {
    String url;
    OnlineListAdapter.Player player;
    public LoadImageThread(String urlImage,OnlineListAdapter.Player p) {
        super();
        url=urlImage;
        player=p;
    }

    @Override
    public void run() {
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            player.face = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
