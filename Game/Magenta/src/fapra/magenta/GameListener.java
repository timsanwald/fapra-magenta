package fapra.magenta;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import fapra.magenta.input.InputHandler;
import fapra.magenta.rendering.Renderer;
import fapra.magenta.simulation.Simulation;
import fapra.magenta.sound.SoundManager;

public class GameListener implements GameListenerInterface {

	// Main-Loop variables
	private Renderer renderer;
	private Simulation simulation;
	private SoundManager soundManager;
	private InputHandler inputHandler;

	// Time variables
	private long lastTime;
	private long thisTime;
	private long deltaTime;

	@Override
	public void setup(Activity activity, View view, SurfaceHolder surfaceHolder) {
		inputHandler = new InputHandler();
		renderer = new Renderer();
		simulation = new Simulation();
		soundManager = new SoundManager();
		//TODO delete due to grid generation of start and target point
		simulation.width = activity.getWindowManager().getDefaultDisplay().getWidth();
		simulation.height = activity.getWindowManager().getDefaultDisplay().getHeight();
		simulation.setup();
		view.setOnTouchListener(inputHandler);
		print(activity);
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void mainLoopIteration(Activity activity, SurfaceHolder surfaceHolder) {
		// Calculate deltatime
		thisTime = System.currentTimeMillis();
		deltaTime = thisTime - lastTime;
		lastTime = thisTime;

		//TODO What to do with SoundManager? Adding to Simulation?

		// Process Input
		simulation.processInput(inputHandler);
		// Simulate World
		simulation.update(deltaTime);
		// Render World
		renderer.draw(surfaceHolder, simulation, deltaTime);
	}
	
	public void print(Activity activity) {
	    Point out = new Point(1080, 1920);
	    //activity.getWindowManager().getDefaultDisplay().getRealSize(out);
	    DisplayMetrics outMetrics = new DisplayMetrics();
	    activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
	    Log.e("x", ""+ ((out.x / outMetrics.xdpi)) * 2.54f);
	    Log.e("y", ""+ ((out.y / outMetrics.ydpi)) * 2.54f);
	    Log.e("d", outMetrics.toString());
	    Log.e("", out.toString());
	}
}
