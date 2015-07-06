package fapra.magenta.data.pickups;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import fapra.magenta.data.GameObject;
import fapra.magenta.data.Point;
import fapra.magenta.data.collision.ICollisionObject;
import fapra.magenta.simulation.Simulation;

public abstract class PickUpGameObject extends GameObject implements ICollisionObject {

    public Point position;
    public float radius;
    
    public PickUpGameObject(Point position, float radius) {
        this.position = position;
        this.radius = radius;
    }
    
    @Override
    public boolean hitCheck(Point p) {
        return this.position.distanceTo(p) < radius;
    }

    public abstract boolean isAlive();

    public abstract Bitmap getDrawable(Context context);

    public abstract void update(float delta, Simulation sim);
}
