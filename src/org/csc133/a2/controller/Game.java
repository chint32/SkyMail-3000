package org.csc133.a2.controller;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import org.csc133.a2.model.GameWorld;
import org.csc133.a2.view.GlassCockpit;
import org.csc133.a2.view.MapView;


public class Game extends Form implements Runnable{


    private GameWorld gw;
    private MapView map;
    private GlassCockpit hud;


    public Game() {

        map = new MapView();
        hud = new GlassCockpit();

        setTitle("SkyMail-3000");
        setLayout(new BorderLayout());

        add(BorderLayout.NORTH, hud);
        add(BorderLayout.CENTER, map);

        gw = new GameWorld(map, hud);
        gw.init();

        play();

        show();
    }




    private void play() {

        // code here to accept and
        // execute user commands that
        // operate on the game world
        //(refer to “Appendix - CN1
        //Notes” for accepting
        //keyboard commands via a text
        //field located on the form)

        final TextField myTextField = new TextField();
        this.add(BorderLayout.SOUTH, myTextField);



        myTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                String sCommand = myTextField.getText().toString();
                if (sCommand == null || sCommand.equals(""))
                    return;
                myTextField.clear();

                //accept user commands through textfield and call appropriate
                //method in gameworld
                switch (sCommand.charAt(0)) {

                    case 'a':
                        gw.acceleratePlayerHelicopter();
                        break;

                    case 'b':
                        gw.brakeHelicopter();
                        break;

                    case 'c':
                        gw.collideWithHelicopter();
                        break;

                    case 'd':
                        gw.generateDisplay();
                        break;

                    case 'e':
                        gw.dockWithRefuelingBlimp();
                        break;

                    case 'g':
                        gw.collideWithBird();
                        break;

                    case 'l':
                        gw.changeStickAngleLeft();
                        break;

                    case 'm':
                        gw.generateMap();
                        break;

                    case 'n':
                        gw.cancelExit();
                        break;

                    case 'r':
                        gw.changeStickAngleRight();
                        break;

                    case 't':
                        gw.tickClock();
                        break;

                    case 'x':
                        gw.exit();
                        break;

                    case 'y':
                        gw.confirmExit();
                        break;

                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        gw.passOverSkyscraper();
                        break;

                    default:
                        System.out.println("Unregistered command. Please enter a different command.");
                }
            }
        });
    }

    @Override
    public void run() {

    }
}
