package org.csc133.a1.gameobjects.moveable;


import org.csc133.a1.util.Constants;

import java.util.Arrays;
import java.util.Random;

public class Bird extends Moveable {

    public Bird(){
        Random rand = new Random();

        //randomly set size of bird 10 - 50
        super.setSize(rand.nextInt(41) + 10);

        //randomly set speed of bird 5 - 10
        setSpeed(rand.nextInt(6) + 5);

        //randomly set locationX 0 - 1024
        //randomly set locationY 0 - 768
        setLocation(rand.nextInt(Constants.gameWorldWidth), rand.nextInt(Constants.gameWorldHeight));

        //randomly set heading 0 - 359
        setHeading(rand.nextInt(360));

        super.setColor(255, 0, 255);
    }

    public void adjustHeading(){

        //adjust heading of bird  within +- 5 degrees randomly so they don't fly in a straight line
        Random rand = new Random();
        setHeading(getHeading() + (rand.nextInt(10) - 5));
        System.out.println("Bird heading adjusted. New heading = " + getHeading());
    }

    //if bird goes out of bounds, turn around (180 degrees)
    public void ifBirdOutOfBounds_turnBirdAround(){

        //check if bird is out of bound.
        //If yes -> turn turn 180 degrees
        if(getLocationX() < 0 || getLocationX() > Constants.gameWorldWidth
        || getLocationY() < 0 || getLocationY() > Constants.gameWorldHeight){
            setHeading(getHeading() - 180);
            System.out.println("Bird out of bounds. Turning it around");
        }
    }

    @Override
    public String toString() {
        return "Bird: loc = " + getLocationX() + ", " +
                getLocationY() + " color = " + Arrays.toString(getColor()) +
                " heading = " + getHeading() + " speed = " +
                getSpeed() + " size = " + getSize();
    }

    //override setLocation to prevent changing color
    //of Bird outside of constructor
    @Override
    public void setColor(int colorR, int colorG, int colorB) {
        System.out.println("You cannot change the color of this class.");
    }

    //override setSize to prevent changing size
    //of birds outside of constructor
    @Override
    public void setSize(float size) {
        System.out.println("You cannot change the size of birds");

    }
}
