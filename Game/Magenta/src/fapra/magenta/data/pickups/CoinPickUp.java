package fapra.magenta.data.pickups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import fapra.magenta.R;
import fapra.magenta.data.Point;
import fapra.magenta.simulation.Simulation;

public class CoinPickUp extends PickUpGameObject {

    @Override
    public String toString() {
        return "CoinPickUp [coinValue=" + coinValue + "]";
    }

    public CoinPickUp(Point position, float radius) {
        super(position, radius);
        coinValue = value;
    }

    public static int value = 5;
    
    public int coinValue;
    
    @Override
    public void update(float delta, Simulation sim) {
    }

    @Override
    public boolean isAlive() {
        // directly consumed, so no isAlive state needed
        return false;
    }

    public static void setStage(int stage) {
        // TODO better values
        switch(stage) {
        case 0:
            value = 5;
            break;
        case 1:
            value = 10;
            break;
        case 2:
            value = 20;
            break;
        case 3:
            value = 50;
            break;
        case 4:
            value = 70;
            break;
        case 5:
            value = 90;
            break;
        case 6:
            value = 125;
            break;
        case 7:
            value = 200;
            break;
        default:
            value = 5;
            break;
        }
    }

    public static int getUpgradeCost(int stage) {
        switch (stage) {
        case 1:
            return 25;
        case 2:
            return 50;
        case 3:
            return 100;
        case 4:
            return 250;
        case 5:
            return 350;
        case 6:
            return 500;
        case 7:
            return 750;
        default:
            return 10;
        }
    }

    private static Bitmap img = null;
    
    @Override
    public Bitmap getDrawable(Context context) {
        if (img == null) {
            img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_coin);
        }
        return img;
    }

    public static int getMaxStage() {
        return 7;
    }
}
