import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/* FriendRecommender.java
 *
 * This class is used to make friend recommendations for users of a social
 * network. The code is incomplete and contains bugs.
 *
 */
public class FriendRecommender {

  /*
   * main
   * This method is used to read input from the user and print the output of the
   * friend recommendations. The input is read from the standard input and the
   * output is printed to the standard output.
   */
  public static void main(String[] args) {
    ArrayList<String> output = new FriendRecommender().compute(new Scanner(System.in));
    for (String s : output) {
      System.out.println(s);
    }
  }

  /*
   * compute
   * This method takes a Scanner, input, and returns an ArrayList of Strings.
   * The Scanner contains the input from the user. The method reads the input
   * and makes friend recommendations based on the input. The method returns an
   * ArrayList of Strings that contains the friend recommendations.
   */
  public ArrayList<String> compute(Scanner input) {
    ArrayList<String> list = new ArrayList<String>();
    String error = "";
    for (String s = input.nextLine(); !s.equals("end"); s = input.nextLine()) {
      Scanner line = new Scanner(s);
      String name = line.next();
      User u = User.find(name);

      try {
        switch (line.next()) {
          case "joins":
            assert (u == null);
            if (line.hasNext()) {
              // throw exception  message
              throw new IllegalStateException("User exists or invalid join syntax");
            } else {

              new User(name);
            }
            break;
          case "leaves":
            assert (u != null);
            if (line.hasNext()) {
              // throw exception message
              throw new IllegalStateException("User does not exist or invalid leave syntax");
            } else {
              u.leave();
            }
            break;
          case "friends":
            assert (u != null);
            String fName = line.next();
            if (fName == null) {
              // throw exception message
              throw new IllegalStateException("Invalid friend name");
            } else {
              if (fName.equals(u.name)) {
                // throw exception message
                throw new IllegalStateException("Invalid followee name");
              }
              recommend(u, u.friend(fName), list);
              for (User v : u.fdj.values()) {
                followSuggestion(u, v, list);
              }
            }
            break;
          case "unfriends":
            assert (u != null);
            u.unfriend(line.next());
            break;
          case "follows":
            assert (u != null);
            User fu = User.find(line.next());
            if (fu == u) {
              // throw exception
              throw new IllegalArgumentException();
            }
            if (fu != null) {
              u.follow(fu);
              followSuggestion(u, fu, list);
            }
            break;
          default:
            // throw exception message
            throw new IllegalArgumentException("Unsupported operation");
        }
      }
      catch (Exception E) {
        error = "Invalid line: " + s;
        break;
      }
    }
    if (!error.equals("")) {
      list.add(error);
    }
    return list;
  }

  /*
   * recommend
   * Given two users, u and f, and an ArrayList of Strings, al, this method
   * will recommend new friends for u based on the friends of f. The
   * recommendations are added to al. The recommendations are of the form
   * "A and B should be friends" where A and B are the names of the users and
   * A comes before B in sorted order. The method does not return anything so
   * the output is passed back in al.
   */

  public void recommend(User u, User f, ArrayList<String> al) {
    ArrayList<String> tmp = new ArrayList<String>();
    makeRecommendations(u, f, tmp);
    makeRecommendations(f, u, tmp);
    Collections.sort(tmp);
    String prev = null;
    for (String s : tmp) {
      if (!s.equals(prev)) {
        al.add(s);
        prev = s;
      }
    }
  }


  /*
   * makeRecommendations
   * Given two users, u and f, and an ArrayList of Strings, al, this method
   * will recommend new friends for u based on the friends of f. The
   * recommendations are added to al. The recommendations are of the form
   * "A and B should be friends" where A and B are the names of the users and
   * A comes before B in sorted order. The method does not return anything so
   * the output is passed back in al.
   */

  public void makeRecommendations(User u, User f, ArrayList<String> al) {
    for (User v : f.adj.values()) {
      if ((u != v) && !u.isFriend(v)) {
        if (v.name.compareTo(u.name) < 0) {
          al.add(v.name + " and " + u.name + " should be friends");
        } else {
          al.add(u.name + " and " + v.name + " should be friends");
        }
      }
    }
  }

  /**
   * This method suggests unique users for a given user 'f' to follow based on the connections
   * of another user 'u', but only if 'u' and 'f' are not already friends. It ensures that the
   * suggestions are unique and sorted. The suggestions are added to an ArrayList 'al' passed
   * to the method.
   *
   * @param u The user whose connections are examined for potential follows.
   * @param f The user who would receive the follow suggestions.
   * @param al The ArrayList where the unique and sorted recommendation strings are stored.
   */

  public void followSuggestion(User u, User f, ArrayList<String> al) {
    // Temporary list to hold potential follow suggestions
    ArrayList<String> tmp = new ArrayList<>();

    // Generate follow recommendations if 'u' and 'f' are not friends
    if (!u.isFriend(f)) {
      FollowRecommendations(u, f, tmp);
    }

    // Sort the temporary list to prepare for duplicate removal
    Collections.sort(tmp);

    // Track the last added recommendation to filter out duplicates
    String prev = null;
    for (String s : tmp) {
      // Only add the suggestion to the final list if it's not a duplicate
      if (!s.equals(prev)) {
        al.add(s);
        prev = s; // Update the last added suggestion
      }
    }
  }


  /**
   * This method generates follow recommendations for a given user 'f' based on the connections
   * of another user 'u'. It populates an ArrayList 'al' with strings recommending who 'f'
   * should follow, excluding users who are already friends or followers of 'f'.
   *
   * @param u The user whose connections are used to find potential follow recommendations.
   * @param f The user for whom follow recommendations are being generated.
   * @param al The ArrayList where recommendation strings are stored.
   */
  public void FollowRecommendations(User u, User f, ArrayList<String> al) {
    // Iterate over each user 'v' in the adjacency list of user 'u'
    for (User v : u.adj.values()) {
      // Check if 'f' is not the same as 'v', 'f' is not friends with 'v', and 'f' does not follow 'v'
      if (f != v && !f.isFriend(v) && !f.doFollow(v)) {
        // Construct the recommendation string
        String recommendation = v.name + " should follow " + f.name;
        // Add the recommendation to the list
        al.add(recommendation);
      }
    }
  }

}


