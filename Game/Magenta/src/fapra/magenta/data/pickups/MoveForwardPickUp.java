package fapra.magenta.data.pickups;

import fapra.magenta.data.Point;

/**
 * Moves the next n steps forward automatically.
 * @author Tim Sanwald
 *
 */
public class MoveForwardPickUp extends PickUpGameObject {

    public static int n = 10;
    
    private int remainingSteps;
    public MoveForwardPickUp(Point position, float radius) {
        super(position, radius);
        remainingSteps = n;
    }

    @Override
    public void update(float delta) {
        //TODO do next step
        remainingSteps--;
    }

    @Override
    public boolean isAlive() {
        return remainingSteps > 0;
    }
    
    public static void setSteps(int steps) {
        n = steps;
    }

}
