package fapra.magenta.listeners;

import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.obstacles.ObstacleGameObject;
import fapra.magenta.data.pickups.PickUpGameObject;

public class ScoringListener implements GameListenerInterface {

    public float score = 0;
    
    @Override
    public void addedNewLine(Point start, Point target) {

    }

    @Override
    public void touchedPickup(PickUpGameObject pickup) {
        // Add some bonus points
        score += 500;
    }
    
    @Override
    public void finishedLine(Line line) {
        score += (float) ((line.calculateDistance() / line.origin.distanceTo(line.target)) * line.calculateDistance());
    }

    @Override
    public void touchedObstacle(ObstacleGameObject obstacle) {
        // Some negative points
        score -= 1000;
    }

}
