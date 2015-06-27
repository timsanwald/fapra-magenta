package fapra.magenta.data.pickups;

import fapra.magenta.data.Point;

public class StopTimePickUp extends PickUpGameObject {

    /** 
     * Milliseconds of the remaining Time.
     */
    public float remainingTime;
    
    public static float time = 500f;
    
    public StopTimePickUp(Point position, float radius) {
        super(position, radius);
        remainingTime = time;
    }

    @Override
    public void update(float delta) {
        remainingTime -= delta;
    }

    @Override
    public boolean isAlive() {
        return remainingTime <= 0;
    }

    public static void setStage(int stage) {
        switch(stage) {
        case 0:
            time = 500f;
            break;
        case 1:
            time = 750f;
            break;
        case 2:
            time = 1000f;
            break;
        case 3:
            time = 1500f;
            break;
        case 4:
            time = 2500f;
            break;
        default:
            time = 500f;
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
}
