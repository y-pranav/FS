/*
In the present situation, most of the movies releasing in OTTs.
The Showtime OTT in US, introduced a new offer for the customers, 
they can purchase either 1-day, 7-day, or 30-day subscription,
and the cost is as follows price[0], price[1], price[2].

The Subscription allows you to watch as many movies as you want with in subscribed days. 
For example:
If you purchased, a 7-day subscription on day 5, then you can watch
the movies for 7 days: day 5, 6, 7, 8, 9, 10 and 11.

Your task is to find out the minimum cost, you spend to watch the movies
in the given list of days .

NOTE: Days are numbered from 1, 2, 3, ...365, in sorted order.

Input Format:
-------------
Line 1: Space separated integer days[], list of days.
Line 2: 3 space separated integer price[], cost of subscription.

Output Format:
--------------
Print an integer, minimum cost. 


Sample Input-1:
---------------
1 4 6 7 8 20
2 7 15

Sample Output-1:
----------------
11

Explanation:
------------
For example, here is a way to buy subscription, to watch the movies in given days:
On day 1, buy a 1-day subscription for price[0] = $2, which cover day 1.
On day 4, buy a 7-day subscription for price[1] = $7, which cover days 4, 5, ..., 10.
On day 20, buy a 1-day subscription for price[0] = $2, which cover day 20.
In total you spent $11.


Sample Input-2:
---------------
1 2 3 4 5 6 7 8 9 10 30 31
2 7 15

Sample Output-2:
----------------
17

Explanation:
------------
For example, here is a way to buy subscription, to watch the movies in given days:
On day 1, buy a 30-day subscription for price[2] = $15, which cover days 1, 2, 3,....,30.
On day 31, buy a 1-day subscription for price[0] = $2, which cover day 31.
In total you spent $17.
 
*/

import java.util.*;

class Solution {
    public static int solve(int n, int[] days, int[] costs, HashSet<Integer> set, int cur, int[] memo) {
        if (cur > days[n - 1]) return 0;
        if (!set.contains(cur)) return memo[cur] = solve(n, days, costs, set, cur + 1, memo);
        if (memo[cur] != -1) return memo[cur];
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            int temp = costs[i] + solve(n, days, costs, set, cur + (i == 0 ? 1 : i == 1 ? 7 : 30), memo);
            res = Math.min(temp, res);
        }
        return  memo[cur] = res;
    } 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input1 = sc.nextLine().split(" ");
        String[] input2 = sc.nextLine().split(" ");
        int n1 = input1.length;
        int n2 = input2.length;
        
        int[] days = new int[n1];
        int[] costs = new int[n2];
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n1; i++) {
            days[i] = Integer.parseInt(input1[i]);
            set.add(days[i]);
        }
        for (int i = 0; i < n2; i++) {
            costs[i] = Integer.parseInt(input2[i]);
        }
        int[] memo = new int[days[n1 - 1] + 1];
        Arrays.fill(memo, -1);
        System.out.println(solve(n1, days, costs, set, days[0], memo));
        sc.close();
    }
}