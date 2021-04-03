package org.csc133.a2.view;

import com.codename1.charts.util.ColorUtil;

public class HeadingDisplayComponent extends SevenSegmentDisplay {

    public HeadingDisplayComponent() {

        setLabel("HDG");
        ledColor = ColorUtil.YELLOW;
        numDigitsShowing = 3;
    }

    private void setHeadingDisplay(int heading) {

        if(heading == 0){
            clockDigits[0] = digitImages[0];
            clockDigits[1] = digitImages[0];
            clockDigits[2] = digitImages[0];
        } else {

            int i = 2;
            while (heading > 0) {
                clockDigits[i] = digitImages[heading % 10];
                heading = heading / 10;
                i--;
            }
        }
    }

    public void update(int number) {

        setHeadingDisplay(number);
        repaint();
    }


}