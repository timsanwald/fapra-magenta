package fapra.magenta.data;

import java.util.LinkedList;
import java.util.List;

public class Point {
	
	private final static List<Point> freedPoints= new LinkedList<Point>();
	
	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static Point generate(float x, float y) {
		if (!freedPoints.isEmpty()) {
			try {
				Point p = freedPoints.remove(0);
				p.x = x;
				p.y = y;
				return p;
			} catch (IndexOutOfBoundsException e) {
				return new Point(x, y);
			}
		}
		return new Point(x, y);
	}
	
	public static void free(Point p) {
		freedPoints.add(p);
	}
	
	public static void clear() {
		freedPoints.clear();
	}

}
