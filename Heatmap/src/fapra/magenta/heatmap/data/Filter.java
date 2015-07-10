package fapra.magenta.heatmap.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Filter {

    public static List<java.awt.Point> convertPoints(List<CombinedRow> rows) {
        ArrayList<java.awt.Point> convertedPoints = new ArrayList<java.awt.Point>();
        
        for (CombinedRow row : rows) {
            for (Point p : row.points) {
                convertedPoints.add(new java.awt.Point(p.getxPx(), p.getyPx()));
            }
        }
        
        return convertedPoints;
    }
    
    public static List<CombinedRow> filterRows(List<CombinedRow> rows, int startGridX, int startGridY, int endGridX, int endGridY, int mode) {
        List<CombinedRow> result = new LinkedList<CombinedRow>();
        for (CombinedRow row : rows) {
            if (isAcceptable(row, startGridX, startGridY, endGridX, endGridY, mode)) {
                result.add(row);
            }
        }
        return result;
    }
    
    public static boolean isAcceptable(CombinedRow row, int startGridX, int startGridY, int endGridX, int endGridY, int mode) {
        if (row.line.getStartGridX() == startGridX 
                && row.line.getStartGridY() == startGridY 
                && row.line.getEndGridX() == endGridX 
                && row.line.getEndGridY() == endGridY
                && row.line.getScrollDirection() == mode) {
            return true;
        }
        return false;
    }
    
}
