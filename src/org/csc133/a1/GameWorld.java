package org.csc133.a1;

import org.csc133.a1.gameobjects.GameObject;
import org.csc133.a1.gameobjects.fixed.RefuelingBlimp;
import org.csc133.a1.gameobjects.fixed.Skyscraper;
import org.csc133.a1.gameobjects.moveable.Bird;
import org.csc133.a1.gameobjects.moveable.Helicopter;
import org.csc133.a1.util.Constants;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class GameWorld {

    private Helicopter playerHelicopter;
    private Skyscraper skyscraper1;
    private Skyscraper skyscraper2;
    private Skyscraper skyscraper3;
    private Skyscraper skyscraper4;
    private Bird bird1;
    private Bird bird2;
    private RefuelingBlimp refuelingBlimp1;
    private RefuelingBlimp refuelingBlimp2;
    private int lives = Constants.numberOfLives;
    private int clock = Constants.clockStart;
    private boolean exitRequested = false;
    private ArrayList<GameObject> listOfGameObjects;
    private ArrayList<Float> skyscraperDistances;
    ArrayList<Float> refuelingBlimpDistances;

    public void init() {

        //Container to hold all the game objects in the game world.
        listOfGameObjects = new ArrayList<>();

        //Instantiate game objects and add them to the game world container.


        skyscraper1 = new Skyscraper(200, 200, 1);
        listOfGameObjects.add(skyscraper1);

        skyscraper2 = new Skyscraper(200, 800, 2);
        listOfGameObjects.add(skyscraper2);

        skyscraper3 = new Skyscraper(700, 800, 3);
        listOfGameObjects.add(skyscraper3);

        skyscraper4 = new Skyscraper(900, 400, 4);
        listOfGameObjects.add(skyscraper4);

        bird1 = new Bird();
        listOfGameObjects.add(bird1);

        bird2 = new Bird();
        listOfGameObjects.add(bird2);

        refuelingBlimp1 = new RefuelingBlimp();
        listOfGameObjects.add(refuelingBlimp1);

        refuelingBlimp2 = new RefuelingBlimp();
        listOfGameObjects.add(refuelingBlimp2);

        playerHelicopter = new Helicopter(skyscraper1.getLocationX(),
                skyscraper1.getLocationY());
        listOfGameObjects.add(playerHelicopter);

    }


    // additional methods here to
    // manipulate world objects and
    // related game state data


    //Increase speed by 3 if player is not already above max speed
    //Automatically reduce speed if damage limits maxSpeed less than current speed
    public void acceleratePlayerHelicopter() {

        //Case 1 to handle when helicopter has no damage and max speed is not limited
        if (playerHelicopter.getDamageLevel() == 0 &&
                playerHelicopter.getSpeed() < playerHelicopter.getMaximumSpeed()) {
            playerHelicopter.setSpeed(playerHelicopter.getSpeed() + 5);
            System.out.println("Increasing speed, helicopter speed = " +
                    playerHelicopter.getSpeed());


        } else {
            System.out.println("Max speed already reached. Current speed = " +
                    playerHelicopter.getSpeed());
        }

        //Case 2 to handle when helicopter is damaged and max speed is limited
        if (playerHelicopter.getDamageLevel() > 0 &&
                playerHelicopter.getDamageLevel() < Constants.helicopterMaxDamage)
        {
            if (playerHelicopter.getSpeed() < (playerHelicopter.getMaximumSpeed() *
                    (1 - (playerHelicopter.getDamageLevel() /
                            Constants.helicopterMaxDamage))) - 5)
            {
                playerHelicopter.setSpeed(playerHelicopter.getSpeed() + 5);
                System.out.println("Increasing speed, helicopter speed = " +
                        playerHelicopter.getSpeed());
            } else {
                System.out.println("Max speed already reached. Current speed = " +
                        playerHelicopter.getSpeed());
            }
        }

    }

    //Reduce speed by 3 if player is not already stopped.
    public void brakeHelicopter() {

        //Case 1: speed > 0, not stopped
        if (playerHelicopter.getSpeed() >= 5) {
            playerHelicopter.setSpeed(playerHelicopter.getSpeed() - 5);
            System.out.println("Decreasing speed, helicopter speed = " + playerHelicopter.getSpeed());
        }
        //Case 2: Speed already 0, stopped
        else {
            System.out.println("Can't slow down any more. Current speed = " + playerHelicopter.getSpeed());
        }

    }

    //Prints lives, clock, highestSkyscraperReached, fuelLevel, damageLevel
    public void generateDisplay() {
        System.out.println("lives: " + lives);
        System.out.println("clock: " + clock);
        System.out.println("highest sequence number passed: " +
                playerHelicopter.getLastSkyscraperReached());
        System.out.println("fuel level: " + playerHelicopter.getFuelLevel());
        System.out.println("damage level: " + playerHelicopter.getDamageLevel());
    }

    //Loops through all objects in the game world container and prints various
    //details about each object
    public void generateMap() {

        System.out.println("Map: ");

        for (int i = 0; i < listOfGameObjects.size(); i++) {
            System.out.println(listOfGameObjects.get(i));
        }
    }

    //Player collides with another helicopter causing damage, changing the color,
    //and reducing max speed.
    public void collideWithHelicopter() {
        float currentDamageLevel = playerHelicopter.getDamageLevel();

        //set new damage level after colliding with helicopter
        playerHelicopter.setDamageLevel(currentDamageLevel + Constants.damageFromCollisionWithHelicopter);
        System.out.println("You just crashed into another helicopter! Damage: "
                + playerHelicopter.getDamageLevel() + "%");
        System.out.println("Max speed reduced by: " +
                playerHelicopter.getDamageLevel() + "%");

        //set new color after taking damage
        playerHelicopter.setColor((int) (playerHelicopter.getColor()[0] *
                (1 - playerHelicopter.getDamageLevel() / 100)), 0, 0);


        //Check to see if speed needs to be reduced to reflect new max speed
        //after taking damage.
        if (playerHelicopter.getSpeed() > (playerHelicopter.getMaximumSpeed() *
                (1 - playerHelicopter.getDamageLevel() / 100))) {
            playerHelicopter.setSpeed((playerHelicopter.getMaximumSpeed() *
                    (1 - playerHelicopter.getDamageLevel() / 100)));
            System.out.println("Speed reduced to comply with damage level. Speed: " +
                    playerHelicopter.getSpeed());
        }

    }

    //Player collides with a bird, causing damage, changing the color,
    //and reducing max speed.
    public void collideWithBird() {
        float currentDamageLevel = playerHelicopter.getDamageLevel();

        //set new damage level after colliding with helicopter
        playerHelicopter.setDamageLevel(currentDamageLevel + Constants.damageFromCollisionWithBird);
        System.out.println("You just crashed into a bird! Damage: "
                + playerHelicopter.getDamageLevel() + "%");
        System.out.println("Max speed reduced by: " +
                playerHelicopter.getDamageLevel() + "%");

        //set new color after taking damage
        playerHelicopter.setColor((int) (playerHelicopter.getColor()[0] *
                (1 - playerHelicopter.getDamageLevel() / 100)), 0, 0);

        //Check to see if speed needs to be reduced to reflect new max speed
        //after taking damage.
        if (playerHelicopter.getSpeed() > (playerHelicopter.getMaximumSpeed() *
                (1 - playerHelicopter.getDamageLevel() / 100))) {
            playerHelicopter.setSpeed((playerHelicopter.getMaximumSpeed() *
                    (1 - playerHelicopter.getDamageLevel() / 100)));
            System.out.println("Speed reduced to comply with damage level. Speed: " +
                    playerHelicopter.getSpeed());
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
            for (int i = 0; i < listOfGameObjects.size(); i++) {
                if (listOfGameObjects.get(i) instanceof RefuelingBlimp) {
                    listOfRefuelingBlimps.add((RefuelingBlimp) listOfGameObjects.get(i));
                }
            }

            //get the size of closest refuelingBlimp,
            //there may be more than one within 100 units
            float sizeOfClosestRefuelingBlimp =
                    listOfRefuelingBlimps.get(getClosestRefuelingBlimp()).getSize();


            //check to see if helicopter has room to empty the refuelingBlimp
            if (playerHelicopter.getFuelLevel() +
                    sizeOfClosestRefuelingBlimp <= Constants.helicopterMaxFuel) {
                //fill helicopter and empty refuelingBlimp
                playerHelicopter.setFuelLevel(playerHelicopter.getFuelLevel() + sizeOfClosestRefuelingBlimp);
                listOfRefuelingBlimps.get(getClosestRefuelingBlimp()).setCapacity(0);
                System.out.println("You just docked with refueling blimp " +
                        (getClosestRefuelingBlimp() + 1) + ". Fuel level: " +
                        playerHelicopter.getFuelLevel() + " + " + sizeOfClosestRefuelingBlimp + " = " +
                        (playerHelicopter.getFuelLevel() + sizeOfClosestRefuelingBlimp));
            }

            //refuelingBlimp has more fuel than the helicopter has taken on
            else {

                //get how much fuel will be left over in reuling blimp
                float excessFuel = playerHelicopter.getFuelLevel() +
                        sizeOfClosestRefuelingBlimp - Constants.helicopterMaxFuel;

                //fill helicopter, subtract from capacity of refuelingBlimp
                playerHelicopter.setFuelLevel(Constants.helicopterMaxFuel);
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
        for (int i = 0; i < listOfGameObjects.size(); i++) {
            if (listOfGameObjects.get(i) instanceof RefuelingBlimp) {
                float distanceX = abs(playerHelicopter.getLocationX() - listOfGameObjects.get(i).getLocationX());
                float distanceY = abs(playerHelicopter.getLocationY() - listOfGameObjects.get(i).getLocationY());
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
            for (int i = 0; i < listOfGameObjects.size(); i++) {
                if (listOfGameObjects.get(i) instanceof Skyscraper) {
                    listOfSkyscrapers.add((Skyscraper) listOfGameObjects.get(i));
                }
            }

            //get the closest Skyscraper
            int closestSkyscraper =
                    listOfSkyscrapers.get(getClosestSkysraper()).getSequenceNumber();


            //check to see if closest skyscraper is next one to reach
            if (closestSkyscraper == (playerHelicopter.getLastSkyscraperReached() + 1)) {
                //update lastSkyscraperReached
                playerHelicopter.setLastSkyscraperReached
                        (playerHelicopter.getLastSkyscraperReached() + 1);
                System.out.println("You just flew over the next Skyscraper." +
                        "lastSkyscraperReached = " +
                        playerHelicopter.getLastSkyscraperReached());
            } else {
                System.out.println("Wrong skyscraper. This is sckyscraper " +
                        closestSkyscraper + ". You're supposed to be at " +
                        (playerHelicopter.getLastSkyscraperReached() + 1));
            }
            listOfSkyscrapers.clear();
        }
    }

    private int getClosestSkysraper() {
        skyscraperDistances = new ArrayList<>();

        //get distance for just the skyscrapers from the game world container
        for (int i = 0; i < listOfGameObjects.size(); i++) {
            if (listOfGameObjects.get(i) instanceof Skyscraper) {
                float distanceX = abs(playerHelicopter.getLocationX() - listOfGameObjects.get(i).getLocationX());
                float distanceY = abs(playerHelicopter.getLocationY() - listOfGameObjects.get(i).getLocationY());
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
        float currentStickAngle = playerHelicopter.getStickAngle();

        //check to see if min stick angle already reached
        if (currentStickAngle < -35f) {
            playerHelicopter.setStickAngle(-40f);
            System.out.println("Stick Angle can't be adjust any further in the" +
                    " left direction. Current stickAngle = " +
                    playerHelicopter.getStickAngle());
        }

        //if not min stick angle, adjust stick angle -5 degrees
        else {
            playerHelicopter.setStickAngle(currentStickAngle - 5f);
            System.out.println("StickAngle adjusted -5 degrees. Current " +
                    "stickAngle = " + playerHelicopter.getStickAngle());
        }
    }

    //change stick angle by +5 degrees (to the right)
    public void changeStickAngleRight() {
        float currentStickAngle = playerHelicopter.getStickAngle();

        //check to see if max stick angle already reached
        if (currentStickAngle > 35f) {
            playerHelicopter.setStickAngle(40f);
            System.out.println("Stick Angle can't be adjust any further in the" +
                    " right direction. Current stickAngle = " +
                    playerHelicopter.getStickAngle());
        }

        //if not max stick angle, adjust stick angle +5 degrees
        else {
            playerHelicopter.setStickAngle(currentStickAngle + 5f);
            System.out.println("StickAngle adjusted +5 degrees. Current " +
                    "stickAngle = " + playerHelicopter.getStickAngle());
        }
    }

    //After death, reset damage and fuel and continue where you were
    private void resetDamageAndFuel() {
        playerHelicopter.setDamageLevel(0f);
        playerHelicopter.setFuelLevel(30f);
        System.out.println("Damage and fuel reset.");
    }

    //simulate next frame
    //update all objects as needed
    public void tickClock() {
        clock += 1;

        //check to see if player has received max damage or fuel is zero
        //If so, lose a life and reset helicopter damage and fuel
        if (playerHelicopter.getDamageLevel() >= Constants.helicopterMaxDamage ||
                playerHelicopter.getFuelLevel() <= 0) {
            playerHelicopter.setSpeed(0);
            lives -= 1;
            System.out.println("You crashed and died. " + lives + " lives left");
            if (lives == 0) {
                System.out.println("Game over! Better luck next time.");
                System.exit(0);
            } else resetDamageAndFuel();
        }

        //if your reach the last skyscraper, you win!
        if (playerHelicopter.getLastSkyscraperReached() == 4) {
            System.out.println("Game over, you win! Total time = " + clock);
            System.exit(0);
        }

        //if fuelLevel and speed > zero and damageLevel is less than max
        //the helicopter moves
        if (playerHelicopter.getFuelLevel() > 0 && playerHelicopter.getSpeed() > 0f &&
                playerHelicopter.getDamageLevel() < Constants.helicopterMaxDamage) {
            playerHelicopter.move();
        }

        //update fuel level if fuel isn't empty and damage is less than max
        if (playerHelicopter.getFuelLevel() > 0 && playerHelicopter.getDamageLevel() < Constants.helicopterMaxDamage) {
            playerHelicopter.setFuelLevel(
                    playerHelicopter.getFuelLevel() -
                            playerHelicopter.getFuelConsumptionRate()
            );
        }

        //if stickAngle is not zero and damage is less than max, adjust heading
        if (playerHelicopter.getStickAngle() != 0 && playerHelicopter.getDamageLevel() <= Constants.helicopterMaxDamage)
            playerHelicopter.changeHeadingIfStickAngleNotZero();

        //automatically adjust bird heading randomly within +- 5 degrees
        //turn bird around if it goes out of bounds.
        bird1.adjustHeading();
        bird1.ifBirdOutOfBounds_turnBirdAround();
        bird1.move();

        bird2.adjustHeading();
        bird2.ifBirdOutOfBounds_turnBirdAround();
        bird2.move();
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
