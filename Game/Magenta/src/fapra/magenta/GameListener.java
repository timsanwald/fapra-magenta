package fapra.magenta;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import fapra.magenta.data.Upgrades;
import fapra.magenta.input.InputHandler;
import fapra.magenta.rendering.Renderer;
import fapra.magenta.simulation.Simulation;
import fapra.magenta.sound.ISoundManager;
import fapra.magenta.sound.NullSoundManager;
import fapra.magenta.sound.SoundManager;
import fapra.magenta.target.GridManager;
import fapra.magenta.target.TargetGenerator;

public class GameListener implements GameListenerInterface {

	// Main-Loop variables
	private Renderer renderer;
	private Simulation simulation;
	private ISoundManager soundManager;
	private InputHandler inputHandler;
	private Upgrades upgrades;
	private TargetGenerator targetGenerator;

	// Time variables
	private long lastTime;
	private long thisTime;
	private long deltaTime;

	@Override
	public void setup(Activity activity, View view, SurfaceHolder surfaceHolder) {
	    upgrades = new Upgrades();
	    upgrades.load(activity);
		inputHandler = new InputHandler();
		renderer = new Renderer();
		targetGenerator = new TargetGenerator(activity);
		simulation = new Simulation(targetGenerator);
		soundManager = new NullSoundManager(activity);
		//TODO delete due to grid generation of start and target point
		simulation.width = activity.getWindowManager().getDefaultDisplay().getWidth();
		simulation.height = activity.getWindowManager().getDefaultDisplay().getHeight();
		simulation.setup(upgrades, soundManager);
		
		view.setOnTouchListener(inputHandler);
		print(activity);
		lastTime = System.currentTimeMillis();
	}

	private boolean isDone = false;
	
	@Override
	public void mainLoopIteration(Activity activity, SurfaceHolder surfaceHolder) {
		// Calculate deltatime
		thisTime = System.currentTimeMillis();
		deltaTime = thisTime - lastTime;
		lastTime = thisTime;

		//TODO What to do with SoundManager? Adding to Simulation?
		if (!simulation.isDone) {
		      // Process Input
	        simulation.processInput(inputHandler);
	        // Simulate World
	        simulation.update(deltaTime);
	        // Render World
	        renderer.draw(surfaceHolder, simulation, deltaTime);
		} else {
		    this.isDone = true;
		    this.dispose();
		}
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

    @Override
    public void dispose() {
        soundManager.dispose();
        renderer.dispose();
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
