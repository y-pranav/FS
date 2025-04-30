/*
You are participating in a futuristic space exploration mission where you must 
navigate through a series of planets aligned in a straight line.
The planets are numbered from 0 to n-1, and you start your journey on planet 0.

You are given a 0-indexed array planets, where each element planets[i] represents 
the maximum number of planets you can move forward from planet i. In simpler terms, 
standing on planet i, you can move to any planet from i+1 to i+planets[i], 
as long as you don't exceed the last planet.

Your goal is to reach the final planet (planet n-1) in the fewest number of 
moves possible.

It is guaranteed that a path to the final planet always exists.

Return the minimum number of moves needed to reach planet n-1.

Example 1
----------
Input:
2 3 1 0 4
Output:
2

Explanation:
- Move from planet 0 to planet 1.
- Move from planet 1 to planet 4 (last planet). 
*/

import java.util.*;

class Solution {
    
    // O(n^2)
    public static int dp(int[] a) {
        int n = a.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (j + a[j] >= i) { // can reach i from j
                    dp[i] = Math.min(dp[j] + 1, dp[i]);
                }
            }
        }
        return dp[n - 1];
    }

    // O(n) 
    public static int greedy(int[] a) {
        int n = a.length;
        int end = 0;
        int max_reach = 0;
        int jumps = 0;
        for (int i = 0; i < n - 1; i++) {
            max_reach = Math.max(i + a[i], max_reach);
            if (i == end) {
                end = max_reach;
                jumps++;
            }
        }
        return jumps;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) 
            a[i] = Integer.parseInt(input[i]);
        
        System.out.println(dp(a));   
        System.out.println(greedy(a));
        sc.close();     
    }
}