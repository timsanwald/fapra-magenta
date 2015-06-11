package fapra.magenta.simulation;

import java.util.LinkedList;
import java.util.Random;

import android.util.Log;
import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.input.InputHandler;

public class Simulation {

	/**
	 * Holds all drawn lines in this game. Initializes with one line.
	 * The last line in the list is the current one.
	 */
	public LinkedList<Line> lines;

	public Point startPoint;
	public Point targetPoint;
	
	// TODO remove those parameter
	public int width;
	public int height;
	
	public Simulation() {
		lines = new LinkedList<Line> ();
		lines.add(new Line());
		
		//TODO remove the next 2 lines
		startPoint = new Point(300, 300);
		targetPoint = new Point(450, 800);
	}

	// Update the environment
	public void update(float delta) {
		//TODO implement updating simulation
	    // move the mail xD
	}

	boolean isTouchedLast = false;
	boolean isValidLine = false;
	
	public void processInput(InputHandler inputHandler) {
		// Process the given input from last iteration
		// Create new Lines and so stuff, touch gestures are included in the inputHandler
	    
	    if (inputHandler.p == null) {
	        return;
	    } else if (inputHandler.isTouched && !isTouchedLast && inputHandler.p != null) {
	        // First down touch
            lines.add(new Line());
            lines.getLast().add(toWorldCoordinates(inputHandler.p));
            if (isInStartRange(inputHandler.p)) {
                isValidLine = true;
            }
        } else if (inputHandler.isTouched && isTouchedLast && inputHandler.p != null) {
            // Move
            lines.getLast().add(toWorldCoordinates(inputHandler.p));
        } else if (!inputHandler.isTouched && isTouchedLast && inputHandler.p != null) {
            //Action Up
            if (!isInTargetRange(inputHandler.p) || !isValidLine) {
                lines.removeLast();
            } else {
                Log.e("Simulation", "Successful drawing");
                lines.getLast().add(toWorldCoordinates(inputHandler.p));
                setNewTarget();
                isValidLine = false;
            }
        }
	    isTouchedLast = inputHandler.isTouched;
	}
	
	/**
	 * Called, when a line is drawn from start to target node.
	 * Should generate a new target and probably shift the camera.
	 */
	private void setNewTarget() {
        // TODO Auto-generated method stub

        startPoint = targetPoint;
        // TODO generate new target on grid
        Random r = new Random();
        targetPoint = new Point(epsilon + r.nextInt(width - 2 * epsilon), epsilon + r.nextInt(height - 2 * epsilon));
        // TODO shift the camera
    }

    private Point toWorldCoordinates(Point p) {
	    //TODO convert coordinates
        return p;
    }

	
    /**
     * Defines the accuracy that is needed to hit start and target point.
     */
    public final static int epsilon = 70; // TODO maybe dependent on density?
	
    /**
     * Checks if the given Point is in the range of the current start node.
     * 
     * @param point
     *            Point to check
     * @return True, if the distance between the given Point and the start
     *         node is less than epsilon, false otherwise.
     */
	private boolean isInStartRange(Point p) {
        if (p.distanceTo(startPoint) < epsilon) {
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
	    if (point.distanceTo(targetPoint) < epsilon) {
	        return true;
	    }
	    return false;
	}
}
