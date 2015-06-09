package fapra.magenta.simulation;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.input.InputHandler;

public class Simulation {

	/**
	 * Holds all drawn lines in this game. Initializes with one line.
	 * The last line in the list is the current one.
	 */
	public LinkedList<Line> lines;

	public Simulation() {
		lines = new LinkedList<Line> ();
		lines.add(new Line());
	}

	// Update the environment
	public void update(float delta) {
		//TODO implement updating simulation
	}

	ConcurrentLinkedQueue<Point> last = null;

	public void processInput(InputHandler inputHandler) {
		// TODO
		// Process the given input from last iteration
		// Create new Lines and so stuff, touch gestures are included in the inputHandler

	    if (inputHandler.current == null) {
	        return;
	    }
	    if (last == null) {
	        last = inputHandler.current;
	    }

		addFromInputToCurrent();
		if (last != inputHandler.current) {
		    last = inputHandler.current;
		    lines.add(new Line());
		}
	}

	Point pFaddFromInputToCurrent;
	/**
	 * Transfer points from InputHandler to the simulation.
	 */
	private void addFromInputToCurrent() {
	    pFaddFromInputToCurrent = last.poll();
	    while(pFaddFromInputToCurrent != null) {
	        lines.getLast().add(pFaddFromInputToCurrent);
	        pFaddFromInputToCurrent = last.poll();
	    }
	}
}
