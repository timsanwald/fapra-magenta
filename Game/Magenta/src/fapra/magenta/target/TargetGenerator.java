package fapra.magenta.target;

import java.util.Random;

import android.app.Activity;
import android.util.Log;
import fapra.magenta.data.Point;

public class TargetGenerator {
	public GridManager gridManager;
	/**
	 * 0: top-down; 1 left-right; 2 bottom up; 3 right-left
	 */
	private int direction;
	private int mode = 0;
	
	public TargetGenerator(Activity activity) {
		
		this.gridManager = new GridManager(activity);
	}

	public Point generateTarget(Point startPoint) {
		// TODO add mode detection
		return this.generateRandomTargetPoint(startPoint);
	}
	
	public Point generateStartPoint() {
		// TODO add mode detection
		return this.generateRandomStartPoint();
	}

	public int getDirection() {
		return this.direction;
	}

	public boolean shift(Point startPoint) {
		// TODO decide if we need to moove the camera based on the grid size and the current point
		return false;
	}
	
	private Point generateRandomStartPoint() {
		Random r = new Random();
		this.direction = r.nextInt(4);
		
		int gridX;
		int gridY;
		
		if(this.direction == 0) {
			gridY = 0;
			gridX = r.nextInt(this.gridManager.getGridSizeX() + 1);
			
			return new Point(this.gridManager.targetToPxX(gridX), this.gridManager.targetToPxY(gridY), gridX, gridY);
		}
		
		if(this.direction == 1) {
			gridX = 0;
			gridY = r.nextInt(this.gridManager.getGridSizeY() + 1);
			
			return new Point(this.gridManager.targetToPxX(gridX), this.gridManager.targetToPxY(gridY), gridX, gridY);
		}
		
		if(this.direction == 2) {
			gridY = this.gridManager.getGridSizeY();
			gridX = r.nextInt(this.gridManager.getGridSizeX() + 1);
			
			return new Point(this.gridManager.targetToPxX(gridX), this.gridManager.targetToPxY(gridY), gridX, gridY);
		}
		
		if(this.direction == 3) {
			gridX = this.gridManager.getGridSizeX();
			gridY = r.nextInt(this.gridManager.getGridSizeY() + 1);
			
			return new Point(this.gridManager.targetToPxX(gridX), this.gridManager.targetToPxY(gridY), gridX, gridY);
		}
		
		// when we reach this s.th. went terribly wrong, because the scroll direction is invalid...
		return null;
	}
	
	private Point generateRandomTargetPoint(Point startPoint) {
		Point targetPoint;
		int gridX;
		int gridY;
		int availableRange;
		Random r = new Random();
		
		if(direction == 0 ) {
			availableRange = this.gridManager.getGridSizeY() - startPoint.coordY;
		} else if(direction == 1) {
			availableRange = this.gridManager.getGridSizeX() - startPoint.coordX;
		} else if(direction == 2) {
			availableRange = startPoint.coordY;
		} else {
			availableRange = startPoint.coordX;
		}
		
		// TODO remove the system.exit part
		if(availableRange == 0) {
			Log.e("shut down", "scroll would have been required");
			System.exit(0);
		}
		
		if(direction == 0) {
			gridY = startPoint.coordY + 1 + r.nextInt(availableRange);
			gridX = r.nextInt(this.gridManager.getGridSizeX() + 1);
		} else if(direction == 1) {
			gridX = startPoint.coordX + 1 + r.nextInt(availableRange);
			gridY = r.nextInt(this.gridManager.getGridSizeY() + 1);
		} else if(direction == 2) {
			gridY = startPoint.coordY - 1 - r.nextInt(availableRange);
			gridX = r.nextInt(this.gridManager.getGridSizeX() + 1);
		} else {
			gridX = startPoint.coordX - 1 - r.nextInt(availableRange);
			gridY = r.nextInt(this.gridManager.getGridSizeY());
		}
		
		return new Point(this.gridManager.targetToPxX(gridX), this.gridManager.targetToPxY(gridY), gridX, gridY);
	}
}
