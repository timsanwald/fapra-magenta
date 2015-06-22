package fapra.magenta.data.pickups;

import java.util.Random;

import fapra.magenta.data.Point;
import fapra.magenta.simulation.Simulation;
import fapra.magenta.target.GridManager;

public class PickUpFactory {
    
    private static final Random rand = new Random();
    
    private static final int possibilities = 2;
    
    public static PickUpGameObject generatePickUpRandomly(Simulation sim, GridManager manager) {
        // To add a new PickUp increase the amount of pickups and add a new case below
        switch(rand.nextInt(possibilities)) {
            case 0:
                return new StopTimePickUp(generateRandomPosition(sim), manager.pointSize - 10);
            case 1:
                return new MoveForwardPickUp(generateRandomPosition(sim), manager.pointSize - 10);
        }
        // shouldn't happen
        return null;
    }
    
    private static Point generateRandomPosition(Simulation sim) {
        //TODO implement real random xD
        return new Point(sim.startPoint.x, sim.targetPoint.y);
    }
}
