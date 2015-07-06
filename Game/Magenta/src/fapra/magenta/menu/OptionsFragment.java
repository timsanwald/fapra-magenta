package fapra.magenta.menu;

import fapra.magenta.R;
import fapra.magenta.data.Highscore;
import fapra.magenta.data.save.SaveGame;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

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
    
    
    private boolean enablePreferenceRestore = false;
    private boolean enableSaveRestore = false;
    
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals(getString(R.string.preference_restore_key))) {
            if (enablePreferenceRestore) {
                // Delete preferences
                PreferenceManager
                .getDefaultSharedPreferences(preferenceScreen.getContext())
                .edit()
                .clear()
                .commit();
                PreferenceManager.setDefaultValues(preferenceScreen.getContext(), R.xml.main_preferences, true);
                setPreferenceScreen(null);
                addPreferencesFromResource(R.xml.main_preferences);
            } else {
                enablePreferenceRestore = true;
                Toast.makeText(getActivity(), "Press again to restore preferences", Toast.LENGTH_LONG).show();
            }
        } else if (preference.getKey().equals(getString(R.string.preference_delete_save_key))) {
            if (enableSaveRestore) {
                // delete save game
                SaveGame sg = new SaveGame();
                sg.load(this.getActivity());
                sg.reset();
                sg.save(getActivity());
                Highscore hs = new Highscore(getActivity());
                hs.reset();
                hs.save();
            } else {
                enableSaveRestore = true;
                Toast.makeText(getActivity(), "Press again to restore save game", Toast.LENGTH_LONG).show();
            }

        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
