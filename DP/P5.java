/*
Amogh is an Antiquarian, The person who collects antiques.
He found a rear keyboard which has following keys,
Keys are 'N', 'S', 'C' and 'P'

1st Key - 'N': Print one character 'N' on Console.
2nd Key - 'S': Select the whole Console.
3rd Key - 'C': Copy selected content to buffer.
4th Key - 'P': Print the buffer on Console, and append it after what has 
already been printed.

Now, your task is to find out maximum numbers of 'N's you can print
after K keystrokes . 

Input Format:
-------------
An integer K

Output Format:
--------------
Print an integer, maximum numbers of 'N's you can print.


Sample Input-1:
-------------------
3

Sample Output-1:
-------------------- 
3

Explanation: 
---------------
We can print at most get 3 N's on console by pressing following key sequence:
N, N, N



Sample Input-2:
-------------------
7

Sample Output-2:
---------------------
9

Explanation: 
---------------
We can print at most get 9 N's on console by pressing following key sequence:
N, N, N, S, C, P, P 
*/

import java.util.*;
public class P5 {
    // O(K^3) solution
    public static int solve1(int curLen, int clipLen, int rem, int K, int[][][] memo) {
        // base case: no more keystrokes
        if (rem == 0) return curLen;

        // only look up / write when curLen, clipLen and rem are ≤ K
        if (curLen <= K && clipLen <= K && memo[curLen][clipLen][rem] != -1) {
            return memo[curLen][clipLen][rem];
        }

        // Option 1: press N
        int best = solve1(curLen + 1, clipLen, rem - 1, K, memo);

        // Option 2: select+copy, only if we have at least 2 strokes left
        if (curLen > 0 && rem >= 2) {
            best = Math.max(best,
                solve1(curLen, curLen, rem - 2, K, memo)
            );
        }

        // Option 3: paste, only if buffer non-empty
        if (clipLen > 0) {
            best = Math.max(best,
                solve1(curLen + clipLen, clipLen, rem - 1, K, memo)
            );
        }

        // store in memo if in bounds
        if (curLen <= K && clipLen <= K) {
            memo[curLen][clipLen][rem] = best;
        }
        return best;
    }

    // O(K^2) solution
    public static int solve2(int K) {
        if (K <= 0) return 0;
        int[] dp = new int[K+1];

        for (int i = 1; i <= K; i++) {
            // Option 1: just type an N
            dp[i] = dp[i-1] + 1;

            // Option 2: break at j, then S, C, and multiple P
            // j must be <= i-3
            for (int j = 1; j <= i - 3; j++) { // For ex: if i = 4, j = 1, then we would have (4 - 1 - 1) = 2-pastes and 1-select and 1-copy. 
                int pastes = i - j - 2;
                int candidate = dp[j] * (pastes + 1);
                dp[i] = Math.max(dp[i], candidate);
            }
        }
        return dp[K];
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int[][][] memo = new int[k + 1][k + 1][k + 1];
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= k; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        System.out.println(solve1(0, 0, k, k, memo));
        System.out.println(solve2(k));
        sc.close();
    }
}


