package fapra.magenta.listeners;

import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.obstacles.ObstacleGameObject;
import fapra.magenta.data.pickups.CoinPickUp;
import fapra.magenta.data.pickups.PickUpGameObject;
import fapra.magenta.data.save.SaveGame;
import fapra.magenta.simulation.Simulation;

public class CoinCalculationListener implements GameListenerInterface {

    public int coins = 0;
    
    @Override
    public void addedNewLine(Point start, Point target) {
        
    }

    @Override
    public void touchedPickup(PickUpGameObject pickup) {
        if (pickup instanceof CoinPickUp) {
            coins += ((CoinPickUp) pickup).coinValue;
        }
    }

    @Override
    public void finishedLine(Line line) {
        coins++;
    }

    @Override
    public void touchedObstacle(ObstacleGameObject obstacle) {
        if (coins > 0) {
            coins--;
        }
    }

    @Override
    public void finishedGame(Simulation simulation) {
        SaveGame sg = new SaveGame();
        sg.load(simulation.activity);
        sg.coins += this.coins;
        sg.save(simulation.activity);
    }
}
