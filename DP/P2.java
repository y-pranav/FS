/*
You are organizing a grand parade where 'N' marching bands will move in a straight 
line. Each band must wear uniforms of exactly one color chosen from 'K' available 
colors. To keep the parade visually appealing and avoid monotony, 
you must follow this important guideline:

- No more than 'two consecutive bands' can wear 'uniforms of the same color'.

Given the total number of bands N and the number of uniform color choices K, 
determine the total number of valid ways you can assign uniform colors to all 
bands so that the above rule is not violated.

Input Format:
-------------
Two integers N and K.

Output Format:
--------------
Print an integer, Number of ways.

Example 1:  
----------
Input: 
3 2
Output:
6  

Explanation:
------------
Bands	band-1	band-2	band-3
----- 	----- 	----- 	-----
1		c1 		c1		c2
2		c1 		c2 		c1
3		c1 		c2 		c2
4		c2 		c1 		c1
5		c2 		c1 		c2
6		c2 		c2 		c1

Example 2:  
----------
Input: 
1 1
Output: 
1


Constraints:  
- 1 <= n <= 50  
- 1 <= k <= 10^5 
- The result will always be within the range of a 32-bit signed integer.
 
*/

import java.util.*;

public class P2 {
    /**
     * Top-Down #1: Full 3D DP tracking the last two colours.
     * Time Complexity: O(n · k · k)
     * Space Complexity: O(n · k · k)
     */
    public static int topdown1(int n, int k, int prev2, int prev1, int[][][] memo) {
        if (n == 0) return 1;
        if (prev2 != -1 && prev1 != -1 && memo[n][prev2][prev1] != -1) return memo[n][prev2][prev1];
        int res = 0;
        for (int i = 0; i < k; i++) {
            if (prev2 == -1) {
                res += topdown1(n - 1, k, i, prev1, memo);
            } else if (prev1 == -1) {
                res += topdown1(n - 1, k, prev2, i, memo);
            } else if ((prev2 != prev1) || (prev1 == prev2 && i != prev1)) {
                res += topdown1(n - 1, k, prev1, i, memo);
            } else {
                continue;
            }
        }
        if (prev2 == -1 || prev1 == -1) return res;
        return memo[n][prev2][prev1] = res;
    }

    /**
     * Top-Down #2: 1D DP using the recurrence
     *   ways(i) = (ways(i−1) + ways(i−2)) * (k−1),
     * with base cases ways(1)=k, ways(2)=k².
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int topdown2(int i, int k, int[] memo) {
        if (i == 0) return 0;
        if (i == 1) return k;
        if (i == 2) return k * k;
        if (memo[i] != -1) return memo[i];

        long w1 = topdown2(i - 1, k, memo);
        long w2 = topdown2(i - 2, k, memo);
        long ans = (w1 + w2) * (k - 1);
        return memo[i] = (int)ans;
    }

    /**
     * Bottom-Up: same recurrence as topdown2, but iteratively in O(1) space.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int bottomUp(int n, int k) {
        if (n == 0) return 0;
        if (n == 1) return k;

        // ways for length 1 and 2
        long w1 = k;      // ways(1)
        long w2 = (long)k * k;  // ways(2)

        // build up from i=3 to n
        for (int i = 3; i <= n; i++) {
            long w = (w2 + w1) * (k - 1);
            w1 = w2;
            w2 = w;
        }
        return (int)w2;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        // Prepare memo for topdown1
        int[][][] memo1 = new int[n + 1][k + 1][k + 1];
        for (int[][] layer : memo1)
            for (int[] row : layer)
                Arrays.fill(row, -1);

        // Prepare memo for topdown2
        int[] memo2 = new int[n + 1];
        Arrays.fill(memo2, -1);

        int ans1 = topdown1(n, k, -1, -1, memo1);
        int ans2 = topdown2(n, k, memo2);
        int ans3 = bottomUp(n, k);

        System.out.println("TopDown1 (3D state)      : " + ans1);
        System.out.println("TopDown2 (2-term recur.) : " + ans2);
        System.out.println("BottomUp (O(1) space)    : " + ans3);

        sc.close();
    }
}
