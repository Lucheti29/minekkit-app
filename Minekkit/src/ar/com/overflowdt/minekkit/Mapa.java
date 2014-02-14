package ar.com.overflowdt.minekkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Mapa extends Activity {
	
	private WebView webView1;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);
		
        webView1 = (WebView) findViewById(R.id.mapa_id);
        
        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Bundle bundle = getIntent().getExtras();
        webView1.loadUrl("http://" + bundle.getString("direccion"));
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
