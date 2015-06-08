package fapra.magenta.input;

import java.util.concurrent.ConcurrentLinkedQueue;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import fapra.magenta.data.Point;

public class InputHandler implements OnTouchListener {

	public ConcurrentLinkedQueue<ConcurrentLinkedQueue<Point>> queues = new ConcurrentLinkedQueue<ConcurrentLinkedQueue<Point>>();
	public ConcurrentLinkedQueue<Point> current;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		// Start event
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    current = new ConcurrentLinkedQueue<Point> ();
			queues.add(current);
			addPoint(x, y);
			return true;
		}

		// Mid event
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			addPoint(x, y);
			return true;
		}

		//End event
		if (event.getAction() == MotionEvent.ACTION_UP) {
			addPoint(x, y);
			return true;
		}
		return false;
	}

	/**
	 * Adds the point to a FIFO queue.
	 * @param x
	 * @param y
	 */
	private void addPoint(float x, float y) {
		current.add(Point.generate(x, y));
	}

}
