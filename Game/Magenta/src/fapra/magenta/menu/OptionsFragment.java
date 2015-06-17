package fapra.magenta.menu;

import fapra.magenta.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;
import android.util.Log;

public class OptionsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    
    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.main_preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.preference_sound_key))) {
            Log.d("Preferences", "Play sound " + sharedPreferences.getBoolean(key, true));
        } else if (key.equals(getString(R.string.preference_music_key))) {
            Log.d("Preferences", "Play Music " + sharedPreferences.getBoolean(key, true));
        }
    }
}
