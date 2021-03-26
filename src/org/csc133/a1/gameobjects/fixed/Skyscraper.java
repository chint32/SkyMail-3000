package org.csc133.a1.gameobjects.fixed;


import org.csc133.a1.util.Constants;

import java.util.Arrays;

public class Skyscraper extends Fixed {

    private int sequenceNumber;

    public Skyscraper(int locationX, int locationY, int sequenceNumber) {

        super.setSize(Constants.skyscraperSize);
        this.sequenceNumber = sequenceNumber;
        super.setLocation(locationX, locationY);
        super.setColor(0, 0, 255);


    }

    public int getSequenceNumber(Skyscraper this) {
        return this.sequenceNumber;
    }

    //override setLocation to prevent changing color
    //of Skyscrapers outside of constructor
    @Override
    public void setColor(int colorR, int colorG, int colorB) {
        System.out.println("You cannot change the color of this Skyscrapers");
    }

    //override setLocation to prevent changing location
    //of Skyscrapers outside of constructor
    @Override
    public void setLocation(float locationX, float locationY) {
        System.out.println("You cannot change the location of Skyscrapers");
    }

    //override setSize to prevent changing size
    //of Skyscrapers outside of constructor
    @Override
    public void setSize(float size) {
        System.out.println("You cannot change the location of Skyscrapers");
    }

    @Override
    public String toString() {

        return "Skyscraper: loc = " + getLocationX() + ", " +
                getLocationY() + " color = " + Arrays.toString(getColor()) +
                " size = " + getSize() + " sequenceNumber = " +
                getSequenceNumber();

    }
}
