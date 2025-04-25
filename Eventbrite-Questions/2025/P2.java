/*

2. Cached Requests
There are n services numbered from 1 to n in a system, and there are m requests to be processed. The service in which the iᵗʰ request is cached is denoted by cache[i], for all 1 ≤ i ≤ m. For any request, If the request is processed from its cached service, it takes 1 unit of time. Otherwise, it takes 2 units of time.

Different services can process different requests simultaneously, but one service can only process one request at a time. Find the minimum time to process all the requests by allocating each request to a service.

Example
It is given that n = 3, m = 5, and cache = [1, 1, 3, 1, 1].
If the 1st, 2nd, and 4th requests are assigned to the 1st service, it will take 1 unit of time each, or 3 units of time to process them in total.
Assign the 3rd request to the 2nd service and the 5th request to the 3rd service. It takes 1 and 2 units of time to complete them, respectively. Therefore, all requests are processed in 3 units of time.

Function Description
Complete the function getMinTime in the editor below.


getMinTime has the following parameters:
	int n: the number of services in the system
	int cache[m]: the service in which the request is cached (of length m)

Returns
int: the minimum time required to process all requests

Constraints
1 ≤ n, m ≤ 2 * 10⁵
1 ≤ cache[i] ≤ n

Sample Case 0
Input
n = 4  
cache = [1, 2, 3, 4]
Output
1

Explanation
Each request is cached in a different service. Process all of them at once, which takes 1 unit of time. 
*/

import java.util.*;

class P2 {
    /*----------------------------------------------------*/
    /*  Can we finish every request within T time‑units?  */
    /*----------------------------------------------------*/
    public static boolean check(int time, int n, HashMap<Integer, Integer> mp) {
        int extra = 0;
        int idle = 0;
        for (int i = 1; i <= n; i++) {
            int val = mp.getOrDefault(i, 0);
            if (val > time) {
                extra += val - time;
            } else {
                idle += (time - val) / 2;
            }
        }
        return extra <= idle;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String[] input = sc.nextLine().split(" ");
        int m = input.length;
        int[] cache = new int[m];
        HashMap<Integer, Integer> mp = new HashMap<>();
        for (int i = 0; i < m; i++) {
            cache[i] = Integer.parseInt(input[i]);
            mp.put(cache[i], mp.getOrDefault(cache[i], 0) + 1);
        }
        System.out.println(mp);
        int low = 1, high = 2 * m;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (check(mid, n, mp)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        System.out.println(low);
    }
}