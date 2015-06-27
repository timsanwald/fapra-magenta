package fapra.magenta.data.pickups;

import fapra.magenta.data.Point;

/**
 * Moves the next n steps forward automatically.
 * @author Tim Sanwald
 *
 */
public class MoveForwardPickUp extends PickUpGameObject {

    public static int n = 5;
    
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

    public static void setStage(int moveForwardStage) {
        // TODO Better numbers
        switch (moveForwardStage) {
        case 1:
            n = 5;
            break;
        case 2:
            n = 10;
            break;
        case 3:
            n = 15;
            break;
        case 4:
            n = 20;
            break;
        default:
            n = 25;
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
