package fapra.magenta.input;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import fapra.magenta.data.Point;

public class InputHandler implements OnTouchListener {

	public Point p = null;
	public boolean isTouched = false;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		isTouched = false;
		p = null;
		// Start event
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    p = new Point(x, y);
		    isTouched = true;
			return true;
		}
		
		// Mid event
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
		    p = new Point(x, y);
		    isTouched = true;
			return true;
		}

		//End event
		if (event.getAction() == MotionEvent.ACTION_UP) {
		    p = new Point(x, y);
		    isTouched = false;
			return true;
		}
		return v.performClick();
	}
}
