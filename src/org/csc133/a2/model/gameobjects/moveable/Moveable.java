package org.csc133.a2.model.gameobjects.moveable;

import org.csc133.a2.model.gameobjects.GameObject;

public abstract class Moveable extends GameObject {

    public float heading;
    private float speed;

    //This method is called from the tickClock method in GameWorld
    //Updates the location of moving objects based on speed and heading
    public void move(){

        float locationX = getLocationX();
        float locationY = getLocationY();

        //Calculate next location after clock tick based on current speed and heading
        locationX = (float) (locationX + (Math.cos(Math.toRadians(90 - heading)) * speed));
        locationY = (float) (locationY + (Math.sin(Math.toRadians(90 - heading)) * speed));

        setLocation(locationX, locationY);
    }



    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }
}
