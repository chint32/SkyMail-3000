package org.csc133.a1.gameobjects.fixed;


import org.csc133.a1.util.Constants;

import java.util.Arrays;
import java.util.Random;

public class RefuelingBlimp extends Fixed {

    private float capacity;

    public RefuelingBlimp(){

        Random rand = new Random();

        //set size of blimp 10 - 50
        super.setSize(rand.nextInt(41) + 10);

        //capacity has a 1:1 proportion with size
        capacity = getSize();

        //set locationX 0 - 1024
        //set locationY 0 - 768
        super.setLocation(rand.nextInt(Constants.gameWorldWidth), rand.nextInt(Constants.gameWorldHeight));

        setColor(0, 255, 0);

    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    //override setLocation to prevent changing location
    //of refueling blimps outside of constructor
    @Override
    public void setLocation(float locationX, float locationY) {
        System.out.println("You cannot change the location of RefuelingBlimps");
    }

    @Override
    public String toString() {
        return "RefuelingBlimp: loc = " + getLocationX() +
                ", " + getLocationY() + " color = " +
                Arrays.toString(getColor()) + " size = " +
                getSize() + " capacity = " +
                getCapacity();
    }

    //override setSize to prevent changing size
    //of refueling blimps outside of constructor
    @Override
    public void setSize(float size) {
        System.out.println("You cannot change the size of RefuelingBlimps");

    }
}
