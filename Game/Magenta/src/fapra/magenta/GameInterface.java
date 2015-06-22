package fapra.magenta;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.SurfaceHolder;
import android.view.View;

public interface GameInterface {
	/**
	 * Called when the game has to be initialized
	 * 
	 */
    void setup(Activity activity, View view, SurfaceHolder surfaceHolder, SharedPreferences preferences);

	/**
	 * Called when a new frame has to be rendered. Here all game related
	 * operations are performed, e.g. simulate the game world, process the input
	 * and so on.
	 */
	public void mainLoopIteration(Activity activity, SurfaceHolder surfaceHolder);
	
	public boolean isDone();

    void dispose();


}
