package org.csc133.a2.view;

import com.codename1.charts.util.ColorUtil;

public class LivesDisplayComponent extends SevenSegmentDisplay {

    public LivesDisplayComponent() {

        setLabel("LIVES");
        ledColor = ColorUtil.GREEN;
        numDigitsShowing = 2;
    }

    private void setLivesDisplay(int lives) {

        while (lives > 0) {
            clockDigits[1] = digitImages[lives % 10];
            lives = lives / 10;
        }

    }

    public void update(int number) {

        setLivesDisplay(number);
        repaint();
    }
}