/**
 * File: AngryBot.java
 * Author: Alex Brodsky
 * Date: September 21, 2015
 * Purpose: CSCI 1110, Assignment 4 Solution
 *
 * Description: This class specifies the AngryBot class, a type of TimBot
 *              with an aggressive personality, focusing on attacking
 *              other districts.
 */

/**
 * The AngryBot class represents AngryBots and their behaviours on the
 * planet DohNat, extending the SpressoBot class.
 */
public class AngryBot extends SpressoBot {
  private static final int ATTACK_ENERGY_THRESHOLD = 2;
  private static final int EMPTY_DISTRICT_SCORE_PENALTY = 2000;

  /**
   * Constructs an AngryBot with a specified ID and initial energy.
   *
   * @param id    ID of the TimBot
   * @param jolts Initial amount of energy
   */
  public AngryBot(int id, int jolts) {
    super(id, jolts);
    personality = 'A';
  }

  /**
   * Determines the next move during the Move phase of each round. The AngryBot
   * may decide to move to a neighboring district or remain in the current one.
   *
   * @return One of District.NORTH, District.EAST, District.SOUTH, District.WEST,
   *         or District.CURRENT
   */
  @Override
  public int getNextMove() {
    if (energyLevel > ATTACK_ENERGY_THRESHOLD) {
      return chooseAttackMove();
    } else {
      return super.getNextMove();
    }
  }

  /**
   * Chooses the next move based on the AngryBot's strategy to attack.
   *
   * @return The direction to move towards the target district, or District.CURRENT
   *         if staying put is the better option.
   */
  private int chooseAttackMove() {
    int[] scores = calculateMoveScores();
    int bestMove = findMinimumScoreMove(scores);
    useMoveEnergy(bestMove);
    return bestMove;
  }

  /**
   * Calculates scores for each possible move.
   *
   * @return Array of scores corresponding to each direction, including the current district.
   */
  private int[] calculateMoveScores() {
    int[] scores = new int[botsSensed.length];
    for (int i = 0; i < scores.length; i++) {
      scores[i] = spressoSensed[i];
      if (!botsSensed[i]) {
        scores[i] += EMPTY_DISTRICT_SCORE_PENALTY;
      }
    }
    return scores;
  }

  /**
   * Finds the move with the minimum score, indicating the best move.
   *
   * @param scores Array of scores for each move.
   * @return The direction with the minimum score.
   */
  private int findMinimumScoreMove(int[] scores) {
    int bestScore = Integer.MAX_VALUE;
    int bestMove = District.CURRENT;
    for (int i = 0; i < scores.length; i++) {
      if (bestScore > scores[i]) {
        bestScore = scores[i];
        bestMove = i;
      }
    }
    return bestMove;
  }
}
