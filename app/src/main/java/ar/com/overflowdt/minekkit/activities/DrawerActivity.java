package ar.com.overflowdt.minekkit.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.adapters.NavDrawerAdapter;
import ar.com.overflowdt.minekkit.fragments.NewsFragment;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.NavDrawerOption;

public class DrawerActivity extends ActionBarActivity {
    private static final String OPTION_PROFILE = ProfileActivity.TAG;
    private static final String OPTION_MESSAGES = AllPmsActivity.TAG;
    private static final String OPTION_SHOP = AllRecompensasActivity.TAG;
    private static final String OPTION_RECOPLAS = ClaimRecoplasActivity.TAG;
    private static final String OPTION_MAP = "MAP";
    private static final String OPTION_ROULETTE = RuletaActivity.TAG;
    private static final String OPTION_WIKI = WikiMainActivity.TAG;
    private static final String OPTION_ONLINE = OnlineListActivity.TAG;
    private static final String OPTION_DIVIDER = "DIVIDER";
    private static final String OPTION_ABOUT = AcercaDeActivity.TAG;
    private static final String OPTION_SETTINGS = ConfigActivity.TAG;
    private static final String OPTION_INVENTORY = "INVENTORY";
    private static final String OPTION_DENUNCIA = DenunciaActivity.TAG;
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

        if (savedInstanceState == null) {
            //cargo el fragmento principal
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame_container, new NewsFragment(), NewsFragment.TAG);
            ft.commit();
        }
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
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectNavigationDrawerItem(position);
            }
        });
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
        optionsList.add(new NavDrawerOption(OPTION_SHOP, "Shop de Recompensas", R.drawable.icon_shop2));
        optionsList.add(new NavDrawerOption(OPTION_INVENTORY, "Inventario", R.drawable.icon_inventory));
        optionsList.add(new NavDrawerOption(OPTION_RECOPLAS, "Recoplas Gratis", R.drawable.icon_recoplas));
        optionsList.add(new NavDrawerOption(OPTION_ROULETTE, "Ruleta", R.drawable.icon_roulette));
        optionsList.add(new NavDrawerOption(OPTION_MAP, "Mapa", R.drawable.icon_map));
        optionsList.add(new NavDrawerOption(OPTION_WIKI, "Wiki", R.drawable.icon_wiki));
        optionsList.add(new NavDrawerOption(OPTION_ONLINE, "Jugadores Online", R.drawable.icon_peopleonline));
        optionsList.add(new NavDrawerOption(OPTION_DENUNCIA, "Denunciar", R.drawable.icon_justice));

        optionsList.add(new NavDrawerOption(OPTION_DIVIDER, "Divider", 0));

        optionsList.add(new NavDrawerOption(OPTION_ABOUT, "Sobre Nosotros", R.drawable.icon_about));
        optionsList.add(new NavDrawerOption(OPTION_SETTINGS, "Opciones", R.drawable.icon_settings));

        return optionsList;
    }

    /**
     * funcion para iniciar las actividades del navbar drawer*
     */
    protected void selectNavigationDrawerItem(int position) {
        mDrawerLayout.closeDrawers();
        NavDrawerOption currentItem = optionsList.get(position);
        Intent intent;
        switch (currentItem.getOptionID()) {
            case OPTION_PROFILE:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case OPTION_ABOUT:
                intent = new Intent(this, AcercaDeActivity.class);
                startActivity(intent);
                break;
            case OPTION_MAP:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Aviso");
                dialogo1.setMessage("El mapa puede consumir muchos datos, se recomienda usar WiFi");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Entendido!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent i = new Intent(DrawerActivity.this, BrowserActivity.class);
                        i.putExtra("title", "Mapa");
                        i.putExtra("direccion", "minekkit.com:8123");
                        startActivity(i);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                break;
            case OPTION_MESSAGES:
                intent = new Intent(this, AllPmsActivity.class);
                startActivity(intent);
                break;
            case OPTION_INVENTORY:
                intent = new Intent(this, BrowserActivity.class);
                intent.putExtra("title", "Inventory");
                String encodedPass = "";
                try {
                    encodedPass = new String(Base64.encode(Session.getInstance().pass.getBytes("CP1252"), Base64.DEFAULT), "CP1252");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                intent.putExtra("direccion", "minekkit.com/api/inventory.php?user=" + Session.getInstance().user + "&pass=" + encodedPass);
                startActivity(intent);
                break;
            case OPTION_ONLINE:
                intent = new Intent(this, OnlineListActivity.class);
                startActivity(intent);
                break;
            case OPTION_RECOPLAS:
                intent = new Intent(this, ClaimRecoplasActivity.class);
                startActivity(intent);
                break;
            case OPTION_ROULETTE:
                intent = new Intent(this, RuletaActivity.class);
                startActivity(intent);
                break;
            case OPTION_SETTINGS:
                intent = new Intent(this, ConfigActivity.class);
                startActivity(intent);
                break;
            case OPTION_SHOP:
                intent = new Intent(getApplicationContext(), AllRecompensasActivity.class);
                startActivity(intent);
                break;
            case OPTION_WIKI:
                intent = new Intent(this, WikiMainActivity.class);
                startActivity(intent);
                break;
            case OPTION_DENUNCIA:
                intent = new Intent(this, DenunciaActivity.class);
                startActivity(intent);
                break;
        }
        supportInvalidateOptionsMenu();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }
}