package fapra.magenta.data.collision;

import fapra.magenta.data.Point;

public interface ICollisionObject {
    
    /**
     * Checks if this object is colliding with the given Point.
     * @param p Point to check with
     * @return true if the point hits this object, false otherwise.
     */
    public abstract boolean hitCheck(Point p);
}
