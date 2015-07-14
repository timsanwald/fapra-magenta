package fapra.magenta.heatmap.data;

import java.util.List;

import fapra.magenta.heatmap.target.GridManager;

public class CombinedRow {
    Device device;
    Line line;
    List<Point> points;

    public CombinedRow(Device device, Line line, List<Point> points) {
        super();
        this.device = device;
        this.line = line;
        this.points = points;
    }

    static float max = 0;
    
    public static int pixelX = 1080;
    public static int pixelY = 1920;
    public static GridManager refManager = new GridManager(pixelX, pixelY, 440, 440);
    
    public void normalize() {
        // Calculate Pointsize
        if (device == null || points == null) {
            return;
        }
        GridManager manager = new GridManager(device.screenXPx, device.screenYPx, device.xDpi, device.yDpi);
        
        // Remove shift of points
        int startGridXPx = manager.targetToPxX(line.startGridX);
        int startGridYPx = manager.targetToPxY(line.startGridY);
        
        int shiftX = startGridXPx - line.startPxX;
        int shiftY = startGridYPx - line.startPxY;
        
        for (Point p : points) {
            p.setxPx(p.getxPx() + shiftX);
            p.setyPx(p.getyPx() + shiftY);
        }
        
        
        // Normalize the screen coordinates
        for (Point p : points) {
            //TODO maybe remove casts
            p.setxPx((int) (((double) p.getxPx() / (double) device.screenXPx) * (double) pixelX));
            p.setyPx((int) (((double) p.getyPx() / (double) device.screenYPx) * (double) pixelY));
        }
        Point start = new Point(-1, -1, line.startPxX + shiftX, line.startPxY + shiftY);
        start.setxPx((int) (((double) start.getxPx() / (double) device.screenXPx) * (double) pixelX));
        start.setyPx((int) (((double) start.getyPx() / (double) device.screenYPx) * (double) pixelY));
        
        Point normStart = new Point(-1, -1, refManager.targetToPxX(line.startGridX), refManager.targetToPxY(line.startGridY));
        shiftPoints(start.getxPx() - normStart.getxPx(), start.getyPx() - normStart.getyPx());
        
    }

    private void shiftPoints(float shiftX, float shiftY) {
        shiftPoints((int) shiftX, (int) shiftY);
    }

    private void shiftPoints(int x, int y) {
        for (Point p : this.points) {
            p.setxPx(p.getxPx() - x);
            p.setyPx(p.getyPx() - y);
        }
    }
    
    @Override
    public String toString() {
        return "CombinedRow [device=" + device.toString() + ", line=" + line.toString() + ", points=" + points + ", pixelX=" + pixelX + ", pixelY=" + pixelY
                + "]";
    }
}
