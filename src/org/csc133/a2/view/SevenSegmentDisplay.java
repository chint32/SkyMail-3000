package org.csc133.a2.view;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

public class SevenSegmentDisplay extends Component {

    Image[] digitImages = new Image[10];
    Image[] digitWithDotImages = new Image[10];
    Image colonImage;
    private String label = null;

    int ledColor;

    int numDigitsShowing = 8;
    Image[] clockDigits = new Image[numDigitsShowing];

    public SevenSegmentDisplay(){
        super();
        try{
            digitImages[0] = Image.createImage("/LED_digit_0.png");
            digitImages[1] = Image.createImage("/LED_digit_1.png");
            digitImages[2] = Image.createImage("/LED_digit_2.png");
            digitImages[3] = Image.createImage("/LED_digit_3.png");
            digitImages[4] = Image.createImage("/LED_digit_4.png");
            digitImages[5] = Image.createImage("/LED_digit_5.png");
            digitImages[6] = Image.createImage("/LED_digit_6.png");
            digitImages[7] = Image.createImage("/LED_digit_7.png");
            digitImages[8] = Image.createImage("/LED_digit_8.png");
            digitImages[9] = Image.createImage("/LED_digit_9.png");


            digitWithDotImages[0] = Image.createImage("/LED_digit_0_with_dot.png");
            digitWithDotImages[1] = Image.createImage("/LED_digit_1_with_dot.png");
            digitWithDotImages[2] = Image.createImage("/LED_digit_2_with_dot.png");
            digitWithDotImages[3] = Image.createImage("/LED_digit_3_with_dot.png");
            digitWithDotImages[4] = Image.createImage("/LED_digit_4_with_dot.png");
            digitWithDotImages[5] = Image.createImage("/LED_digit_5_with_dot.png");
            digitWithDotImages[6] = Image.createImage("/LED_digit_6_with_dot.png");
            digitWithDotImages[7] = Image.createImage("/LED_digit_7_with_dot.png");
            digitWithDotImages[8] = Image.createImage("/LED_digit_8_with_dot.png");
            digitWithDotImages[9] = Image.createImage("/LED_digit_9_with_dot.png");

            colonImage = Image.createImage("/LED_colon.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < numDigitsShowing; i ++){
            clockDigits[i] = digitImages[0];
        }



        ledColor = ColorUtil.CYAN;
    }

    public void setLedColor(int ledColor){
    }

    public void start(){
        getComponentForm().registerAnimated(this);
    }

    public void stop(){
        getComponentForm().deregisterAnimated(this);
    }

    public void laidOut(){
        this.start();
    }



    protected Dimension calcPreferredSize(){
        return new Dimension(colonImage.getWidth() * numDigitsShowing + 20, colonImage.getHeight() + 20 );
    }


    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD = 1;

        int displayDigitWidth = clockDigits[0].getWidth();
        int displayDigitHeight = clockDigits[0].getHeight();

        int displayClockWidth = displayDigitWidth * numDigitsShowing;

        int displayX = getX() + (getWidth() - displayClockWidth) / 2;
        int displayY = getY() + (getHeight() - displayDigitHeight) / 2;

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight() );

        g.setColor(ledColor);
        g.fillRect(displayX + COLOR_PAD,
                displayY + COLOR_PAD ,
                displayClockWidth - COLOR_PAD * 2,
                displayDigitHeight - COLOR_PAD * 2);

        for(int digitIndex = 0; digitIndex < numDigitsShowing ; digitIndex++){
            g.drawImage(clockDigits[digitIndex],
                    displayX + digitIndex * displayDigitWidth,
                    displayY ,
                    displayDigitWidth,
                    displayDigitHeight);
        }

    }

    protected void setLabel(String label){ this.label = label;}
}
