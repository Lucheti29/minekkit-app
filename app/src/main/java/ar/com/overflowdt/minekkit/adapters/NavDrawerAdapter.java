package ar.com.overflowdt.minekkit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.models.Session;
import ar.com.overflowdt.minekkit.util.NavDrawerOption;
import ar.com.overflowdt.minekkit.views.RoundedImageView;


public class NavDrawerAdapter extends BaseAdapter {

    private Context currentActivity;
    private List<NavDrawerOption> navDrawerOptions;
    private int itemType;

    private static final int PROFILE_ITEM = 0;
    //private static final int MESSAGES_ITEM = 1;
    private static final int GENERIC_WITH_ICON_ITEM = 1;
    private static final int GENERIC_WITHOUT_ICON_ITEM = 2;
    private static final int DIVIDER = 3;

    public NavDrawerAdapter(Context currentActivity, List<NavDrawerOption> navDrawerOptions) {
        this.currentActivity = currentActivity;
        this.navDrawerOptions = navDrawerOptions;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                itemType = PROFILE_ITEM;
                break;
            case 10:
                itemType = DIVIDER;
                break;
            default:
                itemType = GENERIC_WITH_ICON_ITEM;
        }
        return itemType;
    }

    @Override
    public int getCount() {
        return this.navDrawerOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup container) {
        int type = getItemViewType(position);
        switch (type) {
            case PROFILE_ITEM:
                currentView = inflateAsProfileItem(position, currentView, container);
                break;
//             case MESSAGES_ITEM:
//			currentView = inflateAsMessagesItem(position, currentView, container);
//			break;
            case GENERIC_WITHOUT_ICON_ITEM:
                currentView = inflateAsGenericWithoutIcon(position, currentView, container);
                break;
            case GENERIC_WITH_ICON_ITEM:
                currentView = inflateAsGenericWithIcon(position, currentView, container);
                break;
            default:
                currentView = inflateAsDivider(position, currentView, container);
        }

        return currentView;
    }

    private View inflateAsGenericWithIcon(int position, View currentView, ViewGroup container) {
        if (currentView == null) {
            currentView = ((LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.navbardrawer_with_icon, null, false);
        }

        //obtengo la option actual
        NavDrawerOption currentOption = navDrawerOptions.get(position);

        //cargo el icono
        ((ImageView) currentView.findViewById(R.id.navdrawer_option_icon)).setImageResource(currentOption.getIconResource());

        //cargo el titulo
        ((TextView) currentView.findViewById(R.id.navdrawer_option_title)).setText(currentOption.getTitle());

        return currentView;
    }

    private View inflateAsMessagesItem(int position, View currentView, ViewGroup container) {
        if (currentView == null) {
            currentView = ((LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.navbardrawer_messages, null, false);
        }

        //obtengo la option actual
        NavDrawerOption currentOption = navDrawerOptions.get(position);

        //cargo el icono
        ((ImageView) currentView.findViewById(R.id.navdrawer_option_icon)).setImageResource(currentOption.getIconResource());

        //cargo el titulo
        ((TextView) currentView.findViewById(R.id.navdrawer_option_title)).setText(currentOption.getTitle());

        //cargo la cantidad de mensajes
        CharSequence number = "5";
        ((TextView) currentView.findViewById(R.id.navdrawer_number_of_messages)).setText(number);

        return currentView;
    }

    private View inflateAsProfileItem(int position, View currentView, ViewGroup container) {
        if (currentView == null) {
            currentView = ((LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.navbardrawer_profile, null, false);
        }

        //obtengo la option actual
        NavDrawerOption currentOption = navDrawerOptions.get(position);

        //cargo el icono
        RoundedImageView userpic = (RoundedImageView) currentView.findViewById(R.id.navdrawer_user_picture);
        userpic.setImageResource(R.drawable.steve);

        //cargo el titulo
        ((TextView) currentView.findViewById(R.id.navdrawer_option_title)).setText(currentOption.getTitle());


        CharSequence publishedProducts = "";

        //cargo user guardado

        if (Session.getInstance().user != null) {//hay user guardado
            ((TextView) currentView.findViewById(R.id.navdrawer_option_title)).setText(Session.getInstance().user);
            publishedProducts = "0 Recoplas";
        } else {
            ((TextView) currentView.findViewById(R.id.navdrawer_option_title)).setTextSize(15);
        }
//        if (user.getPhotoUrl() != null) {
//            Picasso.with(currentActivity).load(user.getPhotoUrl()).into(userpic);
//        }

        //cargo la cantidad de avisos publicados
        ((TextView) currentView.findViewById(R.id.navdrawer_published_products)).setText(publishedProducts);

        return currentView;
    }

    private View inflateAsGenericWithoutIcon(int position, View currentView, ViewGroup container) {
        if (currentView == null) {
            currentView = ((LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.navbardrawer_without_icon, null, false);
        }

        //obtengo la option actual
        NavDrawerOption currentOption = navDrawerOptions.get(position);

        //cargo el titulo
        ((TextView) currentView.findViewById(R.id.navdrawer_option_title)).setText(currentOption.getTitle());
        return currentView;
    }

    private View inflateAsDivider(int position, View currentView, ViewGroup container) {
        if (currentView == null) {
            currentView = ((LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.navbardrawer_divider, null, false);
        }
        return currentView;
    }
}
