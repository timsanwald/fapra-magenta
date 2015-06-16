package fapra.magenta.rendering;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.SurfaceHolder;
import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.simulation.Simulation;

public class Renderer {

	public Renderer() {
		linePaint = new Paint();
		linePaint.setColor(Color.BLUE);
		linePaint.setStrokeWidth(10);
		linePaint.setStyle(Paint.Style.STROKE);
		
		followerPaint = new Paint();
		followerPaint.setColor(Color.GRAY);
		followerPaint.setStrokeWidth(15);
		followerPaint.setStyle(Paint.Style.STROKE);
		//followerPaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
		
        startPaint = new Paint();
        startPaint.setColor(Color.BLUE);
        startPaint.setStrokeWidth(4);
        startPaint.setStyle(Paint.Style.FILL);
        
        targetPaint = new Paint();
        targetPaint.setColor(Color.RED);
        targetPaint.setStrokeWidth(4);
        targetPaint.setStyle(Paint.Style.FILL);
	}

	public void draw(SurfaceHolder surfaceHolder, Simulation simulation, long delta) {
		Canvas c = null;
		try {
			c = surfaceHolder.lockCanvas();
			drawFrame(c, simulation);
		} catch (Exception e) {
			e.printStackTrace();
			// throw(new Error(e));
		} finally {
			// do this in a finally so that if an exception is thrown
			// during the above, we don't leave the Surface in an
			// inconsistent state
			if (c != null)
				surfaceHolder.unlockCanvasAndPost(c);
		}
	}

	private Paint linePaint;
	private void drawFrame(Canvas c, Simulation simulation) {
	    c.drawColor(Color.BLACK);
	    
		// Render path and follower
		for(Line l : simulation.lines) {
		    drawLine(l, c);
		}
		
		// Draw current line drawing
		if (simulation.currentLine != null) {
		    drawLine(simulation.currentLine, c);
		}
		
		for (int i = 1; i < simulation.lines.size(); i++) {
		    connectLines(simulation.lines.get(i-1), simulation.lines.get(i), c);
		}
		
		// Draw follower
		drawLineDistance(c, simulation);
		
	    // Render start and target point
        drawCircle(simulation.startPoint, c, startPaint, simulation.targetGenerator.gridManager.pointSize);
        drawCircle(simulation.targetPoint, c, targetPaint, simulation.targetGenerator.gridManager.pointSize);
	}

	
	private Point followerPoint = new Point(200, 200);
	private Paint followerPaint = new Paint();
	private void drawLineDistance(Canvas c, Simulation sim) {
	    float pathDistance = 0;
	    float currDistance = 0;
	    Point last;
	    try {
	        last = sim.lines.getFirst().getFirst();
	    } catch (NoSuchElementException e) {
	        return;
	    }
	    
	    Path path = new Path();
	    path.moveTo(last.x, last.y);
	    boolean finishedPath = false;
	    
	    for (int i = 0; i < sim.lines.size(); i++) {
            for(Point p : sim.lines.get(i)) {
                currDistance = (float) p.distanceTo(last);
                if (pathDistance + currDistance > sim.follower) {
                    // between current point and last is the follower
                   float part = (sim.follower - pathDistance);
                   followerPoint.x = last.x + ((p.x - last.x) / currDistance) * part;
                   followerPoint.y = last.y + ((p.y - last.y) / currDistance) * part;
                   path.lineTo(followerPoint.x, followerPoint.y);
                   c.drawPath(path, followerPaint);
                   return;
                } else {
                    path.lineTo(p.x, p.y);
                    path.moveTo(p.x, p.y);
                    
                }
                pathDistance += currDistance;
                last = p;
            }
        }
    }
	
	private void drawLine(LinkedList<Point> line, Canvas c) {
		Path path = new Path();
		try {
		path.moveTo(line.getFirst().x, line.getFirst().y);
		} catch (NoSuchElementException e) {

		}
		for (Point p : line){
			path.lineTo(p.x, p.y);
			path.moveTo(p.x, p.y);
		}
		Log.d("Renderer", "" + line.size());
		c.drawPath(path, linePaint);
	}
	
	private void connectLines(LinkedList<Point> start, LinkedList<Point> target, Canvas c) {
	    if (start.isEmpty() || target.isEmpty()) {
	        return;
	    }
	    c.drawLine(start.getLast().x, start.getLast().y, target.getFirst().x, target.getFirst().y, linePaint);
	}
	
	private Paint startPaint;
	private Paint targetPaint;
	
	private void drawCircle(Point p, Canvas c, Paint paint, int size) {
	    c.drawCircle(p.x, p.y, size, paint);
	}
	
	public void dispose() {
	    // clear eventual used resources
	}
}
