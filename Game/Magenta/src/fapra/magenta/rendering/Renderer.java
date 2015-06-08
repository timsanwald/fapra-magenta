package fapra.magenta.rendering;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

public class Renderer {
	public void draw(SurfaceHolder surfaceHolder) {
		Canvas c = null;
		try {
			c = surfaceHolder.lockCanvas();
			drawFrame(c);
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
	
	private void drawFrame(Canvas c) {
		c.drawRGB(255, 0, 0);
	}
}
