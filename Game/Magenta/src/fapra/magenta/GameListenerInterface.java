package fapra.magenta;

import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.View;

public interface GameListenerInterface {
	/**
	 * Called when the game has to be initialized
	 * 
	 * @param activity
	 *            The GameActivity this listener is attached to
	 * @param gl
	 *            The GL
	 */
	public void setup(Activity activity, View view, SurfaceHolder surfaceHolder);

	/**
	 * Called when a new frame has to be rendered. Here all game related
	 * operations are performed, e.g. simulate the game world, process the input
	 * and so on.
	 * 
	 * @param activity
	 * @param surfaceHolder
	 */
	public void mainLoopIteration(Activity activity, SurfaceHolder surfaceHolder);
}
