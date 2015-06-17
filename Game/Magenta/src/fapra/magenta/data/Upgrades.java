package fapra.magenta.data;

import android.app.Activity;
import android.content.SharedPreferences;

public class Upgrades {
    
    private static final String PreferenceTag = "";

    /**
     * Follower speed at the start of a game. Less is better.
     */
    public float followerStartSpeed;

    /**
     * Follower increment value. Less is better.
     */
    public float followerIncrement;

    public float followerStartDistance = 500;
    
    /**
     * Save the current state.
     * @param activity activity to save with
     */
    public void save(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(PreferenceTag, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("followerStartSpeed", followerStartSpeed);
        editor.putFloat("followerIncrement", followerIncrement);
        editor.apply();
    }
    
    /**
     * Load the current state.
     * @param activity
     */
    public void load(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(PreferenceTag, 0);
        
        followerStartSpeed = settings.getFloat("followerStartSpeed", 60);
        followerIncrement = settings.getFloat("followerIncrement", 0.5f);
    }
}
