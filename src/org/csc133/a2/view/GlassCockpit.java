package org.csc133.a2.view;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.table.TableLayout;
import org.csc133.a2.model.gameobjects.moveable.Helicopter;


public class GlassCockpit extends Container {

    GameClockComponent gameClock;
    FuelDisplayComponent fuel;
    DamageDisplayComponent damageDisplay;
    LivesDisplayComponent livesDisplay;
    LastSkyscraperDisplayComponent lastDisplay;
    HeadingDisplayComponent headingDisplay;

    public GlassCockpit(){
        super(new TableLayout(2,6));

        gameClock = new GameClockComponent();
        fuel = new FuelDisplayComponent();
        damageDisplay = new DamageDisplayComponent();
        livesDisplay = new LivesDisplayComponent();
        lastDisplay = new LastSkyscraperDisplayComponent();
        headingDisplay = new HeadingDisplayComponent();

        add( new Label("GAME CLOCK"));
        add( new Label("FUEL"));
        add( new Label("DMG"));
        add( new Label("LIVES"));
        add( new Label("LAST"));
        add( new Label("HDG"));
        add( gameClock);
        add( fuel);
        add( damageDisplay);
        add( livesDisplay);
        add( lastDisplay);
        add( headingDisplay);



//        add(FlowLayout.encloseCenter(new Label("GAME CLOCK"), new Label("FUEL"), new Label("DMG"),
//                new Label("LIVES"), new Label("LAST"), new Label("HDG")));
//        add(FlowLayout.encloseCenter(gameClock, fuel, damageDisplay, livesDisplay, lastDisplay, headingDisplay));

    }

    public void update(Helicopter playerHelicopter, int lives, int last){
        fuel.update((int)playerHelicopter.getFuelLevel());
        damageDisplay.update((int)playerHelicopter.getDamageLevel());
        livesDisplay.update(lives);
        lastDisplay.update(last);
        headingDisplay.update((int)playerHelicopter.getHeading());
        repaint();
    }
}
