package fapra.magenta.target;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class GridManager {
	// screen in cm
	private double screenXCm;
	private double screenYCm;

	// screen in px
	public int screenXPx;
	public int screenYPx;

	// grid in px
	private int gridXPx;
	private int gridYPx;

	// borders to center game screen
	private int borderXPx;
	private int borderYPx;
	
	public int pointSize;

	public GridManager(Activity activity) {
		// get screen sizes in px and cm
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		this.screenXPx = displayMetrics.widthPixels;
		this.screenYPx = displayMetrics.heightPixels;

		Log.d("x pixel", this.screenXPx + "");
		Log.d("y pixel", this.screenYPx + "");

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		this.screenXCm = (this.screenXPx / dm.xdpi) * 2.54;
		this.screenYCm = (this.screenYPx / dm.ydpi) * 2.54;
		Log.d("x cm", "" + this.screenXCm);
		Log.d("y cm", "" + this.screenYCm);

		// setup the grid
		int usedXPx = (int) (((int) (this.screenXCm / 2.54)) * dm.xdpi);
		int usedYPx = (int) (((int) (this.screenYCm / 2.54)) * dm.ydpi);

		this.gridXPx = (int) (usedXPx / ((int) this.screenXCm));
		this.gridYPx = (int) (usedYPx / ((int) this.screenYCm));

		Log.d("gridXPx", "" + this.gridXPx);
		Log.d("gridYpx", "" + this.gridYPx);

		// calculate the borders
		this.borderXPx = (int) ((this.screenXPx - (gridXPx * (int) screenXCm)) / 2);
		this.borderYPx = (int) ((this.screenYPx - (gridYPx * (int) screenYCm)) / 2);

		Log.d("borderX", "" + this.borderXPx);
		Log.d("borderY", "" + this.borderYPx);
		
		// calculate point size
		if(this.gridXPx > this.gridYPx) {
			this.pointSize = (int) (0.5 * this.gridXPx);
		} else {
			this.pointSize = (int) (0.5 * this.gridYPx);
		}
	}
	
	public int getGridSizeX() {
		return (int) this.screenXCm;
	}
	
	public int getGridSizeY() {
		return (int) this.screenYCm;
	}

	public int targetToPxX(int gridX) {
		int px = this.borderXPx + (gridX * this.gridXPx);
		if(px - (0.5 * this.pointSize) < 0) {
			int moove = ((int) (px - (0.5 * this.pointSize)) * -1) + 1;
			px = px + moove;
		} else if(px + (0.5 * this.pointSize) > this.screenXPx) {
			int moove = (((int) (px + (0.5) * this.pointSize)) + 1) - this.screenXPx;
			px = px - moove;
		}
		
		return px;
	}
	
	public float targetToPxY(int gridY) {
		int px = this.borderYPx + (gridY * this.gridYPx);
		if(px - (0.5 * this.pointSize) < 0) {
			int moove = ((int) (px - (0.5 * this.pointSize)) * -1) + 1;
			px = px + moove;
		} else if(px + (0.5 * this.pointSize) > this.screenYPx) {
			int moove = (((int) (px + (0.5) * this.pointSize)) + 1) - this.screenYPx;
			px = px - moove;
		}
		
		return px;
	}
}
