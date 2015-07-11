package fapra.magenta.heatmap.data;

import com.eclipsesource.json.JsonObject;

public class Line {
    int id;
    int deviceId;
    int startGridX;
    int startGridY;
    int endGridX;
    int endGridY;
    int startPxX;
    int startPxY;
    int endPxX;
    int endPxY;
    int scrollDirection;
    
    public Line() {
        
    }
    
    public Line(String[] line) {
        id = Integer.parseInt(line[0]);
        deviceId = Integer.parseInt(line[1]);
        startGridX = Integer.parseInt(line[2]);
        startGridY = Integer.parseInt(line[3]);
        endGridX = Integer.parseInt(line[4]);
        endGridY = Integer.parseInt(line[5]);
        startPxX = Integer.parseInt(line[6]);
        startPxY = Integer.parseInt(line[7]);
        endPxX = Integer.parseInt(line[8]);
        endPxY = Integer.parseInt(line[9]);
        scrollDirection = Integer.parseInt(line[13]);
    }

    public Line(JsonObject obj) {
        System.out.println(obj);
        deviceId = obj.getInt("deviceId", -1);
        id = obj.getInt("lineId", -1);

        startGridX = obj.getInt("startGridX", -1);
        startGridY = obj.getInt("startGridY", -1);
        
        endGridX = obj.getInt("endGridX", -1);
        endGridY = obj.getInt("endGridY", -1);
        
        startPxX = obj.getInt("startPxX", -1);
        startPxY = obj.getInt("startPxY", -1);
        
        endPxX = obj.getInt("endPxX", -1);
        endPxY = obj.getInt("endPxY", -1);
        
        scrollDirection = obj.getInt("scrollDirection", -1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getStartGridX() {
        return startGridX;
    }

    public void setStartGridX(int startGridX) {
        this.startGridX = startGridX;
    }

    public int getStartGridY() {
        return startGridY;
    }

    public void setStartGridY(int startGridY) {
        this.startGridY = startGridY;
    }

    public int getEndGridX() {
        return endGridX;
    }

    public void setEndGridX(int endGridX) {
        this.endGridX = endGridX;
    }

    public int getEndGridY() {
        return endGridY;
    }

    public void setEndGridY(int endGridY) {
        this.endGridY = endGridY;
    }

    public int getStartPxX() {
        return startPxX;
    }

    public void setStartPxX(int startPxX) {
        this.startPxX = startPxX;
    }

    public int getStartPxY() {
        return startPxY;
    }

    public void setStartPxY(int startPxY) {
        this.startPxY = startPxY;
    }

    public int getEndPxX() {
        return endPxX;
    }

    public void setEndPxX(int endPxX) {
        this.endPxX = endPxX;
    }

    public int getEndPxY() {
        return endPxY;
    }

    public void setEndPxY(int endPxY) {
        this.endPxY = endPxY;
    }

    public int getScrollDirection() {
        return scrollDirection;
    }

    public void setScrollDirection(int scrollDirection) {
        this.scrollDirection = scrollDirection;
    }
}
