import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/* FriendRecommender.java
 *
  * This class is used to make friend recommendations for users of a social
  * network. The code is incomplete and contains bugs. There is no main method
  * so you will have to use unit tests to test the code.
  *
*/
public class FriendRecommender {


  /* makeRecommendations
   * Given two users, u and f, and an ArrayList of Strings, al, this method
   * will recommend new friends for u based on the friends of f. The
   * recommendations are added to al. The recommendations are of the form
   * "A and B should be friends" where A and B are the names of the users and
   * A comes before B in sorted order. The method does not return anything so
   * the output is passed back in al.
   */
  public void makeRecommendations( User u, User f, ArrayList<String> al ) {
    for( User v : f.adj.values() ) {
      if( ( u != v ) && !u.isFriend( v ) ) {
        if( v.name.compareTo( u.name ) > 0 ) {
          al.add( v.name + " and " + u.name + " should be friends" );
        } else {
          al.add( u.name + " and " + v.name + " should be friends" );
        }
      }  
    }
  }

}
