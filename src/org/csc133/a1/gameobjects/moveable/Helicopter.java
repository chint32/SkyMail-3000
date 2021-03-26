package org.csc133.a1.gameobjects.moveable;

import org.csc133.a1.interfaces.ISteerable;
import org.csc133.a1.util.Constants;

import java.util.Arrays;

public class Helicopter extends Moveable implements ISteerable {

    private float stickAngle = 0f;
    private final float maximumSpeed = Constants.helicopterMaxSpeed;
    private float fuelLevel = Constants.helicopterMaxFuel;
    private final float fuelConsumptionRate = Constants.helicopterFuelConsumptionRate;
    private float damageLevel = 0f;
    private int lastSkyscraperReached = 1;

    public Helicopter(float locationX, float locationY){
        setLocation(locationX, locationY);
        super.setSize(Constants.helicopterSize);
        setSpeed(20f);
        setColor(255, 0, 0);
    }

    public float getStickAngle() {
        return stickAngle;
    }

    public void setStickAngle(float stickAngle) {

        this.stickAngle = stickAngle;
    }

    public float getMaximumSpeed() {
        return maximumSpeed;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public float getFuelConsumptionRate() {return fuelConsumptionRate;}


    public float getDamageLevel() {return damageLevel;}

    public void setDamageLevel(float damageLevel) {
        this.damageLevel = damageLevel;
    }

    public int getLastSkyscraperReached() {return lastSkyscraperReached;}

    public void setLastSkyscraperReached(int lastSkyscraperReached) {
        this.lastSkyscraperReached = lastSkyscraperReached;
    }

    @Override
    public void changeHeadingIfStickAngleNotZero() {

        //As the helicopter adjust heading, the stick angle is adjust equally
        //in the other direction
        if(stickAngle > 0f){
            setHeading(getHeading() + 5f);
            stickAngle -= 5f;
            System.out.println("Heading adjusted +5 degree. Current heading = "
                    + getHeading());
        } else if(stickAngle < 0f){
            setHeading(getHeading() - 5f);
            stickAngle += 5f;
            System.out.println("Heading adjusted -5 degree. " +
                    "Current heading = " + getHeading());

        }
    }

    @Override
    public String toString() {
        return "Helicopter: loc = " + getLocationX() +
                ", " + getLocationY() + " color = " +
                Arrays.toString(getColor()) +
                " heading = " + getHeading() +
                " speed = " + getSpeed() +
                " size = " + getSize() + " maxSpeed = " +
                getMaximumSpeed() + " stickAngle = " +
                getStickAngle() + " fuelLevel = " +
                getFuelLevel() + " damageLevel = " +
                getDamageLevel();
    }

    //override setSize to prevent changing size
    //of helicopters outside of constructor
    @Override
    public void setSize(float size) {
        System.out.println("You cannot change the size of Helicopters");
    }
}
