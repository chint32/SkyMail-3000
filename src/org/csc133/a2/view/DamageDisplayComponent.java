package org.csc133.a2.view;

import com.codename1.charts.util.ColorUtil;

public class DamageDisplayComponent extends SevenSegmentDisplay {

    public DamageDisplayComponent() {

        setLabel("DMG");
        ledColor = ColorUtil.GREEN;
        numDigitsShowing = 2;
    }

    private void setDamageDisplay(int damageLevel) {

        int i = 1;
        while (damageLevel > 0) {
            clockDigits[i] = digitImages[damageLevel % 10];
            damageLevel = damageLevel / 10;
            i--;
        }
    }

    public void update(int number) {

        setDamageDisplay(number);
        repaint();
    }
}