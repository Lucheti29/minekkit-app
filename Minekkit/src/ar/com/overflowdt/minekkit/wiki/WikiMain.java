package ar.com.overflowdt.minekkit.wiki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.util.Browser;
import ar.com.overflowdt.minekkit.util.MenuHandler;

public class WikiMain extends Activity {

    private EditText etWiki;
    private static final String WIKI_URL_PART_1 = "minekkit.com/wiki/index.php?title=Especial%3ABuscar&profile=default&search=";
    private static final String WIKI_URL_PART_2 = "&fulltext=Search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        etWiki = (EditText) findViewById(R.id.etWiki);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.bindearLogica(item, this);
    }

    public void buscarEnWiki(View view)
    {
        Intent i = new Intent(this,Browser.class);
        i.putExtra("direccion", WIKI_URL_PART_1 + etWiki.getText() + WIKI_URL_PART_2);
        startActivity(i);
    }

}
