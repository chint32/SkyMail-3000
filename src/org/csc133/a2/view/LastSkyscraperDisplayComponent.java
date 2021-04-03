package org.csc133.a2.view;

import com.codename1.charts.util.ColorUtil;

public class LastSkyscraperDisplayComponent extends SevenSegmentDisplay {

    public LastSkyscraperDisplayComponent() {

        setLabel("LAST");
        ledColor = ColorUtil.GREEN;
        numDigitsShowing = 2;
    }

    private void setLastDisplay(int last) {

        while (last > 0) {
            clockDigits[1] = digitImages[last % 10];
            last = last / 10;
        }

    }

    public void update(int number) {

        setLastDisplay(number);
        repaint();
    }


}