package fapra.magenta.data.pickups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import fapra.magenta.R;
import fapra.magenta.data.Point;
import fapra.magenta.simulation.Simulation;

public class StopTimePickUp extends PickUpGameObject {

    /** 
     * Milliseconds of the remaining Time.
     */
    public float remainingTime;
    
    public static float time = 1000f;
    
    public StopTimePickUp(Point position, float radius) {
        super(position, radius);
        remainingTime = time;
    }

    @Override
    public void update(float delta, Simulation sim) {
        remainingTime -= delta;
    }

    @Override
    public boolean isAlive() {
        return remainingTime <= 0;
    }

    public static void setStage(int stage) {
        switch(stage) {
        case 0:
            time = 1000f;
            break;
        case 1:
            time = 2000f;
            break;
        case 2:
            time = 2500f;
            break;
        case 3:
            time = 3500f;
            break;
        case 4:
            time = 4000f;
            break;
        case 5:
            time = 5000f;
            break;
        default:
            time = 1000f;
            break;
        }
    }
    
    public static int getUpgradeCost(int stage) {
        switch (stage) {
        case 1:
            return 25;
        case 2:
            return 75;
        case 3:
            return 200;
        case 4:
            return 500;
        case 5:
            return 2000;
        default:
            return 10;
        }
    }
    
    private static Bitmap img = null;
    
    @Override
    public Bitmap getDrawable(Context context) {
        if (img == null) {
            img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_time);
        }
        return img;
    }
    
    public static int getMaxStage() {
        return 4;
    }

    @Override
    public String toString() {
        return "StopTimePickUp [remainingTime=" + remainingTime + "]";
    }
}
