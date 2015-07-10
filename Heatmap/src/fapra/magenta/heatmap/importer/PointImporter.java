package fapra.magenta.heatmap.importer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.CSVReader;

import fapra.magenta.heatmap.data.PointHandler;
import fapra.magenta.heatmap.data.Point;

public class PointImporter {
    
    public PointHandler importData(InputStream in) {
        PointHandler handler = new PointHandler();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(in), ',', '"');
            String [] nextLine;
            
            List<Point> currentLine = null;
            int currentLineId = -1;
            
            while ((nextLine = reader.readNext()) != null) {
               Point nextRow = parseLine(nextLine);
               if (nextRow != null) {
                   if (currentLineId != nextRow.getLineID()) {
                       currentLine = new LinkedList<Point>();
                       currentLineId = nextRow.getLineID();
                       handler.put(currentLineId, currentLine);
                   }
                   currentLine.add(nextRow);
               }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler;
    }

    private Point parseLine(String[] nextLine) {
        try {
            Point p = new Point(nextLine); 
            return p;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
