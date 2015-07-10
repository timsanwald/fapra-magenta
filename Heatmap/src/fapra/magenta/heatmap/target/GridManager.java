package fapra.magenta.heatmap.target;

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
	public int borderXPx;
	public int borderYPx;
	
	public int pointSize;

	public GridManager(int screenXPx, int screenYPx, double xdpi, double ydpi) {
		this.screenXPx = screenXPx;
		this.screenYPx = screenYPx;

		this.screenXCm = (this.screenXPx / xdpi) * 2.54;
		this.screenYCm = (this.screenYPx / ydpi) * 2.54;

		// setup the grid
		int usedXPx = (int) (((int) (this.screenXCm / 2.54)) * xdpi);
		int usedYPx = (int) (((int) (this.screenYCm / 2.54)) * ydpi);

		this.gridXPx = (int) (usedXPx / ((int) this.screenXCm));
		this.gridYPx = (int) (usedYPx / ((int) this.screenYCm));
		// calculate the borders
		this.borderXPx = (int) ((this.screenXPx - (gridXPx * (int) screenXCm)) / 2);
		this.borderYPx = (int) ((this.screenYPx - (gridYPx * (int) screenYCm)) / 2);

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

	public int targetToPxY(int gridY) {
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
