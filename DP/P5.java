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

class Solution {
    public static int solve(int k, boolean flag) {
        if (k < 0) return Integer.MIN_VALUE;
        if (k == 0) return 0;
        int print = 1 + solve(k - 1, false);
        int sc = solve(k - 2, true);
        int printbuf = flag ? print + solve(k - 1, false) : 0;
        return Math.max(print, Math.max(sc, printbuf));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        System.out.println(solve(k, false));
    }
}

import java.util.*;

class Solution {
    public static int solve(int k, int flag, int[][] memo) {
        if (k < 0) return Integer.MIN_VALUE;
        if (k == 0) return 0;
        if (memo[k][flag] != -1) return memo[k][flag];
        int print = 1 + solve(k - 1, 0, memo);
        int sc = solve(k - 2, 1, memo);
        int printbuf = flag == 1 ? print + solve(k - 1, 0, memo) : 0;
        return memo[k][flag] = Math.max(print, Math.max(sc, printbuf));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int[][] memo = new int[k + 1][2];
        for (int i = 0; i < k; i++) {
            Arrays.fill(memo[i], -1);
        }
        System.out.println(solve(k, 0, memo));
    }
}