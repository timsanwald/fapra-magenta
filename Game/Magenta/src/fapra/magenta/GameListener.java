package fapra.magenta;

import android.app.Activity;
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
		view.setOnTouchListener(inputHandler);
	}

	@Override
	public void mainLoopIteration(Activity activity, SurfaceHolder surfaceHolder) {
		// Calculate deltatime
		thisTime = System.nanoTime();
		deltaTime = thisTime - lastTime;

		//TODO What to do with SoundManager? Adding to Simulation?

		// Process Input
		simulation.processInput(inputHandler);
		// Simulate World
		simulation.update(deltaTime);
		// Render World
		renderer.draw(surfaceHolder, simulation, deltaTime);
	}
}
