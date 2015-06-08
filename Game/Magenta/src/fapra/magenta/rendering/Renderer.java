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
		linePaint.setColor(Color.WHITE);
		linePaint.setStrokeWidth(3);
		linePaint.setStyle(Paint.Style.STROKE);
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
		c.drawRGB(255, 0, 0);
		//TODO search all lines to draw
		for(Line l : simulation.lines) {
		    drawLine(l, c);
		}
	}

	private void drawLine(LinkedList<Point> line, Canvas c) {
		// TODO define paint style
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);

		Path path = new Path();
		try {
		path.moveTo(line.getFirst().x, line.getFirst().y);
		} catch (NoSuchElementException e) {

		}
		for (Point p : line){
			path.lineTo(p.x, p.y);
			path.moveTo(p.x, p.y);
		}
		c.drawPath(path, paint);
	}
}
