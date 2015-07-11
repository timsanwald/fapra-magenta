package fapra.magenta.heatmap.data;

import com.eclipsesource.json.JsonObject;

public class Device {
    public int id;
    String hash;
    int screenXPx;
    int screenYPx;
    int gridSizeX;
    int gridSizeY;
    double xDpi;
    double yDpi;
    double density;
    
    public Device(String[] line) throws NumberFormatException {
        id = Integer.parseInt(line[0]);
        hash = line[1];
        screenXPx = Integer.parseInt(line[2]);
        screenYPx = Integer.parseInt(line[3]);
        gridSizeX = Integer.parseInt(line[4]);
        gridSizeY = Integer.parseInt(line[5]);
        xDpi = Double.parseDouble(line[6]);
        yDpi = Double.parseDouble(line[7]);
        density = Double.parseDouble(line[8]);
    }
    
    public Device(JsonObject obj) {
        id = obj.getInt("deviceId", 0);
        screenXPx = obj.getInt("screenXPx", 0);
        screenYPx = obj.getInt("screenYPx", 0);
        gridSizeX = obj.getInt("gridSizeX", 0);
        gridSizeY = obj.getInt("gridSizeY", 0);
        xDpi = obj.getDouble("xDpi", 0);
        yDpi = obj.getDouble("yDpi", 0);
        density = obj.getDouble("density", 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getScreenXPx() {
        return screenXPx;
    }

    public void setScreenXPx(int screenXPx) {
        this.screenXPx = screenXPx;
    }

    public int getScreenYPx() {
        return screenYPx;
    }

    public void setScreenYPx(int screenYPx) {
        this.screenYPx = screenYPx;
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public void setGridSizeX(int gridSizeX) {
        this.gridSizeX = gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setGridSizeY(int gridSizeY) {
        this.gridSizeY = gridSizeY;
    }

    public double getxDpi() {
        return xDpi;
    }

    public void setxDpi(double xDpi) {
        this.xDpi = xDpi;
    }

    public double getyDpi() {
        return yDpi;
    }

    public void setyDpi(double yDpi) {
        this.yDpi = yDpi;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    @Override
    public String toString() {
        return "Device [id=" + id + ", hash=" + hash + "]";
    }
}
