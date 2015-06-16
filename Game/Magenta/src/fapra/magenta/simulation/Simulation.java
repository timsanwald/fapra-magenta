package fapra.magenta.simulation;

import java.util.LinkedList;
import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.Upgrades;
import fapra.magenta.input.InputHandler;
import fapra.magenta.sound.ISoundManager;
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

	/**
	 * Defines when this simulation is finished and can be closed.
	 */
	public boolean isDone;
	
	private ISoundManager soundManager;

	public Simulation(TargetGenerator targetGenerator) {
		lines = new LinkedList<Line>();

		this.targetGenerator = targetGenerator;

		targetPoint = this.targetGenerator.generateStartPoint();

		if (this.targetGenerator.getDirection() == 0) {
			startPoint = new Point(this.targetGenerator.gridManager.screenXPx / 2, 0);
		}

		if (this.targetGenerator.getDirection() == 1) {
			startPoint = new Point(0, this.targetGenerator.gridManager.screenYPx / 2);
		}
		
		if(this.targetGenerator.getDirection() == 2) {
			startPoint = new Point(this.targetGenerator.gridManager.screenXPx / 2, this.targetGenerator.gridManager.screenYPx);
		}
		
		if (this.targetGenerator.getDirection() == 3) {
			startPoint = new Point(this.targetGenerator.gridManager.screenXPx, this.targetGenerator.gridManager.screenYPx / 2);
		}
	}
	
    public void setup(Upgrades upgrades, ISoundManager soundManager) {
        Line startLine = new Line();
        startLine.add(startPoint);
        startLine.add(targetPoint);
        lines.add(startLine);
        setNewTarget();
        
        followerSpeed = upgrades.followerStartSpeed;
        followerSpeedIncrement = upgrades.followerIncrement;
        
        this.soundManager = soundManager;
    }

	// Update the environment
	public void update(float delta) {
		// TODO implement updating simulation
		// move the mail xD

		followerSpeed = followerSpeed + followerSpeedIncrement;
		follower = follower + (followerSpeed / 1000) * (delta);
	}

	boolean isTouchedLast = false;
	boolean isValidLine = false;

	public void processInput(InputHandler inputHandler) {
		// Process the given input from last iteration
		// Create new Lines and so stuff, touch gestures are included in the
		// inputHandler

		if (inputHandler.p == null) {
			return;
		} else if (inputHandler.isTouched && !isTouchedLast
				&& inputHandler.p != null) {
			// First down touch
			currentLine = new Line();
			currentLine.add(toWorldCoordinates(inputHandler.p));
			if (isInStartRange(inputHandler.p)) {
				isValidLine = true;
				soundManager.playStartLineSound();
			}
		} else if (inputHandler.isTouched && isTouchedLast
				&& inputHandler.p != null) {
			// Move
			currentLine.add(toWorldCoordinates(inputHandler.p));
		} else if (!inputHandler.isTouched && isTouchedLast
				&& inputHandler.p != null) {
			// Action Up
			if (!isInTargetRange(inputHandler.p) || !isValidLine) {
				currentLine.clear();
				this.soundManager.playMissedTargetSound();
			} else {
				currentLine.add(toWorldCoordinates(inputHandler.p));
				currentLine.origin = startPoint;
				currentLine.target = targetPoint;
				lines.add(currentLine);
				currentLine = null;
				setNewTarget();
				isValidLine = false;
				this.soundManager.playFinishedLineSound();
			}
		}
		isTouchedLast = inputHandler.isTouched;
	}

	/**
	 * Called, when a line is drawn from start to target node. Should generate a
	 * new target and probably shift the camera.
	 */
	private void setNewTarget() {
		// TODO Auto-generated method stub
	    
		startPoint = new Point(targetPoint);
		if(this.targetGenerator.shift(startPoint)) {
			// TODO move the camera;
		    
		}
		
		this.targetPoint = this.targetGenerator.generateTarget(startPoint);
	}

	private Point toWorldCoordinates(Point p) {
		// TODO convert coordinates
		return p;
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
