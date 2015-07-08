package fapra.magenta.data.pickups;

import java.util.Random;

import android.util.Log;
import fapra.magenta.data.Point;
import fapra.magenta.simulation.Simulation;
import fapra.magenta.target.GridManager;

public class PickUpFactory {
    
    private static final Random rand = new Random();
    
    private static final int possibilities = 3;
    
    public static PickUpGameObject generatePickUpRandomly(Simulation sim, GridManager manager) {
        // To add a new PickUp increase the amount of pickups and add a new case below
        PickUpGameObject pickup = null;
        switch(rand.nextInt(possibilities)) {
            case 0:
                pickup = new StopTimePickUp(generateRandomPosition(sim), manager.pointSize - 10);
                break;
            case 1:
                pickup = new MoveForwardPickUp(generateRandomPosition(sim), manager.pointSize - 10);
                break;
            case 2:
                pickup = new CoinPickUp(generateRandomPosition(sim), manager.pointSize - 10);
                break;
        }
        Log.d("Factory", "Generated Pickup " + pickup.toString());
        return pickup;
    }
    
    private static Point generateRandomPosition(Simulation sim) {
        //TODO implement more real random
        float randomX;
        float randomY;
       
        
        if (sim.startPoint.x > sim.targetPoint.x) {
            randomX = randInt(sim.targetPoint.x, sim.startPoint.x);
        } else {
            randomX = randInt(sim.targetPoint.x, sim.startPoint.x);
        }
        
        if (sim.startPoint.y > sim.targetPoint.y) {
            randomY = randInt(sim.targetPoint.y, sim.startPoint.y);
        } else {
            randomY = randInt(sim.targetPoint.y, sim.startPoint.y);
        }
        
        return new Point(randomX, randomY);
    }
    
    private static float randInt(float Min, float Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }
}
