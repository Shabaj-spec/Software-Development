import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class UserTest {
  private static String[] friendNames = {"Alice", "Bob", "Carol"};

  // clear the static users HashMap before each test
  @AfterEach
  void clearUsers() {
    User.users.clear();
  }

  // Test constructing a user adds them to the HashMap
  @Test
  void testConstructor() {
    User u = new User(friendNames[0]);
    assertEquals(friendNames[0], u.name, "Incorrect name");
    assertEquals(1, User.users.size(), "Incorrect size");
    assertEquals(u, User.users.get(friendNames[0]), "User not in HashMap");
  }

  // Verify that the find method returns the correct user or null for non-existent users
  @Test
  void testFind() {
    User u = new User(friendNames[0]);
    assertEquals(u, User.find(friendNames[0]), "Find should return the correct user");
    assertNull(User.find("NonExistentUser"), "Find should return null for non-existent user");
  }

  // Test the functionality of friending between users
  @Test
  void testFriend() {
    User alice = new User(friendNames[0]);
    User bob = new User(friendNames[1]);
    alice.friend(bob.name);
    assertTrue(alice.adj.containsKey(bob.name), "Alice should have Bob as a friend");
    assertTrue(bob.adj.containsKey(alice.name), "Bob should have Alice as a friend");
  }

  // Ensure that unfriending removes the user from each other's friend lists
  @Test
  void testUnfriend() {
    User alice = new User(friendNames[0]);
    User bob = new User(friendNames[1]);
    alice.friend(bob.name);
    alice.unfriend(bob.name);
    assertFalse(alice.adj.containsKey(bob.name), "Alice should not have Bob as a friend after unfriending");
    assertFalse(bob.adj.containsKey(alice.name), "Bob should not have Alice as a friend after being unfriended");
  }

  // Test if leaving correctly removes the user from the HashMap and friend lists
  @Test
  void testLeave() {
    User alice = new User(friendNames[0]);
    User bob = new User(friendNames[1]);
    alice.friend(bob.name);
    alice.leave();
    assertFalse(User.users.containsKey(alice.name), "Alice should be removed from the HashMap after leaving");
    assertFalse(bob.adj.containsKey(alice.name), "Alice should be removed from Bob's friends after leaving");
  }

  // Test the isFriend method to ensure it accurately checks friendship status
  @Test
  void testIsFriend() {
    User alice = new User(friendNames[0]);
    User bob = new User(friendNames[1]);
    assertFalse(alice.isFriend(bob), "Alice should not be friends with Bob initially");
    alice.friend(bob.name);
    assertTrue(alice.isFriend(bob), "Alice should be friends with Bob after friending");
  }

  // Verify that creating a user with an existing name doesn't duplicate entries
  @Test
  void testAddExistingUser() {
    User alice1 = new User(friendNames[0]);
    User alice2 = new User(friendNames[0]);
    assertEquals(1, User.users.size(), "Adding a user with an existing name should not create a new entry");
  }

  // Check that unfriending a non-existent user has no adverse effect
  @Test
  void testUnfriendNonExistingUser() {
    User alice = new User(friendNames[0]);
    alice.unfriend(friendNames[1]); // Unfriend non-existing friend
    assertFalse(alice.adj.containsKey(friendNames[1]), "Unfriending a non-existing user should have no effect");
  }

}