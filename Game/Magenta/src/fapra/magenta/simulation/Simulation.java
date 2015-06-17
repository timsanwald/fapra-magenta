package fapra.magenta.simulation;

import java.util.LinkedList;

import android.util.Log;
import fapra.magenta.Projection;
import fapra.magenta.audio.sound.ISoundManager;
import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.Upgrades;
import fapra.magenta.input.InputHandler;
import fapra.magenta.target.TargetGenerator;

public class Simulation {

	/**
	 * Holds all drawn lines in this game. Initializes with one line. The last
	 * line in the list is the current one.
	 */
	public LinkedList<Line> lines;
	public Line currentLine;

	public Point startPoint;
	public Point targetPoint;

	public TargetGenerator targetGenerator;
	
	public Projection projection;

	/**
	 * Defines when this simulation is finished and can be closed.
	 */
	public boolean isGameOver = false;
	
	private ISoundManager soundManager;

	public Simulation(TargetGenerator targetGenerator) {
	    projection = new Projection();
		lines = new LinkedList<Line>();

		this.targetGenerator = targetGenerator;
	}
	
    public void setup(Upgrades upgrades, ISoundManager soundManager) {
        targetPoint = this.targetGenerator.generateStartPoint();
        setNewTarget();
        while (currentDistance < upgrades.followerStartDistance) {
            currentLine = new Line();
            currentLine.add(startPoint);
            currentLine.add(targetPoint);
            Log.e("Setup", "1");
            addCurrentLine();
            Log.e("Setup", "2");
            setNewTarget();
            Log.e("Setup", "3");
        }
        
        followerSpeed = upgrades.followerStartSpeed;
        followerSpeedIncrement = upgrades.followerIncrement;
        
        this.soundManager = soundManager;
    }

    public void addCurrentLine() {
        currentLine.origin = startPoint;
        currentLine.target = targetPoint;
        if (!lines.isEmpty()) {
            currentDistance += lines.getLast().getLast().distanceTo(currentLine.getFirst());
        }
        currentDistance += currentLine.calculateDistance();
        Log.e("Setup", "5");
        lines.add(currentLine);
        Log.e("AddCurrentLine", "currentDistance=" + currentDistance);
        currentLine = null;
    }
    
	// Update the environment
	public void update(float delta) {
		// move the mail xD

		followerSpeed = followerSpeed + followerSpeedIncrement;
		follower = follower + (followerSpeed / 1000) * (delta);
		
		if (checkGameOver()) {
		    isGameOver = true;
		}
	}

	boolean isTouchedLast = false;
	boolean isValidLine = false;

	public boolean checkGameOver() {
	    return follower >= currentDistance;
	}
	
	public void processInput(InputHandler inputHandler) {
		// Process the given input from last iteration
		// Create new Lines and so stuff, touch gestures are included in the
		// inputHandler
	    
		if (inputHandler.p == null) {
			return;
		} else {
		    projection.convertFromPixels(inputHandler.p);
		}
		
		if (inputHandler.eventID == 1) {
			// First down touch
			currentLine = new Line();
			currentLine.add(inputHandler.p);
			if (isInStartRange(inputHandler.p)) {
				isValidLine = true;
				soundManager.playStartLineSound();
			}
		} else if (inputHandler.eventID == 2 && currentLine != null) {
			// Move
			currentLine.add(inputHandler.p);
		} else if (inputHandler.eventID == 3 && currentLine != null) {
			// Action Up
			if (!isInTargetRange(inputHandler.p) || !isValidLine) {
				currentLine.clear();
				this.soundManager.playMissedTargetSound();
			} else {
				currentLine.add(inputHandler.p);
				addCurrentLine();
				setNewTarget();
				isValidLine = false;
				this.soundManager.playFinishedLineSound();
			}
		}
		inputHandler.reset();
	}

	/**
	 * Called, when a line is drawn from start to target node. Should generate a
	 * new target and probably shift the camera.
	 */
	private void setNewTarget() {
		startPoint = new Point(targetPoint);
        projection.addShift(this.targetGenerator.shiftX(startPoint), 
                this.targetGenerator.shiftY(startPoint),
                this.targetGenerator.gridManager);
		this.targetPoint = this.targetGenerator.generateTarget(startPoint);
		projection.convertFromPixels(this.targetPoint);
		Log.d("Simulation", "new start: " + startPoint);
        Log.d("Simulation", "new Target: " + targetPoint);
	}

	/**
	 * Checks if the given Point is in the range of the current start node.
	 * 
	 * @param point
	 *            Point to check
	 * @return True, if the distance between the given Point and the start node
	 *         is less than epsilon, false otherwise.
	 */
	private boolean isInStartRange(Point p) {
		if (p.distanceTo(startPoint) < this.targetGenerator.gridManager.pointSize) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the given Point is in the range of the current target.
	 * 
	 * @param point
	 *            Point to check
	 * @return True, if the distance between the given Point and the target
	 *         Point is less than epsilon, false otherwise.
	 */
	private boolean isInTargetRange(Point point) {
		if (point.distanceTo(targetPoint) < this.targetGenerator.gridManager.pointSize) {
			return true;
		}
		return false;
	}

	public float currentDistance = 0;
	public float follower = 0;
	public float followerSpeed = 60;
	public float followerSpeedIncrement = 0.2f;
}
