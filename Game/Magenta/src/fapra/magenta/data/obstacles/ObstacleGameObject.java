package fapra.magenta.data.obstacles;

import fapra.magenta.data.GameObject;
import fapra.magenta.data.Point;
import fapra.magenta.data.collision.ICollisionObject;

public abstract class ObstacleGameObject extends GameObject implements ICollisionObject {

    @Override
    public abstract boolean hitCheck(Point p);
}
