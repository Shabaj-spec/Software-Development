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

  /* main
   * This method is used to read input from the user and print the output of the
   * friend recommendations. The input is read from the standard input and the
   * output is printed to the standard output.
   */
  public static void main( String [] args ) {
    ArrayList<String> output = new FriendRecommender().compute( new Scanner( System.in ) );
    for( String s : output ) {
      System.out.println( s );
    }
  }

  /* compute
   * This method takes a Scanner, input, and returns an ArrayList of Strings.
   * The Scanner contains the input from the user. The method reads the input
   * and makes friend recommendations based on the input. The method returns an
   * ArrayList of Strings that contains the friend recommendations.
   */
  public ArrayList<String> compute( Scanner input ) {
    ArrayList<String> list = new ArrayList<String>();

    for(String s = input.nextLine(); !s.equals( "end" ); s = input.nextLine()) {
      Scanner line = new Scanner( s );
      String name = line.next();
      User u = User.find( name );

      switch( line.next() ) {
        case "joins":
          assert( u == null );
          new User( name );
          break;
        case "leaves":
          assert( u != null );
          u.leave();
          break;
        case "friends":
          assert( u != null );
          makeRecommendations( u, u.friend( line.next() ), list );
          break;
        case "unfriends":
          assert( u != null );
          u.unfriend( line.next() );
          break;
        default:
          System.out.println( "Unknown user action" );
      }
    }
    return list;
  }

  /* recommend
   * Given two users, u and f, and an ArrayList of Strings, al, this method
   * will recommend new friends for u based on the friends of f. The
   * recommendations are added to al. The recommendations are of the form
   * "A and B should be friends" where A and B are the names of the users and
   * A comes before B in sorted order. The method does not return anything so
   * the output is passed back in al.
   */
  public void recommend( User u, User f, ArrayList<String> al ) {
    ArrayList<String> tmp = new ArrayList<String>();
    Collections.sort( tmp );
    String prev = null;
    for( String s : tmp ) {
      if( !s.equals( prev ) ) {
        al.add( s );
        prev = s;
      }
    }
  }


  /* makeRecommendations
   * Given two users, u and f, and an ArrayList of Strings, al, this method
   * will recommend new friends for u based on the friends of f. The
   * recommendations are added to al. The recommendations are of the form
   * "A and B should be friends" where A and B are the names of the users and
   * A comes before B in sorted order. The method does not return anything so
   * the output is passed back in al.
   */
  public void makeRecommendations(User u, User f, ArrayList<String> al) {
    ArrayList<String> tempRecommendations = new ArrayList<>();

    // Gather recommendations based on friends of 'f'
    for (User v : f.adj.values()) {
      if ((u != v) && !u.isFriend(v)) {
        if (v.name.compareTo(u.name) < 0) {
          tempRecommendations.add(v.name + " and " + u.name + " should be friends");
        } else {
          tempRecommendations.add(u.name + " and " + v.name + " should be friends");
        }
      }
    }

    // Gather recommendations based on friends of 'u'
    for (User i : u.adj.values()) {
      if ((f != i) && !f.isFriend(i)) {
        if (i.name.compareTo(f.name) < 0) {
          tempRecommendations.add(i.name + " and " + f.name + " should be friends");
        } else {
          tempRecommendations.add(f.name + " and " + i.name + " should be friends");
        }
      }
    }

    // Sort temporary recommendations
    Collections.sort(tempRecommendations);

    // Add unique recommendations to the main list
    String previous = "";
    for (String recommendation : tempRecommendations) {
      if (!recommendation.equals(previous)) {
        al.add(recommendation);
        previous = recommendation;
      }
    }
  }

}