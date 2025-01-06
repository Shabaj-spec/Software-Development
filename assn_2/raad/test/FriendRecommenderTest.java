import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FriendRecommenderTest {
  private static String[] friendNames = {"Alice", "Bob", "Carol"};

  // clear the static users HashMap before each test
  @AfterEach
  void clearUsers() {
    User.users.clear();
  }

  // recommending friends between two users where the second has another friend should recommend that friend
  @Test
  void testTwoFriends() {
    User u = new User(friendNames[0]);
    User f = new User(friendNames[1]);
    User g = new User(friendNames[2]);
    u.friend(f.name);
    f.friend(g.name);
    FriendRecommender fr = new FriendRecommender();
    ArrayList<String> al = new ArrayList<String>();
    fr.makeRecommendations(u, f, al);
    assertEquals(1, al.size(), "Wrong recommendation count");
    assertEquals(u.name + " and " + g.name + " should be friends", al.get(0), "Incorrect recommendation");
  }

  // Test making recommendations based on mutual friends
  @Test
  void testMakeRecommendations() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    User carol = new User("Carol");
    User dave = new User("Dave");
    alice.friend(bob.name);
    bob.friend(carol.name);
    bob.friend(dave.name);
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, bob, recommendations);
    assertEquals(2, recommendations.size(), "Should have two recommendations for Alice");
    assertTrue(recommendations.contains("Carol and Alice should be friends"), "Carol should be recommended to Alice");
    assertTrue(recommendations.contains("Dave and Alice should be friends"), "Dave should be recommended to Alice");
  }

  // Test recommendation process for mutual friends
  @Test
  void testRecommendationForMutualFriends() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    User carol = new User("Carol");
    alice.friend(bob.name);
    bob.friend(carol.name);
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, bob, recommendations);
    assertTrue(recommendations.contains("Carol and Alice should be friends"), "Mutual friends should be recommended");
  }

  // Test no recommendations are made when there are no mutual friends
  @Test
  void testNoRecommendationWhenNoMutualFriends() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, bob, recommendations);
    assertTrue(recommendations.isEmpty(), "Should not recommend when there are no mutual friends");
  }

  // Ensure that the recommendations are unique and not repeated
  @Test
  void testRecommendationsAreUnique() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    User carol = new User("Carol");
    alice.friend(bob.name);
    bob.friend(carol.name);
    carol.friend(alice.name);
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, bob, recommendations);
    assertEquals(1, recommendations.size(), "Recommendations should be unique");
  }

  // Test that a user is not recommended to themselves
  @Test
  void testNoSelfRecommendation() {
    User alice = new User("Alice");
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, alice, recommendations);
    assertTrue(recommendations.isEmpty(), "User should not be recommended to themselves");
  }

  // Test that no recommendations are made when a user leaves the network
  @Test
  void testRecommendationsWhenUserLeavesNetwork() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    alice.friend(bob.name);
    bob.leave();
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, bob, recommendations);
    assertTrue(recommendations.isEmpty(), "Should not recommend when a user leaves the network");
  }

  // Test friend recommendations when a user has multiple friends
  @Test
  void testRecommendationWithMultipleFriends() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    User carol = new User("Carol");
    User dave = new User("Dave");
    alice.friend(bob.name);
    alice.friend(carol.name);
    bob.friend(dave.name);
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, bob, recommendations);
    assertTrue(recommendations.contains("Dave and Alice should be friends"), "Dave should be recommended to Alice");
    assertFalse(recommendations.contains("Carol and Alice should be friends"), "Carol should not be recommended as she is already a friend");
  }

  // Verify that the recommendation process does not produce duplicate entries
  @Test
  void testNoDuplicateRecommendations() {
    User alice = new User("Alice");
    User bob = new User("Bob");
    User carol = new User("Carol");
    alice.friend(bob.name);
    bob.friend(carol.name);
    carol.friend(alice.name);
    FriendRecommender recommender = new FriendRecommender();
    ArrayList<String> recommendations = new ArrayList<>();
    recommender.makeRecommendations(alice, bob, recommendations);
    recommender.makeRecommendations(alice, carol, recommendations);
    assertEquals(1, recommendations.size(), "There should be no duplicate recommendations");
  }

}