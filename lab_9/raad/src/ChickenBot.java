/**
 * File: ChickenBot.java
 * Author: Alex Brodsky
 * Date: September 21, 2015
 * Purpose: CSCI 1110, Assignment 4 Solution
 *
 * Description: This class specifies the ChickenBot class for representing
 *              ChickenBots and their behaviors on the planet DohNat.
 */

public class ChickenBot extends TimBot {
  private static final int SCORE_FOR_BOT = 2000;
  private static final int SCORE_FOR_ADJACENT_BOT = 1000;

  /**
   * Constructs a ChickenBot with a specified ID and initial energy.
   *
   * @param id    ID of the TimBot
   * @param jolts Initial amount of energy
   */
  public ChickenBot(int id, int jolts) {
    super(id, jolts);
    personality = 'C';
  }

  /**
   * Determines the next move during the Move phase of each round.
   *
   * @return One of District.NORTH, District.EAST, District.SOUTH,
   *         District.WEST, or District.CURRENT
   */
  @Override
  public int getNextMove() {
    if (energyLevel <= 0) {
      return District.CURRENT; // Stay if no energy
    }

    int[] scores = calculateMoveScores();
    return selectMoveWithMinimumScore(scores);
  }

  /**
   * Calculates scores for each possible move.
   *
   * @return Array of scores corresponding to each direction.
   */
  private int[] calculateMoveScores() {
    int[] scores = new int[botsSensed.length];
    int adj = 0;

    for (int i = 0; i < scores.length; i++) {
      scores[i] = spressoSensed[i];
      if (i != District.CURRENT && botsSensed[i]) {
        scores[i] += SCORE_FOR_BOT;
        adj = SCORE_FOR_ADJACENT_BOT;
      }
    }
    scores[District.CURRENT] += adj;

    return scores;
  }

  /**
   * Selects the move with the minimum score.
   *
   * @param scores Array of scores for each move.
   * @return The direction with the minimum score.
   */
  private int selectMoveWithMinimumScore(int[] scores) {
    int minScore = Integer.MAX_VALUE;
    int move = District.CURRENT;

    for (int i = 0; i < scores.length; i++) {
      if (minScore > scores[i]) {
        minScore = scores[i];
        move = i;
      }
    }

    useMoveEnergy(move);
    return move;
  }
}

