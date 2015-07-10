package fapra.magenta.heatmap.data;

public class Point {

    private long id;
    private int lineID;
    private int xPx;
    private int yPx;
    
    public Point(String[] line) throws NumberFormatException {
        id = Long.parseLong(line[0]);
        lineID = Integer.parseInt(line[1]);
        xPx = Integer.parseInt(line[2]);
        yPx = Integer.parseInt(line[3]);
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
