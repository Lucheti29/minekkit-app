package ar.com.overflowdt.minekkit.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.adapters.NavDrawerAdapter;
import ar.com.overflowdt.minekkit.util.NavDrawerOption;

public class DrawerActivity extends ActionBarActivity {
    private static final String OPTION_PROFILE = "";
    private static final String OPTION_MESSAGES = "";
    private static final String OPTION_SHOP = "";
    private static final String OPTION_RECOPLAS = "";
    private static final String OPTION_MAP = "";
    private static final String OPTION_ROULETTE = "";
    private static final String OPTION_WIKI = "";
    private static final String OPTION_ONLINE = "";
    private static final String OPTION_DIVIDER = "";
    private static final String OPTION_ABOUT = "";
    private static final String OPTION_SETTINGS = "";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Toolbar toolbar;
    private ListView mDrawerList;
    private ArrayList<NavDrawerOption> optionsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        initializeToolbar();
        initializeDrawer();
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_mkapp);
    }

    private void initializeDrawer() {
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        String[] mPlanetTitles = new String[]{"Mensajes", "Recompensas", "Ruleta", "Donar", "Foro", "Log Out"};
        mDrawerList.setAdapter(new NavDrawerAdapter(this, navigationDrawerOptions()));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    private List<NavDrawerOption> navigationDrawerOptions() {
        optionsList = new ArrayList<NavDrawerOption>();
        optionsList.add(new NavDrawerOption(OPTION_PROFILE, "Username", R.drawable.steve));

        optionsList.add(new NavDrawerOption(OPTION_MESSAGES, "Mensajes", R.drawable.icon_messages));
        optionsList.add(new NavDrawerOption(OPTION_SHOP, "Shop de Recompensas", R.drawable.icon_shop));
        optionsList.add(new NavDrawerOption(OPTION_RECOPLAS, "Recoplas Gratis", R.drawable.icon_recoplas));
        optionsList.add(new NavDrawerOption(OPTION_ROULETTE, "Ruleta", R.drawable.icon_roulette));
        optionsList.add(new NavDrawerOption(OPTION_MAP, "Mapa", R.drawable.icon_map));
        optionsList.add(new NavDrawerOption(OPTION_WIKI, "Wiki", R.drawable.icon_wiki));
        optionsList.add(new NavDrawerOption(OPTION_ONLINE, "Wiki", R.drawable.icon_peopleonline));

        optionsList.add(new NavDrawerOption(OPTION_DIVIDER, "*Divider*", 0));

        optionsList.add(new NavDrawerOption(OPTION_ABOUT, "*Help*", 0));
        optionsList.add(new NavDrawerOption(OPTION_SETTINGS, "*Settings*", R.drawable.icon_settings));

        return optionsList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }
}