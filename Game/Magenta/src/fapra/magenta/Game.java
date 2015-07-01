package fapra.magenta;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import fapra.magenta.apiClient.Client;
import fapra.magenta.audio.music.IMusicManager;
import fapra.magenta.audio.music.MusicManager;
import fapra.magenta.audio.music.NullMusicManager;
import fapra.magenta.audio.sound.ISoundManager;
import fapra.magenta.audio.sound.NullSoundManager;
import fapra.magenta.audio.sound.SoundManager;
import fapra.magenta.data.save.SaveGame;
import fapra.magenta.input.InputHandler;
import fapra.magenta.rendering.Renderer;
import fapra.magenta.simulation.Simulation;
import fapra.magenta.target.TargetGenerator;

public class Game implements GameInterface {

	// Main-Loop variables
	private Renderer renderer;
	private Simulation simulation;
	private ISoundManager soundManager;
	private IMusicManager musicManager;
	private InputHandler inputHandler;
	private SaveGame saveGame;
	private TargetGenerator targetGenerator;
	private Client apiClient;
	private Thread apiClientThread;
	
	// Time variables
	private long lastTime;
	private long thisTime;
	private long deltaTime;

    @Override
	public void setup(Activity activity, View view, SurfaceHolder surfaceHolder, SharedPreferences preferences) {
	    saveGame = new SaveGame();
	    saveGame.load(activity);
		inputHandler = new InputHandler();
		targetGenerator = new TargetGenerator(activity);
	    renderer = new Renderer(targetGenerator.gridManager.pointSize);
		simulation = new Simulation(targetGenerator, activity);
		apiClient = new Client(activity);
		
		apiClientThread = new Thread(apiClient);
		apiClientThread.start();
		
		if (preferences.getBoolean(activity.getString(R.string.preference_sound_key), true)) {
	       soundManager = new SoundManager(activity);
		} else {
		    soundManager = new NullSoundManager(activity);
		}
        if (preferences.getBoolean(activity.getString(R.string.preference_music_key), true)) {
            musicManager = new MusicManager(activity);
        } else {
            musicManager = new NullMusicManager(activity);
        }
        
		simulation.setup(saveGame, soundManager);
		
		view.setOnTouchListener(inputHandler);
		print(activity);
		lastTime = System.currentTimeMillis();
	}

	private boolean isDone = false;
	private boolean isGameOverShown = false;
	@Override
	public void mainLoopIteration(final Activity activity, SurfaceHolder surfaceHolder) {
		// Calculate deltatime
		thisTime = System.currentTimeMillis();
		deltaTime = thisTime - lastTime;
		lastTime = thisTime;

		if (!simulation.isGameOver) {
		      // Process Input
	        simulation.processInput(inputHandler);
	        // Simulate World
	        simulation.update(deltaTime);
	        // Render World
	        renderer.draw(surfaceHolder, simulation, deltaTime);
		} else if (!isGameOverShown){
		    //TODO Switch to gameOverScreen
		    isGameOverShown = true;
		    if (activity instanceof GameActivity) {
		        Log.d("Game", "Open GameOver screen");
		        activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.onBackPressed();
                        ((GameActivity) activity).replaceMainFragment(new GameOverScreen(simulation));
                    }
                });
		    }
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
        musicManager.dispose();
        renderer.dispose();
        apiClientThread.interrupt();
        isDone = true;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
    
}
