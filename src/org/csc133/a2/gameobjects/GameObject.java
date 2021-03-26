package org.csc133.a2.gameobjects;

public abstract class GameObject {

    private float size;
    private float locationX;
    private float locationY;
    private final int[] color = new int[3];

    public void setColor(int colorR, int colorG, int colorB) {
        this.color[0] = colorR;
        this.color[1] = colorG;
        this.color[2] = colorB;
    }

    public void setLocation(float locationX, float locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getLocationX() {
        return locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public int[] getColor() {
        return color;
    }
}
