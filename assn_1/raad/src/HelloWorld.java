/* Assignment 1
 * Purpose to download, run and make sure it works, then commit the result.
 * Steps to complete this assignment:
 * 1.  Change line 17 so that bannerNumber is set to your banner number
 * 2.  Change line 18 so that csid is set to your CSID
 * 3.  Change line 19 so that lastName is set to your last name
 * 4.  Change line 20 so that firstName is set to your first name
 * 5.  Compile and run the program
 * 6.  Commit and push the changes back to the repository
 * 7.  You're done!
 */

import java.io.File;
import java.io.FileWriter;

public class HelloWorld {
    // set your full Banner Number here
    private static final String bannerNumber = "B00946674";

    // replace ???? with your csid (e.g., abrodsky)
    private static final String csid = "raad";

    // replace ???? with your last name (e.g., Brodsky)
    private static final String lastName = "Raad";

    // replace ???? with your last name (e.g., Alex)
    private static final String firstName = "Shabaj Islam";

    public static void main(String [] args) {
        try {
            File f = new File("data.txt");
            FileWriter fw = new FileWriter(f);
            fw.write(bannerNumber + "," + csid + ",\"" + lastName + "\",\""
                    + firstName + "\",");
            fw.close();
        } catch (Exception e) {
            System.out.println("Oops, something went wrong.");
            System.out.println(e);
        }
        System.out.println("Hello " + firstName + "!");
    }
}