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

    public void normalize() {
        // Calculate Pointsize
        if (device == null || points == null || device.yDpi < 10) {
            System.out.println(this.toString());
            return;
        }
        GridManager manager = new GridManager(device.screenXPx, device.screenYPx, device.xDpi, device.yDpi);
        int pointSize = manager.pointSize;
        
        // Remove shift of points
        
        int startGridXPx = manager.targetToPxX(line.startGridX);
        int startGridYPx = manager.targetToPxY(line.startGridY);
        int endGridX = manager.targetToPxX(line.endGridX);
        int endGridY = manager.targetToPxY(line.endGridY);
        
        int shiftX = line.startPxX - startGridXPx;
        int shiftY = line.startPxY - startGridYPx;
        int shiftEndX = line.endPxX - endGridX;
        int shiftEndY = line.endPxY - endGridY;
        
        int epsilonX = Math.abs(shiftX) - Math.abs(shiftEndX);
        int epsilonY = Math.abs(shiftY) - Math.abs(shiftEndY);
        if (epsilonX > 1 || epsilonY > 1) {
            System.out.println("epsilonX=" + epsilonX + " epsilonY=" + epsilonY);
        }
        for (Point p : points) {
            p.setxPx(p.getxPx() - shiftX);
            p.setyPx(p.getyPx() - shiftY);
        }
        
        // Normalize the screen coordinates
        double normX = ((double) pixelX / (double) device.screenXPx);
        double normY = ((double) pixelY / (double) device.screenYPx);
        
        for (Point p : points) {
            if (p.getxPx() > pixelX) {
                System.out.println("false x=" + p.getxPx());
            } else if (p.getyPx() > pixelY) {
                System.out.println("false y=" + p.getyPx());
            }
            //TODO maybe remove casts
            p.setxPx((int) (((double) p.getxPx() / (double) device.screenXPx) * (double) pixelX));
            p.setyPx((int) (p.getyPx()));

        }
    }
    
    public static int pixelX = 1080;
    public static int pixelY = 1920;

    @Override
    public String toString() {
        return "CombinedRow [device=" + device.toString() + ", line=" + line.toString() + ", points=" + points + ", pixelX=" + pixelX + ", pixelY=" + pixelY
                + "]";
    }
}
