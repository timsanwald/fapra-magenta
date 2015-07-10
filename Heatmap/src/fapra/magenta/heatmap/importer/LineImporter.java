package fapra.magenta.heatmap.importer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;

import fapra.magenta.heatmap.data.Device;
import fapra.magenta.heatmap.data.Line;
import fapra.magenta.heatmap.data.LineHandler;
import fapra.magenta.heatmap.data.Point;

public class LineImporter {

    public LineHandler importData(InputStream in) {
        LineHandler handler = new LineHandler();
        
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(in), ',', '"');
            String [] nextLine;
            
            while ((nextLine = reader.readNext()) != null) {
               Line nextRow = parseLine(nextLine);
               if (nextRow != null) {
                   handler.put(nextRow.getId(), nextRow);
               }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return handler;
    }

    private Line parseLine(String[] nextLine) {
        try {
            Line p = new Line(nextLine); 
            return p;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
