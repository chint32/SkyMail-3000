package org.csc133.a2;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class Game extends Form {

    private GameWorld gw;

    public Game() {

        gw = new GameWorld();
        gw.init();
        play();

    }

    private void play() {

        // code here to accept and
        // execute user commands that
        // operate on the game world
        //(refer to “Appendix - CN1
        //Notes” for accepting
        //keyboard commands via a text
        //field located on the form)

        Label myLabel = new Label("Enter a Command:");
        this.addComponent(myLabel);

        final TextField myTextField = new TextField();
        this.addComponent(myTextField);
        this.show();

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
}
