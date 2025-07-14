/*
Keerthilal wants to try his luck in Diamonds business. 
He decides to buy and sell diamonds. 

He is given the prices of one diamond for N days by his friend.
Initially, it is assumed that he has no diamonds.

You need to help Keerthilal in making the maximum profit that is possible. 
He can sell a diamond only after he buys a diamond. 

Note: 
    - He is allowed to do any number of transactions
      but, he can buy and sell only one diamond per transaction.
    
    - He must complete one transaction before the next transaction.
    
    - After each transaction completed, there is a break day.
    i.e After he sells his diamond, he cannot buy diamond on next day.

		
Input Format:
-------------
Space separated integers, price of the diamond for N days.

Output Format:
--------------
Print an integer, maximum profit.


Sample Input-1:
---------------
7 1 5 3 6 4

Sample Output-1:
----------------
5

Explanation: 
------------
Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.


Sample Input-2:
---------------
1 2 3 1 3

Sample Output-2:
----------------
3

Explanation: 
------------
Buy on day 1 (price = 1) and sell on day 2 (price = 2), profit = 2-1 = 1.
day 3 is a break.
Buy on day 4 (price = 1) and sell on day 5 (price = 3), profit = 3-1 = 2.
Total Maximum Profit = 1+2 = 3
*/

import java.util.*;

class Solution {
    // dp[idx][0] = max profit from idx when canBuy==false (i.e. holding a diamond)
    // dp[idx][1] = max profit from idx when canBuy==true  (i.e. not holding)
    static int[][] dp;
    static int[] prices;
    static int n;
    
    // top-down function
    public static int solve(int idx, boolean canBuy) {
        if (idx >= n) return 0;
        int t = canBuy ? 1 : 0;
        if (dp[idx][t] != Integer.MIN_VALUE) 
            return dp[idx][t];
        
        int doNothing = solve(idx + 1, canBuy);
        int doTrans;
        if (canBuy) {
            // option to buy today
            doTrans = -prices[idx] + solve(idx + 1, false);
        } else {
            // option to sell today, then cooldown one day
            doTrans = prices[idx] + solve(idx + 2, true);
        }
        
        int ans = Math.max(doNothing, doTrans);
        dp[idx][t] = ans;
        return ans;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] in = sc.nextLine().trim().split("\\s+");
        n = in.length;
        prices = new int[n];
        for (int i = 0; i < n; i++) 
            prices[i] = Integer.parseInt(in[i]);
        
        // init dp to “uncomputed”
        dp = new int[n][2];
        for (int i = 0; i < n; i++) 
            dp[i][0] = dp[i][1] = Integer.MIN_VALUE;
        
        // start at day 0, with the ability to buy
        System.out.println(solve(0, true));
        sc.close();
    }
}
