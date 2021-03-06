package fapra.magenta.heatmap.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Filter {

    public static List<java.awt.Point> convertPointsInRow(List<CombinedRow> rows) {
        ArrayList<java.awt.Point> convertedPoints = new ArrayList<java.awt.Point>();

        for (CombinedRow row : rows) {
            convertedPoints.addAll(convertPoints(row.points));
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
        if (row.line.getStartGridX() == startGridX && row.line.getStartGridY() == startGridY && row.line.getEndGridX() == endGridX
                && row.line.getEndGridY() == endGridY && row.line.getScrollDirection() == mode) {
            return true;
        }
        return false;
    }

    private static final String pattern = "http://fachpraktikum.hci.simtech.uni-stuttgart.de/ss2015/grp3/server/public/index.php/api/dbLines/start-{0}-{1}/end-{2}-{3}/";
    private static final String pattern2 = "http://fachpraktikum.hci.simtech.uni-stuttgart.de/ss2015/grp3/server/public/index.php/api/dbLines/?page={0}";
    
    public static List<CombinedRow> queryRows(int startGridX, int startGridY, int endGridX, int endGridY){
        String url = MessageFormat.format(pattern, startGridX, startGridY, endGridX, endGridY);
        
        // stackoverflow 
        
        try {
          InputStream is = new URL(url).openStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          JsonArray array = JsonArray.readFrom(rd);
          is.close();
          return generateRows(array);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

    public static List<CombinedRow> queryRows(int pages){
        try {
        List<CombinedRow> rows = new ArrayList<CombinedRow>();
        for (int i=1; i<=pages; i++) {
            String url = MessageFormat.format(pattern2, i);
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            JsonObject array = JsonObject.readFrom(rd);
            is.close();  
            rows.addAll(generateRows(array.get("data").asArray()));
        }
        
        if (rows.isEmpty()) {
            return null;
        }
        return rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<CombinedRow> generateRows(JsonArray array) {
        List<CombinedRow> rows = new ArrayList<CombinedRow>(array.size());
        for (JsonValue value : array) {
            if (value instanceof JsonObject) {
                CombinedRow row = generateRow((JsonObject) value);
                if (row != null) {
                    rows.add(row);
                }
            }
        }
        return rows;
    }

    private static CombinedRow generateRow(JsonObject obj) {
        // single line as input
        Device device = new Device(obj.get("device").asObject());
        Line line = new Line(obj);
        List<Point> points = generatePoints(obj);
        CombinedRow row = new CombinedRow(device, line, points);
        return row;
    }
    
    private static List<Point> generatePoints(JsonObject obj) {
        List<Point> points = new ArrayList<Point>();
        
        JsonArray ps = obj.get("points").asArray();
        Iterator<JsonValue> it = ps.iterator();
        while (it.hasNext()) {
            JsonObject pObj = it.next().asObject();
            Point p = new Point(pObj);
            points.add(p);
        }
        return points;
    }

    public static void normalizeRows(List<CombinedRow> filteredRows) {
        for (CombinedRow row : filteredRows) {
            row.normalize();
        }
    }

    public static List<java.awt.Point> convertPoints(List<Point> points) {
        List<java.awt.Point> results = new ArrayList<java.awt.Point>();
        for (Point p : points) {
            results.add(new java.awt.Point(p.getxPx(), p.getyPx()));
        }
        return results;
    }
}
