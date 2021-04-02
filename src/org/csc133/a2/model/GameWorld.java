package org.csc133.a2.model;

import org.csc133.a2.model.gameobjects.GameObject;
import org.csc133.a2.model.gameobjects.GameObjectCollection;
import org.csc133.a2.model.gameobjects.fixed.RefuelingBlimp;
import org.csc133.a2.model.gameobjects.fixed.Skyscraper;
import org.csc133.a2.model.gameobjects.moveable.Bird;
import org.csc133.a2.model.gameobjects.moveable.Helicopter;
import org.csc133.a2.util.Constants;
import org.csc133.a2.view.GlassCockpit;
import org.csc133.a2.view.MapView;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class GameWorld {


    private static final int NUMBER_OF_BIRDS = 5;
    private static final int NUMBER_OF_REFUELING_BLIMPS = 4;

    private GameObjectCollection gameObjectCollection = new GameObjectCollection();

    private int playerStartLocationX;
    private int playerStartLocationY;
    private int lives = Constants.numberOfLives;
    private int clock = Constants.clockStart;
    private boolean exitRequested = false;
    private ArrayList<Float> skyscraperDistances;
    ArrayList<Float> refuelingBlimpDistances;

    private final MapView map;
    private final GlassCockpit hud;


    public GameWorld(MapView map, GlassCockpit hud) {
        this.map = map;
        this.hud = hud;
    }

    public void init() {

        initSkyscrapers();
        initRefuelingBlimps();
        initPlayerHelicopter();
        initBirds();
        hud.update(getPlayerHelicopter(), lives, getPlayerHelicopter().getLastSkyscraperReached());

    }

    private void initBirds() {
        for (int i = 0; i < GameWorld.NUMBER_OF_BIRDS; ++i) {
            Bird bird = new Bird();
            gameObjectCollection.add(bird);
        }
    }


    private void initSkyscrapers() {

        Skyscraper skyscraper = new Skyscraper(200, 200, 1);
        gameObjectCollection.add(skyscraper);

        skyscraper = new Skyscraper(200, 800, 2);
        gameObjectCollection.add(skyscraper);

        skyscraper = new Skyscraper(700, 800, 3);
        gameObjectCollection.add(skyscraper);

        skyscraper = new Skyscraper(900, 400, 4);
        gameObjectCollection.add(skyscraper);

        System.out.println("Skyscrapers Initialized...");
    }

    private void initRefuelingBlimps() {
        for (int i = 0; i < GameWorld.NUMBER_OF_REFUELING_BLIMPS; ++i) {
            RefuelingBlimp rb = new RefuelingBlimp();
            gameObjectCollection.add(rb);
        }

        System.out.println("RefuelingBlimps Initialized...");
    }

    private void initPlayerHelicopter(){

        Skyscraper firstSkyScraper = null;
        for (GameObject potentialFirstSkyscraper : gameObjectCollection) {
            if (potentialFirstSkyscraper instanceof Skyscraper && ((Skyscraper) potentialFirstSkyscraper).getSequenceNumber() == 1) {
                firstSkyScraper = (Skyscraper) potentialFirstSkyscraper;
            }
        }
        playerStartLocationX = (int)firstSkyScraper.getLocationX();
        playerStartLocationY = (int)firstSkyScraper.getLocationY();
        Helicopter playerHelicopter = new Helicopter(playerStartLocationX, playerStartLocationY);
        gameObjectCollection.add(playerHelicopter);


        System.out.println("Player Helicopter Initialized...");
    }



    private Helicopter getPlayerHelicopter(){
        Helicopter playerHelicopter = null;
        for(GameObject go : gameObjectCollection){
            if (go instanceof Helicopter)
                playerHelicopter = (Helicopter) go;
        }
        return playerHelicopter;
    }



    //Increase speed by 3 if player is not already above max speed
    //Automatically reduce speed if damage limits maxSpeed less than current speed
    public void acceleratePlayerHelicopter() {

        //Case 1 to handle when helicopter has no damage and max speed is not limited
        if (getPlayerHelicopter().getDamageLevel() == 0 &&
               getPlayerHelicopter().getSpeed() < getPlayerHelicopter().getMaximumSpeed()) {
            getPlayerHelicopter().setSpeed(getPlayerHelicopter().getSpeed() + 5);
            System.out.println("Increasing speed, helicopter speed = " +
                    getPlayerHelicopter().getSpeed());


        } else {
            System.out.println("Max speed already reached. Current speed = " +
                    getPlayerHelicopter().getSpeed());
        }

        //Case 2 to handle when helicopter is damaged and max speed is limited
        if (getPlayerHelicopter().getDamageLevel() > 0 &&
                getPlayerHelicopter().getDamageLevel() < Constants.helicopterMaxDamage) {
            if (getPlayerHelicopter().getSpeed() < (getPlayerHelicopter().getMaximumSpeed() *
                    (1 - (getPlayerHelicopter().getDamageLevel() /
                            Constants.helicopterMaxDamage))) - 5) {
                getPlayerHelicopter().setSpeed(getPlayerHelicopter().getSpeed() + 5);
                System.out.println("Increasing speed, helicopter speed = " +
                        getPlayerHelicopter().getSpeed());
            } else {
                System.out.println("Max speed already reached. Current speed = " +
                        getPlayerHelicopter().getSpeed());
            }
        }

    }

    //Reduce speed by 3 if player is not already stopped.
    public void brakeHelicopter() {

        //Case 1: speed > 0, not stopped
        if (getPlayerHelicopter().getSpeed() >= 5) {
            getPlayerHelicopter().setSpeed(getPlayerHelicopter().getSpeed() - 5);
            System.out.println("Decreasing speed, helicopter speed = " + getPlayerHelicopter().getSpeed());
        }
        //Case 2: Speed already 0, stopped
        else {
            System.out.println("Can't slow down any more. Current speed = " + getPlayerHelicopter().getSpeed());
        }

    }

    //Prints lives, clock, highestSkyscraperReached, fuelLevel, damageLevel
    public void generateDisplay() {
        System.out.println("lives: " + lives);
        System.out.println("clock: " + clock);
        System.out.println("highest sequence number passed: " +
                getPlayerHelicopter().getLastSkyscraperReached());
        System.out.println("fuel level: " + getPlayerHelicopter().getFuelLevel());
        System.out.println("damage level: " + getPlayerHelicopter().getDamageLevel());
    }

    //Loops through all objects in the game world container and prints various
    //details about each object
    public void generateMap() {

        System.out.println("Map: ");

        for (int i = 0; i < gameObjectCollection.size(); i++) {
            System.out.println(gameObjectCollection.get(i));
        }
    }

    //Player collides with another helicopter causing damage, changing the color,
    //and reducing max speed.
    public void collideWithHelicopter() {
        float currentDamageLevel = getPlayerHelicopter().getDamageLevel();

        //set new damage level after colliding with helicopter
        getPlayerHelicopter().setDamageLevel(currentDamageLevel + Constants.damageFromCollisionWithHelicopter);
        System.out.println("You just crashed into another helicopter! Damage: "
                + getPlayerHelicopter().getDamageLevel() + "%");
        System.out.println("Max speed reduced by: " +
                getPlayerHelicopter().getDamageLevel() + "%");

        //set new color after taking damage
        getPlayerHelicopter().setColor((int) (getPlayerHelicopter().getColor()[0] *
                (1 - getPlayerHelicopter().getDamageLevel() / 100)), 0, 0);


        //Check to see if speed needs to be reduced to reflect new max speed
        //after taking damage.
        if (getPlayerHelicopter().getSpeed() > (getPlayerHelicopter().getMaximumSpeed() *
                (1 - getPlayerHelicopter().getDamageLevel() / 100))) {
            getPlayerHelicopter().setSpeed((getPlayerHelicopter().getMaximumSpeed() *
                    (1 - getPlayerHelicopter().getDamageLevel() / 100)));
            System.out.println("Speed reduced to comply with damage level. Speed: " +
                    getPlayerHelicopter().getSpeed());
        }

    }

    //Player collides with a bird, causing damage, changing the color,
    //and reducing max speed.
    public void collideWithBird() {
        float currentDamageLevel = getPlayerHelicopter().getDamageLevel();

        //set new damage level after colliding with helicopter
        getPlayerHelicopter().setDamageLevel(currentDamageLevel + Constants.damageFromCollisionWithBird);
        System.out.println("You just crashed into a bird! Damage: "
                + getPlayerHelicopter().getDamageLevel() + "%");
        System.out.println("Max speed reduced by: " +
                getPlayerHelicopter().getDamageLevel() + "%");

        //set new color after taking damage
        getPlayerHelicopter().setColor((int) (getPlayerHelicopter().getColor()[0] *
                (1 - getPlayerHelicopter().getDamageLevel() / 100)), 0, 0);

        //Check to see if speed needs to be reduced to reflect new max speed
        //after taking damage.
        if (getPlayerHelicopter().getSpeed() > (getPlayerHelicopter().getMaximumSpeed() *
                (1 - getPlayerHelicopter().getDamageLevel() / 100))) {
            getPlayerHelicopter().setSpeed((getPlayerHelicopter().getMaximumSpeed() *
                    (1 - getPlayerHelicopter().getDamageLevel() / 100)));
            System.out.println("Speed reduced to comply with damage level. Speed: " +
                    getPlayerHelicopter().getSpeed());
        }
    }

    //player docks with refuelingBlimp, giving fuel to the player
    //and lowering the capacity of the refuelingBlimp.
    public void dockWithRefuelingBlimp() {

        //Case 1: No refuelingBlimps with 100 units
        if (getClosestRefuelingBlimp() == -1) {
            System.out.println("No refueling blimps are close enough to dock with.");
        }

        //Case 2: There is a refueling blimp within 100 units
        else {

            //get just the refuelingBlimps from the list of world objects
            ArrayList<RefuelingBlimp> listOfRefuelingBlimps = new ArrayList<>();
            for (int i = 0; i < gameObjectCollection.size(); i++) {
                if (gameObjectCollection.get(i) instanceof RefuelingBlimp) {
                    listOfRefuelingBlimps.add((RefuelingBlimp) gameObjectCollection.get(i));
                }
            }

            //get the size of closest refuelingBlimp,
            //there may be more than one within 100 units
            float sizeOfClosestRefuelingBlimp =
                    listOfRefuelingBlimps.get(getClosestRefuelingBlimp()).getSize();


            //check to see if helicopter has room to empty the refuelingBlimp
            if (getPlayerHelicopter().getFuelLevel() +
                    sizeOfClosestRefuelingBlimp <= Constants.helicopterMaxFuel) {
                //fill helicopter and empty refuelingBlimp
                getPlayerHelicopter().setFuelLevel(getPlayerHelicopter().getFuelLevel() + sizeOfClosestRefuelingBlimp);
                listOfRefuelingBlimps.get(getClosestRefuelingBlimp()).setCapacity(0);
                System.out.println("You just docked with refueling blimp " +
                        (getClosestRefuelingBlimp() + 1) + ". Fuel level: " +
                        getPlayerHelicopter().getFuelLevel() + " + " + sizeOfClosestRefuelingBlimp + " = " +
                        (getPlayerHelicopter().getFuelLevel() + sizeOfClosestRefuelingBlimp));
            }

            //refuelingBlimp has more fuel than the helicopter has taken on
            else {

                //get how much fuel will be left over in reuling blimp
                float excessFuel = getPlayerHelicopter().getFuelLevel() +
                        sizeOfClosestRefuelingBlimp - Constants.helicopterMaxFuel;

                //fill helicopter, subtract from capacity of refuelingBlimp
                getPlayerHelicopter().setFuelLevel(Constants.helicopterMaxFuel);
                listOfRefuelingBlimps.get(getClosestRefuelingBlimp()).setCapacity(excessFuel);
                System.out.println("You just docked with refueling blimp " +
                        (getClosestRefuelingBlimp() + 1) + ". Fuel level: " +
                        Constants.helicopterMaxFuel);

            }
            listOfRefuelingBlimps.clear();
        }
    }

    //Get just refuelingBlimps from world objects container.
    //For each, check to see if sum of distanceX and distanceY is less than 100.
    //If multiple refuelingBlimps are within 100, return the closest one.
    private int getClosestRefuelingBlimp() {
        refuelingBlimpDistances = new ArrayList<>();
        for (int i = 0; i < gameObjectCollection.size(); i++) {
            if (gameObjectCollection.get(i) instanceof RefuelingBlimp) {
                float distanceX = abs(getPlayerHelicopter().getLocationX() - gameObjectCollection.get(i).getLocationX());
                float distanceY = abs(getPlayerHelicopter().getLocationY() - gameObjectCollection.get(i).getLocationY());
                float totalDistance = distanceX + distanceY;
                if (totalDistance < 100)
                    refuelingBlimpDistances.add(totalDistance);
            }
        }
        return indexOfSmallest(refuelingBlimpDistances);
    }

    //Pass over skyscraper to update lastSkyscraperReached
    public void passOverSkyscraper() {

        //Case 1: No Skyscrapers exist in the world
        if (getClosestSkysraper() == -1) {
            System.out.println("No skyscrapers exist.");
        }

        //Case 2: There are Skyscrapers
        else {

            //get just the Skyscrapers from the list of world objects
            ArrayList<Skyscraper> listOfSkyscrapers = new ArrayList<>();
            for (int i = 0; i < gameObjectCollection.size(); i++) {
                if (gameObjectCollection.get(i) instanceof Skyscraper) {
                    listOfSkyscrapers.add((Skyscraper) gameObjectCollection.get(i));
                }
            }

            //get the closest Skyscraper
            int closestSkyscraper =
                    listOfSkyscrapers.get(getClosestSkysraper()).getSequenceNumber();


            //check to see if closest skyscraper is next one to reach
            if (closestSkyscraper == (getPlayerHelicopter().getLastSkyscraperReached() + 1)) {
                //update lastSkyscraperReached
                getPlayerHelicopter().setLastSkyscraperReached
                        (getPlayerHelicopter().getLastSkyscraperReached() + 1);
                System.out.println("You just flew over the next Skyscraper." +
                        "lastSkyscraperReached = " +
                        getPlayerHelicopter().getLastSkyscraperReached());
            } else {
                System.out.println("Wrong skyscraper. This is sckyscraper " +
                        closestSkyscraper + ". You're supposed to be at " +
                        (getPlayerHelicopter().getLastSkyscraperReached() + 1));
            }
            listOfSkyscrapers.clear();
        }
    }

    private int getClosestSkysraper() {
        skyscraperDistances = new ArrayList<>();

        //get distance for just the skyscrapers from the game world container
        for (int i = 0; i < gameObjectCollection.size(); i++) {
            if (gameObjectCollection.get(i) instanceof Skyscraper) {
                float distanceX = abs(getPlayerHelicopter().getLocationX() - gameObjectCollection.get(i).getLocationX());
                float distanceY = abs(getPlayerHelicopter().getLocationY() - gameObjectCollection.get(i).getLocationY());
                float totalDistance = distanceX + distanceY;
                skyscraperDistances.add(totalDistance);
            }
        }

        //if any of the distances are less than 100,
        //return the index of the smallest one
        if (skyscraperDistances.get(indexOfSmallest(skyscraperDistances)) < 100)
            return indexOfSmallest(skyscraperDistances);

            //otherwise return -1 to signal no skyscrapers within 100 units
        else return -1;
    }

    //returns the index of the smallest float in the array
    private static int indexOfSmallest(ArrayList<Float> array) {

        // add this
        if (array.size() == 0)
            return -1;

        int index = 0;
        float min = array.get(index);

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) <= min) {
                min = array.get(i);
                index = i;
            }
        }
        return index;
    }

    //change stick angle by -5 degrees (to the left)
    public void changeStickAngleLeft() {
        float currentStickAngle = getPlayerHelicopter().getStickAngle();

        //check to see if min stick angle already reached
        if (currentStickAngle < -35f) {
            getPlayerHelicopter().setStickAngle(-40f);
            System.out.println("Stick Angle can't be adjust any further in the" +
                    " left direction. Current stickAngle = " +
                    getPlayerHelicopter().getStickAngle());
        }

        //if not min stick angle, adjust stick angle -5 degrees
        else {
            getPlayerHelicopter().setStickAngle(currentStickAngle - 5f);
            System.out.println("StickAngle adjusted -5 degrees. Current " +
                    "stickAngle = " + getPlayerHelicopter().getStickAngle());
        }
    }

    //change stick angle by +5 degrees (to the right)
    public void changeStickAngleRight() {
        float currentStickAngle = getPlayerHelicopter().getStickAngle();

        //check to see if max stick angle already reached
        if (currentStickAngle > 35f) {
            getPlayerHelicopter().setStickAngle(40f);
            System.out.println("Stick Angle can't be adjust any further in the" +
                    " right direction. Current stickAngle = " +
                    getPlayerHelicopter().getStickAngle());
        }

        //if not max stick angle, adjust stick angle +5 degrees
        else {
            getPlayerHelicopter().setStickAngle(currentStickAngle + 5f);
            System.out.println("StickAngle adjusted +5 degrees. Current " +
                    "stickAngle = " + getPlayerHelicopter().getStickAngle());
        }
    }

    //After death, reset damage and fuel and continue where you were
    private void resetDamageAndFuel() {
        getPlayerHelicopter().setDamageLevel(0f);
        getPlayerHelicopter().setFuelLevel(30f);
        System.out.println("Damage and fuel reset.");
    }

    //simulate next frame
    //update all objects as needed
    public void tickClock() {
        clock += 1;

        //check to see if player has received max damage or fuel is zero
        //If so, lose a life and reset helicopter damage and fuel
        if (getPlayerHelicopter().getDamageLevel() >= Constants.helicopterMaxDamage ||
                getPlayerHelicopter().getFuelLevel() <= 0) {
            getPlayerHelicopter().setSpeed(0);
            lives -= 1;
            System.out.println("You crashed and died. " + lives + " lives left");
            if (lives == 0) {
                System.out.println("Game over! Better luck next time.");
                System.exit(0);
            } else resetDamageAndFuel();
        }

        //if your reach the last skyscraper, you win!
        if (getPlayerHelicopter().getLastSkyscraperReached() == 4) {
            System.out.println("Game over, you win! Total time = " + clock);
            System.exit(0);
        }

        //if fuelLevel and speed > zero and damageLevel is less than max
        //the helicopter moves
        if (getPlayerHelicopter().getFuelLevel() > 0 && getPlayerHelicopter().getSpeed() > 0f &&
                getPlayerHelicopter().getDamageLevel() < Constants.helicopterMaxDamage) {
            getPlayerHelicopter().move();
        }

        //update fuel level if fuel isn't empty and damage is less than max
        if (getPlayerHelicopter().getFuelLevel() > 0 && getPlayerHelicopter().getDamageLevel() < Constants.helicopterMaxDamage) {
            getPlayerHelicopter().setFuelLevel(
                    getPlayerHelicopter().getFuelLevel() -
                            getPlayerHelicopter().getFuelConsumptionRate()
            );
        }

        //if stickAngle is not zero and damage is less than max, adjust heading
        if (getPlayerHelicopter().getStickAngle() != 0 && getPlayerHelicopter().getDamageLevel() <= Constants.helicopterMaxDamage)
            getPlayerHelicopter().changeHeadingIfStickAngleNotZero();

        //automatically adjust bird heading randomly within +- 5 degrees
        //turn bird around if it goes out of bounds.
        updateBirds();


        hud.update(getPlayerHelicopter(), lives, getPlayerHelicopter().getLastSkyscraperReached());
    }

    private void updateBirds(){
        Bird bird = null;
        for(GameObject go : gameObjectCollection){
            if(go instanceof Bird){
                bird = (Bird)go;
                bird.adjustHeading();
                bird.ifBirdOutOfBounds_turnBirdAround();
                bird.move();
            }
        }
    }



    //confirm exiting the game
    public void confirmExit() {
        if (exitRequested) {
            System.out.println("Exit confirmed.");
            System.exit(0);
        }
    }

    //cancel exiting the game
    public void cancelExit() {
        System.out.println("Exit canceled.");
        exitRequested = false;

    }

    //request to exit the game
    public void exit() {
        System.out.println("Are you sure you want to exit? 'Y' for yes, 'N' for no");
        exitRequested = true;
    }
}
