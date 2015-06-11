package fapra.magenta.data;

public class Point {
	
	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

    public double distanceTo(Point targetPoint) {
        return Math.sqrt((targetPoint.x - this.x) * (targetPoint.x - this.x) 
                + (targetPoint.y - this.y) * (targetPoint.y - this.y));
    }

}
