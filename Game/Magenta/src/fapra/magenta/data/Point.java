package fapra.magenta.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Point {
	
	public float x;
	public float y;
	public long time;
	
	public int coordX = -1;
	public int coordY = -1;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
		this.time = System.currentTimeMillis();
	}
	
	public Point(float x, float y, int coordX, int coordY) {
		this(x, y);
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	@Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + ", time=" + time + ", coordX=" + coordX + ", coordY=" + coordY + "]";
    }

    public Point(Point p) {
	    this(p.x, p.y, p.coordX, p.coordY);
	}

    public double distanceTo(Point targetPoint) {
        if (targetPoint == null) {
            Log.d("Point", "target null");
            return Double.MAX_VALUE;
        }
        return Math.sqrt((targetPoint.x - this.x) * (targetPoint.x - this.x) 
                + (targetPoint.y - this.y) * (targetPoint.y - this.y));
    }

    public Object toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("xPx", x);
        json.put("yPx", y);
        json.put("timestamp", time);
        return json;
    }
}
