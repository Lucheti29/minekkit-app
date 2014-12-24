package ar.com.overflowdt.minekkit.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.Browser;
import ar.com.overflowdt.minekkit.util.MenuHandler;

public class WikiMainActivity extends ActionBarActivity {

    private EditText etWiki;
    private static final String WIKI_URL_PART_1 = "minekkit.com/wiki/index.php?title=Especial%3ABuscar&profile=default&search=";
    private static final String WIKI_URL_PART_2 = "&fulltext=Search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        etWiki = (EditText) findViewById(R.id.etWiki);

        //Fuentes custom
        Typeface mecha_Condensed_Bold = Typeface.createFromAsset(getAssets(),
                "fonts/Mecha_Condensed_Bold.ttf");
        TextView mtxt_buscarEN = (TextView) findViewById(R.id.mtxt_buscarEN);
        mtxt_buscarEN.setTypeface(mecha_Condensed_Bold);
        TextView ltxt_mkWiki = (TextView) findViewById(R.id.ltxt_mkWiki);
        ltxt_mkWiki.setTypeface(mecha_Condensed_Bold);
        TextView buttonWiki = (TextView) findViewById(R.id.buttonWiki);
        buttonWiki.setTypeface(mecha_Condensed_Bold);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.bindearLogica(item, this);
    }

    public void buscarEnWiki(View view) {
        Intent i = new Intent(this, Browser.class);
        i.putExtra("title", "Wiki");
        i.putExtra("direccion", WIKI_URL_PART_1 + etWiki.getText() + WIKI_URL_PART_2);
        startActivity(i);
    }

}
