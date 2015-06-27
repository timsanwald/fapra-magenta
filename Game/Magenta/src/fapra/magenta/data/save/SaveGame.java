package fapra.magenta.data.save;

import fapra.magenta.R;
import fapra.magenta.data.pickups.CoinPickUp;
import fapra.magenta.data.pickups.MoveForwardPickUp;
import fapra.magenta.data.pickups.StopTimePickUp;
import android.app.Activity;
import android.content.SharedPreferences;

public class SaveGame {

    /**
     * Follower speed at the start of a game. Less is better.
     */
    public float followerStartSpeed;

    /**
     * Follower increment value. Less is better.
     */
    public float followerIncrement;

    public float followerStartDistance = 500;
    
    public int coinPickupStage = 0;
    public int stopTimeStage = 0;
    public int moveForwardStage = 0;

    
    public int coins = 0;
    
    /**
     * Save the current state.
     * @param activity activity to save with
     */
    public void save(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(activity.getString(R.string.upgrades_storage_key), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("followerStartSpeed", followerStartSpeed);
        editor.putFloat("followerIncrement", followerIncrement);
        editor.putInt("moveForwardStage", moveForwardStage);
        editor.putInt("coinPickupStage", coinPickupStage);
        editor.putInt("stopTimeStage", stopTimeStage);
        editor.putInt("coins", coins);
        editor.apply();
    }
    
    /**
     * Load the current state.
     * @param activity
     */
    public void load(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(activity.getString(R.string.upgrades_storage_key), 0);
        followerStartSpeed = settings.getFloat("followerStartSpeed", 60);
        followerIncrement = settings.getFloat("followerIncrement", 0.5f);
        coinPickupStage = settings.getInt("coinPickupStage", 0);
        CoinPickUp.setStage(coinPickupStage);
        stopTimeStage = settings.getInt("stopTimeStage", 0);
        StopTimePickUp.setStage(stopTimeStage);
        moveForwardStage = settings.getInt("moveForwardStage", 0);
        MoveForwardPickUp.setStage(moveForwardStage);
        
        coins = settings.getInt("coins", 0);
    }
}
