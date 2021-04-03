package org.csc133.a2.view;

import com.codename1.charts.util.ColorUtil;

public class FuelDisplayComponent extends SevenSegmentDisplay {

    public FuelDisplayComponent() {

        setLabel("FUEL");
        ledColor = ColorUtil.YELLOW;
        numDigitsShowing = 4;
    }

    private void setFuelDisplay(int fuelLevel) {

            int i = 3;
            while (fuelLevel > 0) {
                clockDigits[i] = digitImages[fuelLevel % 10];
                fuelLevel = fuelLevel / 10;
                i--;
            }

    }

    public void update(int number) {

        setFuelDisplay(number);
        repaint();
    }
}
