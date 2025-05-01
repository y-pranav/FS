/*
You are managing a fleet of exploratory spacecraft, each carrying containers 
composed of two types of critical resources: 
fuel units (represented by '0') and oxygen tanks (represented by '1'). 
You're given a list 'containers', where each container is represented by a 
binary string indicating its resource composition, 
along with two integers: 'fuelLimit' (maximum allowed fuel units) and 
'oxygenLimit' (maximum allowed oxygen tanks).

Your goal is to select the largest possible subset of containers such that the 
total number of fuel units does not exceed 'fuelLimit' and the total number of 
oxygen tanks does not exceed 'oxygenLimit'.

Input format:
-------------
Line 1: Space seperated strings, represents the container strings
Line 2: Two space separated integers, represents fuelLimit & oxygenLimit

Output format:
--------------
An integer, largest possible subset of containers.


Example 1:
----------
Input=
10 0001 111001 1 0
5 3

Output=
4

Explanation: The largest subset meeting the constraints is {"10", "0001", "1", "0"}.
- Total fuel units = 5 (within limit)
- Total oxygen tanks = 3 (within limit)
Container "111001" cannot be included as it exceeds the oxygen tank limit.


Example 2:
----------
Input=
10 0 1
1 1

Output=
2

Explanation: The largest subset meeting the constraints is {"0", "1"}.
- Total fuel units = 1
- Total oxygen tanks = 1


Constraints:
- 1 <= containers.length <= 600
- 1 <= containers[i].length <= 100
- 'containers[i]' consists only of digits '0' and '1'.
- 1 <= fuelLimit, oxygenLimit <= 100
*/

import java.util.*;

class Solution {
    static int res = 0;
    public static int topdown(String[] a, int fl, int ol, int[] zeros, int idx, int tfl, int tol, int[][][] memo) {
        if (idx == a.length || tfl > fl || tol > ol) return 0;
        if (memo[idx][tfl][tol] != -1) return memo[idx][tfl][tol];
        int take = 0;
        if (tfl + zeros[idx] <= fl && tol + a[idx].length() - zeros[idx] <= ol) take += 1 + topdown(a, fl, ol, zeros, idx + 1, tfl + zeros[idx], tol + a[idx].length() - zeros[idx], memo);
        int notake = topdown(a, fl, ol, zeros, idx + 1, tfl, tol, memo);
        return memo[idx][tfl][tol] = Math.max(take, notake);
    }

    public static int bottomup(String[] input, int[] zeros, int fl, int ol) {
        int n = input.length;
        int[][] dp = new int[fl+1][ol+1];
        for (int i = 0; i < n; i++) {
            int f = zeros[i];
            int o = input[i].length() - f;
            for (int cf = fl; cf >= f; cf--) {
              for (int co = ol; co >= o; co--) {
                dp[cf][co] = Math.max(
                  dp[cf][co],
                  1 + dp[cf - f][co - o]
                );
              }
            }
        }
        return dp[fl][ol];
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int fl = sc.nextInt();
        int ol = sc.nextInt();
        int[] zeros = new int[n];
        int[][][] memo = new int[n + 1][fl + 1][ol + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= fl; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        for (int i = 0; i < n; i++) {
            for (char ch: input[i].toCharArray()) {
                if (ch == '0') {
                    zeros[i]++;
                }
            }   
        }
        System.out.println(topdown(input, fl, ol, zeros, 0, 0, 0, memo));
        System.out.println(bottomup(input, zeros, fl, ol));
        sc.close();
    }
}