package fapra.magenta.listeners;

import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.obstacles.ObstacleGameObject;
import fapra.magenta.data.pickups.PickUpGameObject;
import fapra.magenta.database.LineSaveThread;
import fapra.magenta.simulation.Simulation;

public class DataCollectionListener implements GameListenerInterface {
	@Override
	public void addedNewLine(Point start, Point target) {
	}

	@Override
	public void touchedPickup(PickUpGameObject pickup) {
	}

	@Override
	public void finishedLine(Line line) {
	}

	@Override
	public void touchedObstacle(ObstacleGameObject obstacle) {
	}

	@Override
	public void finishedGame(Simulation simulation) {
		LineSaveThread thread = new LineSaveThread(simulation);
		thread.start();
	}

}
