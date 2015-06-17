package fapra.magenta.input;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import fapra.magenta.data.Point;

public class InputHandler implements OnTouchListener {

	public Point p = null;
	public int eventID = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		eventID = 0;
		p = null;
		// Start event
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    p = new Point(x, y);
		    eventID = 1;
			return true;
		}
		
		// Mid event
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
		    p = new Point(x, y);
		    eventID = 2;
			return true;
		}

		//End event
		if (event.getAction() == MotionEvent.ACTION_UP) {
		    p = new Point(x, y);
		    eventID = 3;
			return true;
		}
		return v.performClick();
	}

    public void reset() {
        p = null;
        eventID = 0;
    }
}
