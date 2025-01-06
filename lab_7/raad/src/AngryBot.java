/**
   File: AngryBot.java
   Author: Alex Brodsky
   Date: September 21, 2015
   Purpose: CSCI 1110, Assignment 4 Solution

   Description: This class specifies the AngryBot class.
*/


/**
   This is the AngryBot class for representing AngryBots and their 
   behaviours on the planet DohNat.  
*/
public class AngryBot extends SpressoBot {

  public static final int EMPTY_DISTRICT_PENALTY = 2000;
  public static final int MIN_ENERGY_LEVEL_FOR_ATTACK = 2;

  /**
   * This is the only constructor for this class.  This constructor
   * initializes the Tibot and sets its ID and the initial amount of
   * energy that it has.
   * <p>
   * parameter: id    : ID of the TimBot
   * jolts : Initial amount of energy
   */
  public AngryBot(int id, int jolts) {
    super(id, jolts);
    personality = 'A';
  }


  /**
   * This method is called during the Move phase of each round and
   * requires the TimBot to decide whether or not to move to another
   * district.  If the TimBot wishes to move, it returns, District.NORTH,
   * District.EAST, District.SOUTH, or District.WEST, indicating which
   * direction it wishes to move.  If it does not wish to move, it
   * returns District.CURRENT.
   * <p>
   * returns: the one of District.NORTH, District.EAST, District.SOUTH,
   * District.WEST, or District.CURRENT
   */
  public int getNextMove() {
    int[] scores = new int[botsSensed.length];
    int move = District.CURRENT;

    if (energyLevel > MIN_ENERGY_LEVEL_FOR_ATTACK) {
      for (int i = 0; i < scores.length; i++) {
        scores[i] = spressoSensed[i];
        if (!botsSensed[i]) {
          scores[i] += EMPTY_DISTRICT_PENALTY;
        }
      }

      int min = scores[District.CURRENT] + 1;
      for (int i = 0; i < scores.length; i++) {
        if (min > scores[i]) {
          min = scores[i];
          move = i;
        }
      }

      if (move != District.CURRENT) {
        energyLevel--;
      }
    } else {
      move = super.getNextMove();
    }
    return move;
  }
}