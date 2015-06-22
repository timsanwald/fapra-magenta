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
    
    public static void setTime(float time) {
        StopTimePickUp.time = time;
    }
}
