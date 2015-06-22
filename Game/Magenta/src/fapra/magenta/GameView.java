package fapra.magenta;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView implements Callback {

	private final Activity activity;
	private SurfaceHolder surfaceHolder;
	
	public GameView(Activity activity, AttributeSet attrs, GameInterface gameListener) {
		super(activity, attrs);
		this.activity = activity;
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
        this.gameListener = gameListener;
        this.gameListener.setup(activity, this, surfaceHolder, PreferenceManager.getDefaultSharedPreferences(activity));
	}

	private boolean isActive = true;
	private GameInterface gameListener;
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Thread gameLoop=new Thread() {
	    	public void run() {
	            while (isActive) {
	            	gameListener.mainLoopIteration(activity, surfaceHolder);
	    		}
	    	}
	    };
		gameLoop.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		surfaceHolder=holder;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isActive=false;
	    activity.finish();
	}
	
}