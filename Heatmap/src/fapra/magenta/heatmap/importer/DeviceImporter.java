package fapra.magenta.heatmap.importer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.opencsv.CSVReader;

import fapra.magenta.heatmap.data.Device;
import fapra.magenta.heatmap.data.DeviceHandler;

public class DeviceImporter {
    public DeviceHandler importData(InputStream in) {
        DeviceHandler handler = new DeviceHandler();
        
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(in), ',', '"');
            String [] nextLine;
            
            while ((nextLine = reader.readNext()) != null) {
               Device nextRow = parseLine(nextLine);
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
    
    private Device parseLine(String[] nextLine) {
        try {
            Device p = new Device(nextLine);
            return p;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
