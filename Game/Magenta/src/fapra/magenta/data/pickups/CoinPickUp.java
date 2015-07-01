package fapra.magenta.data.pickups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import fapra.magenta.R;
import fapra.magenta.data.Point;

public class CoinPickUp extends PickUpGameObject {

    public CoinPickUp(Point position, float radius) {
        super(position, radius);
        coinValue = value;
    }

    public static int value = 10;
    
    public int coinValue;
    
    @Override
    public void update(float delta) {
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
            value = 100;
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
            return 75;
        case 3:
            return 200;
        case 4:
            return 500;
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
}
