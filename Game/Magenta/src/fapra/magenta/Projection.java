package fapra.magenta;

import fapra.magenta.data.Point;
import fapra.magenta.target.GridManager;

public class Projection {
    
    public float shiftX = 0;
    public float shiftY = 0;
    
    public void addShift(float shiftX, float shiftY) {
        this.shiftX = this.shiftX + shiftX;
        this.shiftY = this.shiftY + shiftY;
    }
    
    public void addShift(int shiftGridX, int shiftGridY, GridManager manager) {
        this.shiftX += shiftGridX * manager.pointSize * 2;
        this.shiftY += shiftGridY * manager.pointSize * 2;
    }
    
    public void convertToPixels(Point p) {
        p.x = p.x - shiftX;
        p.y = p.y - shiftY;
    }
    
    public void convertFromPixels(Point p) {
        p.x = p.x + shiftX;
        p.y = p.y + shiftY;
    }
    
    public float toPixelsX(float x) {
        return x + shiftX;
    }
    
    public float toPixelsY(float y) {
        return y + shiftY;
    }
}
