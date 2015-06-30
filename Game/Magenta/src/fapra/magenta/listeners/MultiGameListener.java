package fapra.magenta.listeners;

import java.util.LinkedList;
import java.util.List;

import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.obstacles.ObstacleGameObject;
import fapra.magenta.data.pickups.PickUpGameObject;
import fapra.magenta.simulation.Simulation;

public class MultiGameListener implements GameListenerInterface {

    @Override
    public void addedNewLine(Point start, Point target) {
        for (GameListenerInterface listen : listeners) {
            listen.addedNewLine(start, target);
        }
    }

    @Override
    public void touchedPickup(PickUpGameObject pickup) {
        for (GameListenerInterface listen : listeners) {
            listen.touchedPickup(pickup);
        }
    }

    @Override
    public void finishedLine(Line line) {
        for (GameListenerInterface listen : listeners) {
            listen.finishedLine(line);
        }
    }

    @Override
    public void touchedObstacle(ObstacleGameObject obstacle) {
        for (GameListenerInterface listen : listeners) {
            listen.touchedObstacle(obstacle);
        }
    }
    
    private List<GameListenerInterface> listeners = new LinkedList<GameListenerInterface>();
    
    public void addGameListener(GameListenerInterface gameListener) {
        listeners.add(gameListener);
    }
    
    public void removeGameListener(GameListenerInterface gameListener) {
        listeners.remove(gameListener);
    }
    
    @Override
    public void finishedGame(Simulation simulation) {
        for (GameListenerInterface listen : listeners) {
            listen.finishedGame(simulation);
        }
    }

}
