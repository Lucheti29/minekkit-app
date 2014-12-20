package ar.com.overflowdt.minekkit.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import ar.com.overflowdt.minekkit.R;

public class ConfigActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

    }

//    @Override
//    protected void onCreate(final Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
//    }
//
//    public static class MyPreferenceFragment extends PreferenceFragment
//    {
//        @Override
//        public void onCreate(final Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.settings);
//        }
//    }
}