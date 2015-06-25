package fapra.magenta.rendering;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import fapra.magenta.Projection;
import fapra.magenta.data.Line;
import fapra.magenta.data.Point;
import fapra.magenta.data.obstacles.ObstacleGameObject;
import fapra.magenta.data.pickups.PickUpGameObject;
import fapra.magenta.simulation.Simulation;

public class Renderer {

    Projection projection;
    private Paint pickupPaint;
    private Paint oldPaint;
    private Paint scorePaint;
    private Paint coinPaint;

    public Renderer() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(10);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        followerPaint = new Paint();
        followerPaint.setColor(Color.RED);
        followerPaint.setStrokeWidth(50);
        followerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        followerPaint.setStrokeJoin(Paint.Join.ROUND);
        followerPaint.setStrokeCap(Paint.Cap.ROUND);
        followerPaint.setAntiAlias(true);
        
        oldPaint = new Paint();
        oldPaint.setColor(Color.LTGRAY);
        oldPaint.setStrokeWidth(50);
        oldPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        oldPaint.setStrokeJoin(Paint.Join.ROUND);
        oldPaint.setStrokeCap(Paint.Cap.ROUND);
        oldPaint.setAntiAlias(true);
        
        corridorPaint = new Paint();
        corridorPaint.setColor(Color.WHITE);
        //TODO make use of gridmanager size here
        corridorPaint.setStrokeWidth(100);
        corridorPaint.setStrokeJoin(Paint.Join.ROUND);
        corridorPaint.setStrokeCap(Paint.Cap.ROUND);
        corridorPaint.setAntiAlias(true);
        corridorPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(50);
        scorePaint.setTextAlign(Align.LEFT);
        
        coinPaint = new Paint();
        coinPaint.setColor(Color.BLACK);
        coinPaint.setTextSize(50);
        coinPaint.setTextAlign(Align.RIGHT);
        
        startPaint = new Paint();
        startPaint.setColor(Color.BLUE);
        startPaint.setStrokeWidth(4);
        startPaint.setStyle(Paint.Style.FILL);
        startPaint.setAntiAlias(true);

        pickupPaint = new Paint();
        pickupPaint.setColor(Color.GREEN);
        pickupPaint.setStrokeWidth(4);
        pickupPaint.setTextSize(30);
        pickupPaint.setAntiAlias(true);
        
