/*
You are given a set of releases of a software and you are asked to find the most
recent release. There may be multiple checkins of the software in a given branch. 
Each branch may also have sub branches. For example release 3-5-4 refers to the 
fourth checkin in the fifth sub branch of the third main branch. 
This hierarchy can go upto any number of levels. 

If a level is missing it is considered as level 0 in that hierarchy. 
For example 3-5-7 is  same as 3-5-7-0 or even same as 3-5-7-0-0. 
The higher numbers denote more recent releases. 

For example 3-5-7-1 is more recent than 3-5-7 but less recent than 3-6.

Input Format:
-------------
A single line space separated strings, list of releases 

Output Format:
--------------
Print the latest release of the software.


Sample Input-1:
---------------
1-2 1-2-3-0-0 1-2-3

Sample Output-1:
----------------
1-2-3

Sample Input-2:
---------------
3-5-4 3-5-7 3-5-7-1 3-5-7-0-0 3-6

Sample Output-2:
----------------
3-6 
*/
import java.util.*;

public class P6 {
    
    /** 
     * Compare two version strings a and b. 
     * Returns negative if a < b, zero if equal, positive if a > b.
     */
    public static int compareVersions(String a, String b) {
        String[] pa = a.split("-");
        String[] pb = b.split("-");
        int n = Math.max(pa.length, pb.length);
        
        for (int i = 0; i < n; i++) {
            // parse or treat missing as 0
            int ai = (i < pa.length) ? Integer.parseInt(pa[i]) : 0;
            int bi = (i < pb.length) ? Integer.parseInt(pb[i]) : 0;
            if (ai != bi) {
                return Integer.compare(ai, bi);
            }
        }
        return 0;  // completely equal
    }

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (sc.hasNextLine()) {
        String line = sc.nextLine().trim();
        if (line.isEmpty()) continue;  

        String[] versions = line.split("\\s+");
        List<String> list = Arrays.asList(versions);
        Collections.sort(list, P6::compareVersions);
        System.out.println(list.get(list.size() - 1));
    }
    sc.close();
}

}
