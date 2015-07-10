package fapra.magenta.heatmap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fapra.magenta.heatmap.data.CombinedRow;
import fapra.magenta.heatmap.data.DeviceHandler;
import fapra.magenta.heatmap.data.Line;
import fapra.magenta.heatmap.data.LineHandler;
import fapra.magenta.heatmap.data.PointHandler;
import fapra.magenta.heatmap.importer.DeviceImporter;
import fapra.magenta.heatmap.importer.LineImporter;
import fapra.magenta.heatmap.importer.PointImporter;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        
        InputStream points = new FileInputStream("input/data_points.csv");
        InputStream devices = new FileInputStream("input/data_devices.csv");
        InputStream lines = new FileInputStream("input/data_lines.csv");
        
        PointImporter pImporter = new PointImporter();
        PointHandler pHandler = pImporter.importData(points);
        System.out.println(pHandler.size());
        
        DeviceImporter dImporter = new DeviceImporter();
        DeviceHandler dHandler = dImporter.importData(devices);
        System.out.println(dHandler.size());
        
        LineImporter lImporter = new LineImporter();
        LineHandler lHandler = lImporter.importData(lines);
        System.out.println(lHandler.size());
        
        
        List<CombinedRow> rows = new ArrayList<CombinedRow>(lHandler.size());
        Collection<Line> lineCollection = lHandler.valueCollection();
        for (Line l : lineCollection) {
            CombinedRow row = new CombinedRow(dHandler.get(l.getDeviceId()), l, pHandler.get(l.getId()));
            row.normalize();
            rows.add(row);
        }
        
        System.out.println(rows.size());
        
        MainFrame frame = new MainFrame(rows);
        frame.setVisible(true);
    }

}
