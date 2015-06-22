package fapra.magenta.data;

import fapra.magenta.data.collision.ICollisionObject;

public class Circle extends Point implements ICollisionObject {
    
    private float radius;
    
    public Circle(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
    }
    
    public Circle(float x, float y, int coordX, int coordY, float radius) {
        super(x, y, coordX, coordY);
        this.radius = radius;
    }
    
    public Circle(Point p, float radius) {
        this(p.x, p.y, p.coordX, p.coordY, radius);
    }

    public Circle(Circle circle) {
        this(circle.x, circle.y, circle.coordX, circle.coordY, circle.radius);
    }
    
    @Override
    public boolean hitCheck(Point p) {
        if (this.distanceTo(p) < radius) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
