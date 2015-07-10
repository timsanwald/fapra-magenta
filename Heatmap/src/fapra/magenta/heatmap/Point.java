package fapra.magenta.heatmap;

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
            return Double.MAX_VALUE;
        }
        return Math.sqrt((targetPoint.x - this.x) * (targetPoint.x - this.x) 
                + (targetPoint.y - this.y) * (targetPoint.y - this.y));
    }

}
