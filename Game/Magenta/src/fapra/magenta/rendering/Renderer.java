package fapra.magenta.rendering;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
		// search all lines to draw
		for(Line l : simulation.lines) {
		    drawLine(l, c);
		}
		drawCircle(simulation.startPoint, c, startPaint);
		drawCircle(simulation.targetPoint, c, targetPaint);
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
		c.drawPath(path, linePaint);
	}
	
	private Paint startPaint;
	private Paint targetPaint;
	
	private void drawCircle(Point p, Canvas c, Paint paint) {
	    
	    c.drawCircle(p.x, p.y, Simulation.epsilon, paint);
	}
}
