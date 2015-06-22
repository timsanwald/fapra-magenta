package fapra.magenta.data.pickups;

import fapra.magenta.data.GameObject;
import fapra.magenta.data.Point;
import fapra.magenta.data.collision.ICollisionObject;

public abstract class PickUpGameObject extends GameObject implements ICollisionObject {

    public Point position;
    public float radius;
    
    public PickUpGameObject(Point position, float radius) {
        this.position = position;
        this.radius = radius;
    }
    
    @Override
    public boolean hitCheck(Point p) {
        return this.position.distanceTo(position) < radius;
    }

    public abstract void update(float delta);

    public abstract boolean isAlive();
}