        targetPaint = new Paint();
        targetPaint.setColor(Color.BLUE);
        targetPaint.setStrokeWidth(4);
        targetPaint.setStyle(Paint.Style.FILL);
        targetPaint.setAntiAlias(true);
    }

    public void draw(SurfaceHolder surfaceHolder, Simulation simulation, long delta) {
        Canvas c = null;
        try {
            c = surfaceHolder.lockCanvas();
            drawFrame(c, simulation);
            drawHUD(c, simulation);
        } catch (Exception e) {
            e.printStackTrace();
            // throw(new Error(e));
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (c != null)
                surfaceHolder.unlockCanvasAndPost(c);
        }
    }

    private void drawHUD(Canvas c, Simulation simulation) {
        String textScore = "score: " + simulation.scoringListener.score;
        Rect rect = new Rect();
        scorePaint.getTextBounds(textScore, 0, textScore.length(), rect);
        c.drawText(textScore, 10, (-rect.top) + 10, scorePaint);
        
        textScore = "coins: " + simulation.coinCalculationListener.coins;
        coinPaint.getTextBounds(textScore, 0, textScore.length(), rect);
        c.drawText(textScore, c.getWidth() - 10, (-rect.top) + 10, coinPaint);
    }

    private Paint linePaint;

    private void drawFrame(Canvas c, Simulation simulation) {
        this.projection = simulation.projection;
        
        if (c == null) {
            return;
        }
        c.translate(-simulation.projection.shiftX, -simulation.projection.shiftY);
        c.drawColor(Color.LTGRAY);
        
        // Draw corridor
        for (Line line : simulation.lines) {
            c.drawLine(line.origin.x, line.origin.y, line.target.x, line.target.y, corridorPaint);
            drawCircle(line.origin, c, corridorPaint, simulation.targetGenerator.gridManager.pointSize + 5);
            drawCircle(line.target, c, corridorPaint, simulation.targetGenerator.gridManager.pointSize + 5);
        }
        
        // Render path and follower
        for (Line line : simulation.lines) {
            //drawLine(l, c);
            c.drawLine(line.origin.x, line.origin.y, line.target.x, line.target.y, oldPaint);
            drawCircle(line.origin, c, oldPaint, simulation.targetGenerator.gridManager.pointSize);
            if (line.target.distanceTo(simulation.startPoint) >= simulation.targetGenerator.gridManager.pointSize) {
                drawCircle(line.target, c, oldPaint, simulation.targetGenerator.gridManager.pointSize);
            }
        }

        // Draw current line drawing
        if (simulation.currentLine != null) {
            drawLine(simulation.currentLine, c);
        }

        // Draw follower
        drawLineDistance(c, simulation);

        // Draw Pickups
        for (PickUpGameObject pickup : simulation.pickups) {
            renderPickup(c, pickup);
        }

        // Draw Obstacles
        for (ObstacleGameObject obstacle : simulation.obstacles) {
            renderObstacle(c, obstacle);
        }

        // Render start and target point
        drawCircle(simulation.startPoint, c, startPaint, simulation.targetGenerator.gridManager.pointSize);
        drawCircle(simulation.targetPoint, c, targetPaint, simulation.targetGenerator.gridManager.pointSize);

        c.translate(simulation.projection.shiftX, simulation.projection.shiftY);
    }

    private Paint corridorPaint;

    private Point followerPoint = new Point(0, 0);
    private Paint followerPaint = new Paint();

    private void drawLineDistance(Canvas c, Simulation sim) {
        float pathDistance = 0;
        float currDistance = 0;
        Path path = new Path();

        try {
            path.moveTo(sim.lines.getFirst().getFirst().x, sim.lines.getFirst().getFirst().y);
        } catch (NoSuchElementException e) {
            return;
        }

        for (Line line : sim.lines) {
            currDistance = (float) line.origin.distanceTo(line.target);
            if (pathDistance + currDistance < sim.follower) {
                path.lineTo(line.target.x, line.target.y);
                path.moveTo(line.target.x, line.target.y);
                drawCircle(line.origin, c, followerPaint, sim.targetGenerator.gridManager.pointSize);
                drawCircle(line.target, c, followerPaint, sim.targetGenerator.gridManager.pointSize);
            } else {
                // between current point and last is the follower
                float part = (sim.follower - pathDistance);
                followerPoint.x = line.origin.x + ((line.target.x - line.origin.x) / currDistance) * part;
                followerPoint.y = line.origin.y + ((line.target.y - line.origin.y) / currDistance) * part;
                path.lineTo(followerPoint.x, followerPoint.y);
                drawCircle(line.origin, c, followerPaint, sim.targetGenerator.gridManager.pointSize);
                break;
            }
            pathDistance += currDistance;
        }
        
        c.drawPath(path, followerPaint);
        
        
        /*
        float pathDistance = 0;
        float currDistance = 0;
        Point last;
        try {
            last = sim.lines.getFirst().getFirst();
        } catch (NoSuchElementException e) {
            return;
        }

        Path path = new Path();
        path.moveTo(last.x, last.y);

        for (int i = 0; i < sim.lines.size(); i++) {
            for (Point p : sim.lines.get(i)) {
                currDistance = (float) p.distanceTo(last);
                if (pathDistance + currDistance > sim.follower) {
                    // between current point and last is the follower
                    float part = (sim.follower - pathDistance);
                    followerPoint.x = last.x + ((p.x - last.x) / currDistance) * part;
                    followerPoint.y = last.y + ((p.y - last.y) / currDistance) * part;
                    path.lineTo(followerPoint.x, followerPoint.y);
                    c.drawPath(path, followerPaint);
                    return;
                } else {
                    path.lineTo(p.x, p.y);
                    path.moveTo(p.x, p.y);

                }
                pathDistance += currDistance;
                last = p;
            }
        } */
    }

    private void drawLine(LinkedList<Point> line, Canvas c) {
        Path path = new Path();
        try {
            path.moveTo(line.getFirst().x, line.getFirst().y);
        } catch (NoSuchElementException e) {

        }
        for (Point p : line) {
            path.lineTo(p.x, p.y);
            path.moveTo(p.x, p.y);
        }
        c.drawPath(path, linePaint);
    }

    private Paint startPaint;
    private Paint targetPaint;

    private void drawCircle(Point p, Canvas c, Paint paint, int size) {
        c.drawCircle(p.x, p.y, size, paint);
    }

    public void dispose() {
        // TODO clear eventual used resources
    }

    public void renderPickup(Canvas c, PickUpGameObject pickup) {
        // TODO
        c.drawCircle(pickup.position.x, pickup.position.y, pickup.radius, pickupPaint);
        c.drawText(pickup.getClass().getSimpleName().subSequence(0, 2).toString(), pickup.position.x, pickup.position.y, pickupPaint);
    }

    public void renderObstacle(Canvas c, ObstacleGameObject obstacle) {
        // TODO
    }
}
