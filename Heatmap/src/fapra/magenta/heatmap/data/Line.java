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
        deviceId = Integer.parseInt(obj.getString("deviceId", "-1"));
        id = Integer.parseInt(obj.getString("lineId", "-1"));

        startGridX = Integer.parseInt(obj.getString("startGridX", "-1"));
        startGridY = Integer.parseInt(obj.getString("startGridY", "-1"));
        
        endGridX = Integer.parseInt(obj.getString("endGridX", "-1"));
        endGridY = Integer.parseInt(obj.getString("endGridY", "-1"));
        
        startPxX = Integer.parseInt(obj.getString("startPxX", "-1"));
        startPxY = Integer.parseInt(obj.getString("startPxY", "-1"));
        
        endPxX = Integer.parseInt(obj.getString("endPxX", "-1"));
        endPxY = Integer.parseInt(obj.getString("endPxY", "-1"));
        
        scrollDirection = Integer.parseInt(obj.getString("scrollDirection", "-1"));
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
