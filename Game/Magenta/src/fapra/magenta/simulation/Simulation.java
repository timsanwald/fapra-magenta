package fapra.magenta.simulation;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;
import fapra.magenta.Projection;
import fapra.magenta.audio.sound.ISoundManager;
import fapra.magenta.data.Circle;
import fapra.magenta.data.Line;
import fapra.magenta.data.Upgrades;
import fapra.magenta.data.obstacles.ObstacleGameObject;
import fapra.magenta.data.pickups.PickUpFactory;
import fapra.magenta.data.pickups.PickUpGameObject;
import fapra.magenta.data.pickups.StopTimePickUp;
import fapra.magenta.input.InputHandler;
import fapra.magenta.target.TargetGenerator;

public class Simulation {

    /**
     * Holds all drawn lines in this game. Initializes with one line. The last
     * line in the list is the current one.
     */
    public LinkedList<Line> lines;
    public Line currentLine;

    public Circle startPoint;
    public Circle targetPoint;

    public TargetGenerator targetGenerator;

    public Projection projection;

    /**
     * Defines when this simulation is finished and can be closed.
     */
    public boolean isGameOver = false;

    // Pickup objects
    public PickUpFactory pickUpFactory;
    public List<PickUpGameObject> pickups;
    public List<ObstacleGameObject> obstacles;

    private ISoundManager soundManager;

    public Simulation(TargetGenerator targetGenerator) {
        projection = new Projection();
        lines = new LinkedList<Line>();
        pickups = new LinkedList<PickUpGameObject>();
        obstacles = new LinkedList<ObstacleGameObject>();
        this.targetGenerator = targetGenerator;
    }

    public void setup(Upgrades upgrades, ISoundManager soundManager) {
        targetPoint = new Circle(this.targetGenerator.generateStartPoint(), this.targetGenerator.gridManager.pointSize);
        setNewTarget();
        while (currentDistance < upgrades.followerStartDistance) {
            currentLine = new Line();
            currentLine.add(startPoint);
            currentLine.add(targetPoint);
            addCurrentLine();
            setNewTarget();
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
        lines.add(currentLine);
        currentLine = null;
    }

    private PickUpGameObject currentPickup = null;

    // Update the environment
    public void update(float delta) {
        // update pickup
        if (currentPickup != null) {
            currentPickup.update(delta);
        }

        // update follower
        if (!(currentPickup instanceof StopTimePickUp)) {
            followerSpeed = followerSpeed + followerSpeedIncrement;
            follower = follower + (followerSpeed / 1000) * (delta);
        }

        // Check for expired pickup
        if (currentPickup != null && !currentPickup.isAlive()) {
            currentPickup = null;
        }

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
            if (this.startPoint.hitCheck(inputHandler.p)) {
                isValidLine = true;
                soundManager.playStartLineSound();
            }
        } else if (inputHandler.eventID == 2 && currentLine != null) {
            // Move
            currentLine.add(inputHandler.p);
            if (isValidLine) {
                // Check for hitting pickups
                for (PickUpGameObject pickup : pickups) {
                    if (pickup.hitCheck(inputHandler.p)) {
                        currentPickup = pickup;
                        Log.d("Simulation", pickup.getClass().getSimpleName() + " bound as current pickup");
                    }
                }
                pickups.remove(currentPickup);

                // Check for hitting obstacles
                for (ObstacleGameObject obstacle : obstacles) {
                    if (obstacle.hitCheck(inputHandler.p)) {
                        isValidLine = false;
                    }
                }
            }
        } else if (inputHandler.eventID == 3 && currentLine != null) {
            // Action Up
            if (!this.targetPoint.hitCheck(inputHandler.p)) {
                isValidLine = false;
            }

            if (isValidLine) {
                currentLine.add(inputHandler.p);
                addCurrentLine();
                setNewTarget();
                isValidLine = false;
                this.soundManager.playFinishedLineSound();
            } else {
                currentLine.clear();
                this.soundManager.playMissedTargetSound();
            }
        }
        inputHandler.reset();
    }

    /**
     * Called, when a line is drawn from start to target node. Should generate a
     * new target and probably shift the camera.
     */
    private void setNewTarget() {
        startPoint = new Circle(targetPoint);
        projection.addShift(this.targetGenerator.shiftX(startPoint), this.targetGenerator.shiftY(startPoint),
                this.targetGenerator.gridManager);
        this.targetPoint = new Circle(this.targetGenerator.generateTarget(startPoint), this.targetGenerator.gridManager.pointSize);
        projection.convertFromPixels(this.targetPoint);
        // TODO upgradeable probability to spawn a new pickup
        generateNewPickup();
    }

    private void generateNewPickup() {
        PickUpGameObject p = PickUpFactory.generatePickUpRandomly(this, this.targetGenerator.gridManager);
        if (p != null) {
            pickups.add(p);
        }
    }

    public float currentDistance = 0;
    public float follower = 0;
    public float followerSpeed = 60;
    public float followerSpeedIncrement = 0.2f;
}
