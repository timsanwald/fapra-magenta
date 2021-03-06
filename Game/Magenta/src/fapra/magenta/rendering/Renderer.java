package fapra.magenta.rendering;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import fapra.magenta.Projection;
import fapra.magenta.R;
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
    private Paint followerPointPaint;
    private Paint corridorPointPaint;
    private Paint oldPointPaint;
    private Paint distancePaint;

    public Renderer(int pointWidth, Context context) {
        // Line paints

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(10);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        followerPaint = new Paint();
        followerPaint.setColor(Color.RED);
        followerPaint.setStrokeWidth(pointWidth / 2);
        followerPaint.setStyle(Paint.Style.STROKE);
        followerPaint.setStrokeJoin(Paint.Join.ROUND);
        followerPaint.setStrokeCap(Paint.Cap.ROUND);
        followerPaint.setAntiAlias(true);
        
        oldPaint = new Paint();
        oldPaint.setColor(Color.LTGRAY);
        oldPaint.setStrokeWidth(pointWidth / 2);
        oldPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        oldPaint.setStrokeJoin(Paint.Join.ROUND);
        oldPaint.setStrokeCap(Paint.Cap.ROUND);
        oldPaint.setAntiAlias(true);
        
        corridorPaint = new Paint();
        corridorPaint.setColor(Color.WHITE);
        corridorPaint.setStrokeWidth(pointWidth);
        corridorPaint.setStrokeJoin(Paint.Join.ROUND);
        corridorPaint.setStrokeCap(Paint.Cap.ROUND);
        corridorPaint.setAntiAlias(true);
        corridorPaint.setStyle(Paint.Style.STROKE);
        
        
        // Point paints
        Paint basePointPaint = new Paint();
        basePointPaint.setStyle(Paint.Style.FILL);
        basePointPaint.setAntiAlias(true);
        
        startPaint = new Paint(basePointPaint);
        startPaint.setColor(Color.BLUE);

        pickupPaint = new Paint(basePointPaint);
        pickupPaint.setColor(Color.GREEN);
        
        targetPaint = new Paint(basePointPaint);
        targetPaint.setColor(Color.BLUE);
        
        corridorPointPaint = new Paint(basePointPaint);
        corridorPointPaint.setColor(Color.WHITE);
        
        followerPointPaint = new Paint(basePointPaint);
        followerPointPaint.setColor(Color.RED);
        
        oldPointPaint = new Paint(basePointPaint);
        oldPointPaint.setColor(Color.LTGRAY);
        
        // HUD paints
        Paint hudBasicPaint = new Paint();
        hudBasicPaint.setColor(Color.BLACK);
        hudBasicPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.hudFontSize));
        
        scorePaint = new Paint(hudBasicPaint);
        scorePaint.setTextAlign(Align.LEFT);
        
        coinPaint = new Paint(hudBasicPaint);
        coinPaint.setTextAlign(Align.RIGHT);
        
        distancePaint = new Paint(hudBasicPaint);
        distancePaint.setTextAlign(Align.CENTER);
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
        Rect rect = new Rect();
        
        String textScore = "score: " + simulation.scoringListener.score;
        scorePaint.getTextBounds(textScore, 0, textScore.length(), rect);
        c.drawText(textScore, 10, (-rect.top) + 10, scorePaint);
        
        textScore = "distance: " + ((int) (simulation.timeLeft / 1000f)) + "sec";
        coinPaint.getTextBounds(textScore, 0, textScore.length(), rect);
        c.drawText(textScore, c.getWidth()/2, (-rect.top) + 10, distancePaint);
        
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
            c.drawCircle(line.origin.x, line.origin.y, simulation.targetGenerator.gridManager.pointSize * 1.3f, corridorPointPaint);
            c.drawCircle(line.target.x, line.target.y, simulation.targetGenerator.gridManager.pointSize * 1.3f, corridorPointPaint);
        }
        
        // Render path and follower
        for (Line line : simulation.lines) {
            //drawLine(l, c);
            c.drawLine(line.origin.x, line.origin.y, line.target.x, line.target.y, oldPaint);
            drawCircle(line.origin, c, oldPointPaint, simulation.targetGenerator.gridManager.pointSize);
            if (line.target.distanceTo(simulation.startPoint) >= simulation.targetGenerator.gridManager.pointSize) {
                drawCircle(line.target, c, oldPointPaint, simulation.targetGenerator.gridManager.pointSize);
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
            renderPickup(c, pickup, simulation.activity);
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
                drawCircle(line.origin, c, followerPointPaint, sim.targetGenerator.gridManager.pointSize);
                drawCircle(line.target, c, followerPointPaint, sim.targetGenerator.gridManager.pointSize);
            } else {
                // between current point and last is the follower
                float part = (sim.follower - pathDistance);
                followerPoint.x = line.origin.x + ((line.target.x - line.origin.x) / currDistance) * part;
                followerPoint.y = line.origin.y + ((line.target.y - line.origin.y) / currDistance) * part;
                path.lineTo(followerPoint.x, followerPoint.y);
                drawCircle(line.origin, c, followerPointPaint, sim.targetGenerator.gridManager.pointSize);
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
    }

    public void renderPickup(Canvas c, PickUpGameObject pickup, Context context) {
        Bitmap b = pickup.getDrawable(context);
        c.drawBitmap(b, pickup.position.x - (b.getWidth()/2), pickup.position.y - (b.getHeight()/2), pickupPaint);
    }

    public void renderObstacle(Canvas c, ObstacleGameObject obstacle) {
        // TODO
    }
}
