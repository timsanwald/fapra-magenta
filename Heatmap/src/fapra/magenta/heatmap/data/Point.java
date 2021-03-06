package fapra.magenta.heatmap.data;

import com.eclipsesource.json.JsonObject;

public class Point {

    private long id;
    private int lineID;
    private int xPx;
    private int yPx;
    
    
    public Point(long id, int lineID, int xPx, int yPx) {
        super();
        this.id = id;
        this.lineID = lineID;
        this.xPx = xPx;
        this.yPx = yPx;
    }

    public Point(String[] line) throws NumberFormatException {
        id = Long.parseLong(line[0]);
        lineID = Integer.parseInt(line[1]);
        xPx = Integer.parseInt(line[2]);
        yPx = Integer.parseInt(line[3]);
    }

    public Point(JsonObject value) {
        this.lineID = Integer.parseInt(value.getString("lineId", "-1"));
        this.xPx = Integer.parseInt(value.getString("xPx", "-1"));
        this.yPx = Integer.parseInt(value.getString("yPx", "-1"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }

    public int getxPx() {
        return xPx;
    }

    public void setxPx(int xPx) {
        this.xPx = xPx;
    }

    public int getyPx() {
        return yPx;
    }

    public void setyPx(int yPx) {
        this.yPx = yPx;
    }
    
}
