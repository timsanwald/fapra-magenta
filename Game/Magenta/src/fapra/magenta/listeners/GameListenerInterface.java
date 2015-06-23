package fapra.magenta.listeners;

import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.obstacles.ObstacleGameObject;
import fapra.magenta.data.pickups.PickUpGameObject;

public interface GameListenerInterface {

    public void addedNewLine(Point start, Point target);
    
    public void touchedPickup(PickUpGameObject pickup);
    
    public void finishedLine(Line line);
    
    public void touchedObstacle(ObstacleGameObject obstacle);
}
