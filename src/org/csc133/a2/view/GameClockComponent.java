package org.csc133.a2.view;



public class GameClockComponent extends SevenSegmentDisplay{

    private final long startTimeMillis;
    private long lastUpdateMillis;

    public GameClockComponent(){
        setLabel(" GAME CLOCK");
        numDigitsShowing = 6;

        startTimeMillis = System.currentTimeMillis();
    }


    protected void update(long elapsedMilliseconds) {
        int minutes = (int)(elapsedMilliseconds / 60000);
        int seconds = (int)((elapsedMilliseconds % 60000) / 1000);
        int tenthsOfSeconds = (int)((elapsedMilliseconds % 1000) / 100);

        clockDigits[0] = digitImages[minutes / 10];
        clockDigits[1] = digitImages[minutes % 10];
        clockDigits[2] = colonImage;
        clockDigits[3] = digitImages[seconds / 10];
        clockDigits[4] = digitWithDotImages[seconds % 10];
        clockDigits[5] = digitImages[tenthsOfSeconds];
    }

    @Override
    public boolean animate() {
        if (System.currentTimeMillis() - lastUpdateMillis >= 10) {

            lastUpdateMillis = System.currentTimeMillis();
            long elapsedTime = lastUpdateMillis - startTimeMillis;
            update(elapsedTime);
            return true;
        }
        else {
            return false;
        }
    }



}
