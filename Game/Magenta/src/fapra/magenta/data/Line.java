package fapra.magenta.data;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Line extends LinkedList<Point> {

    private static final long serialVersionUID = 5985312462403128639L;

    public Point origin = null;
    public Point target = null;
    public boolean isResearchable = false;
    public boolean isLandscape = false;
    private int scrollDirection;
    
    public Line(int scrollDirection, boolean isLandscape) {
        this.scrollDirection = scrollDirection;
    }
    
    
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

    public JSONObject toJSON() throws JSONException {
        
        JSONObject json = new JSONObject();

        json.put("startGridX", origin.coordX);
        json.put("startGridY", origin.coordY);

        json.put("endGridX", target.coordX);
        json.put("endGridY", target.coordY);

        json.put("startPxX", origin.x);
        json.put("startPxY", origin.y);

        json.put("endPxX", target.x);
        json.put("endPxY", target.y);

        json.put("startTime", this.getFirst().time);
        json.put("endTime", this.getLast().time);

        json.put("isLandscape", isLandscape);
        json.put("scrollDirection", scrollDirection);
        
        // Add points
        JSONArray points = new JSONArray();
        for (Point p : this) {
            points.put(p.toJSON());
        }
        json.put("points", points);
        
        return json;
    }
}
