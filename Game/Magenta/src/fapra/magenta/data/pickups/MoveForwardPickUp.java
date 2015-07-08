package fapra.magenta.data.pickups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import fapra.magenta.R;
import fapra.magenta.data.Point;
import fapra.magenta.simulation.Simulation;

/**
 * Moves the next n steps forward automatically.
 * @author Tim Sanwald
 *
 */
public class MoveForwardPickUp extends PickUpGameObject {

    public static int n = 1;
    
    private int remainingSteps;
    public MoveForwardPickUp(Point position, float radius) {
        super(position, radius);
        remainingSteps = n;
    }

    @Override
    public void update(float delta, Simulation sim) {
        // do next step
        if (isAlive()) {
            sim.connectDirect();
            remainingSteps--;
        }
    }

    @Override
    public boolean isAlive() {
        return remainingSteps > 0;
    }

    public static void setStage(int moveForwardStage) {
        // TODO Better numbers
        switch (moveForwardStage) {
        case 0:
            n = 1;
            break;
        case 1:
            n = 2;
            break;
        case 2:
            n = 5;
            break;
        case 3:
            n = 7;
            break;
        case 4:
            n = 10;
            break;
        case 5:
            n = 13;
            break;
        case 6:
            n = 15;
            break;
        case 7:
            n = 18;
            break;
        default:
            n = 2;
            break;
        }
    }
    
    public static int getUpgradeCost(int stage) {
        switch (stage) {
        case 1:
            return 100;
        case 2:
            return 250;
        case 3:
            return 750;
        case 4:
            return 1250;
        case 5:
            return 2500;
        case 6:
            return 5000;
        case 7:
            return 10000;
        default:
            return 10;
        }
    }

    private static Bitmap img = null;
    
    @Override
    public Bitmap getDrawable(Context context) {
        if (img == null) {
            img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_forward);
        }
        return img;
    }

    public static int getMaxStage() {
        return 7;
    }

    @Override
    public String toString() {
        return "MoveForwardPickUp [remainingSteps=" + remainingSteps + "]";
    }
}
