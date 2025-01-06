import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* User.java
 *
 * This class represents a user in a social network. The code uses a static
 * HashMap to keep track of all users. The HashMap is keyed by the user's name
 * and can be searched using the find method.
 *
 * Users can:
 *  - friend another user
 * - unfriend another user
 * - leave the social network
 * - check if they are friends with another user
 */
public class User {
  /*
   * static HashMap to keep track of all users. Static means that there is only
   * one copy of the HashMap for all instances of the class.
   */
  public static HashMap<String, User> users = new HashMap<String, User>();
  public String name;
  public HashMap<String, User> adj = new HashMap<String, User>();
  public HashMap<String, User> fdj = new HashMap<String, User>();

  /*
   * Constructor for the User class. The constructor takes a String, nm, which
   * is the name of the user. The constructor adds the user to the static
   * HashMap. Warning: if a user with the same name already exists, the
   * constructor will not add the new user to the HashMap.
   */

  public User(String nm) {
    name = nm;
    if (!users.containsKey(name)) {
      users.put(name, this);
    }
  }

  /*
   * find
   * Given a String, nm, this method returns the User with that name. If no
   * such user exists, the method returns null.
   */

  public static User find(String nm) {
    return users.get(nm);
  }

  /*
   * friend
   * Given a String, f, this method will friend the user with that name. The
   * method returns the User that was friended. Friending adds the friendship to
   * adj and to the other user's adj. Friending a user that is already a friend
   * does not change the friendship.
   *
   */

  public User friend(String f) {
    User u = users.get(f);
    adj.put(u.name, u);
    u.adj.put(name, this);
    return u;
  }

  /*
   * unfriend
   * Given a String, f, this method will unfriend the user with that name. The
   * method returns the User that was unfriended. Unfriending removes the
   * friendship from adj and from the other user's adj.
   */

  public User unfriend(String f) {
    User u = users.get(f);
    adj.remove(u.name);
    u.adj.remove(this.name);
    return u;
  }

  /*
   * leave
   * This method removes the user from the social network. It removes the user
   * from the static HashMap and removes the user from all of their friends'
   * adj.
   */

  public void leave() {
    users.remove(name);
    for (User v : adj.values()) {
      v.adj.remove(name);
    }
  }

  /*
   * isFriend
   * Given a User, u, this method returns true if u is a friend of this user and
   * false otherwise.
   */

  public boolean isFriend(User u) {
    return adj.containsKey(u.name);
  }

  /**
   * Adds the specified user 'u' to the follow list of the current user instance.
   * If the user 'u' is not null, it is added to the 'follows' map, where the user's
   * name serves as the key and the user object itself is the value.
   *
   * @param u The user to be followed by the current user instance.
   */

  public void follow(User u) {
    if (u != null) {
      fdj.put(u.name, u);
    }
  }

  /**
   * Checks if the current user instance is following another specified user 'u'.
   * This is determined by checking if the user 'u's name is a key in the 'follows' map
   * which keeps track of all users that the current user is following.
   *
   * @param u The user to check against the current user's follow list.
   * @return true if the current user is following 'u', false otherwise.
   */

  public boolean doFollow(User u) {
    return fdj.containsKey(u.name);
  }

}
