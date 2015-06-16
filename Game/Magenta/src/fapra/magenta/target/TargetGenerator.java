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

	/**
     * 0: top-down; 1 left-right; 2 bottom up; 3 right-left
     */
	
	private final float RATE_HIGH = 0.8f;
	private final float RATE_MID = 0.65f;
	private final float RATE_LOW = 0.4f;
	private final float PROBABILITY_MID = 0.6f;
	private final float PROBABILITY_LOW = 0.3f;
    public int shiftX(Point startPoint) {
        int shift = 0;
        int occupied = 0;
        switch(direction) {
        case 1:
            occupied = startPoint.coordX;
            break;
        case 3:
            occupied = gridManager.getGridSizeX() - startPoint.coordX;
            break;
        default:
            return 0;
        }
        float rate = ((float) occupied) / ((float) (gridManager.getGridSizeX()));
        Random r = new Random();
        float rand = r.nextFloat();
        if (rate > RATE_HIGH) {
            shift = r.nextInt(occupied);
            if (shift < 1) {
                shift = 1;
            }
        } else if (rate > RATE_MID && rand < PROBABILITY_MID) {
            shift = r.nextInt(occupied);
        } else if (rate > RATE_LOW && rand < PROBABILITY_LOW) {
            shift = r.nextInt(occupied);
        }
        
        switch(direction) {
        case 1:
            startPoint.coordX = startPoint.coordX - shift;
            return shift;
        case 3:
            startPoint.coordX = startPoint.coordX + shift;
            return -shift;
        }
        return shift;
    }
    
    public int shiftY(Point startPoint) {
        int shift = 0;
        int occupied = 0;
        switch(direction) {
        case 0:
            occupied = startPoint.coordY;
            break;
        case 2:
            occupied = gridManager.getGridSizeY() - startPoint.coordY;
            break;
        default:
            return 0;
        }
        float rate = ((float) occupied) / ((float) (gridManager.getGridSizeY()));
        Random r = new Random();
        float rand = r.nextFloat();
        if (rate > RATE_HIGH) {
            shift = r.nextInt(occupied);
            if (shift < 1) {
                shift = 1;
            }
        } else if (rate > RATE_MID && rand < PROBABILITY_MID) {
            shift = r.nextInt(occupied);
        } else if (rate > RATE_LOW && rand < PROBABILITY_LOW) {
            shift = r.nextInt(occupied);
        }
        
        switch(direction) {
        case 0:
            startPoint.coordY = startPoint.coordY - shift;
            return shift;
        case 2:
            startPoint.coordY = startPoint.coordY + shift;
            return -shift;
        }
        return shift;
    }
}
