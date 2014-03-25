package ar.com.overflowdt.minekkit.util;

import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import ar.com.overflowdt.minekkit.interfaces.ImageLoadable;

/**
 * Created by Fede on 24/03/14.
 */
public class LoadImageThread  implements Runnable {
    String url;
    ImageLoadable player;
    public LoadImageThread(String urlImage,ImageLoadable p) {
        super();
        url=urlImage;
        player=p;
    }

    @Override
    public void run() {
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            player.setImage(BitmapFactory.decodeStream(conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
