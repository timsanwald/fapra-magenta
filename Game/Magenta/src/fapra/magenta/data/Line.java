package fapra.magenta.data;

import java.util.LinkedList;

import android.util.Log;

public class Line extends LinkedList<Point> {

    private static final long serialVersionUID = 5985312462403128639L;
    
    public Point origin = null;
    public Point target = null;
	
    public float calculateDistance() {
        float distance = 0f;
        Point last = this.getFirst();
        for (Point current : this) {
            distance += (float) last.distanceTo(current);
            last = current;
        }
        Log.e("Line", "Calculated Distance " + distance);
        return distance;
    }
    
}
