/*
You are a robot explorer navigating a vast digital maze stored as a string of digits. 
Each digit or pair of digits on the path represents a movement instruction, which 
translates to a specific direction using the following map:

"1" → Move 'A'

"2" → Move 'B'

...

"26" → Move 'Z'

However, the maze has tricky encoding rules. Sometimes a single digit can be 
read alone, and sometimes two digits together form a valid move — but not every 
combination is valid. For example, "05" is invalid, while "5" is fine. 
Your robot can only navigate using valid instruction steps, and you must find 
how many unique navigation sequences the robot can follow to reach the end of 
the maze.

Given the string s of digits, determine the total number of distinct ways the 
robot can interpret and follow the path from start to end without making an 
invalid move.

If no valid navigation is possible, return 0.


Input Format:
-------------
A string s.

Output Format:
--------------
Print an integer result.


Sample Input-1:
---------------
123

Sample Output-1:
----------------
3

Explanation:
------------
123 can be converted as: ABC, LC, AW


Sample Input-2:
---------------
326

Sample Output-2:
----------------
2

Explanation:
------------
326 can be converted as: CBF, CZ
 
*/

import java.util.*;

class Solution {
    public static int solve(int pos, int n, String s, int[] memo) {
        if (pos == n) return 1;
        if (s.charAt(pos) == '0') return 0;
        if (memo[pos] != -1) return memo[pos];
        int res = solve(pos + 1, n, s, memo);
        if (pos + 1 < n) {
            if (s.charAt(pos) == '1' || (s.charAt(pos) == '2' && s.charAt(pos + 1) <= '6')) {
                res += solve(pos + 2, n, s, memo);
            }
        }
        return memo[pos] = res;
    }
    // public static int solve(int n, String s, HashMap<String, Integer> memo) {
    //     if (s.isEmpty()) return 1;
    //     String key = n + "," + s;
    //     if (memo.containsKey(key)) return memo.get(key);
    //     int res = 0;
    //     for (int i = 1; i <= s.length(); i++) {
    //         if (s.charAt(0) == '0') break;
    //         int num = Integer.parseInt(s.substring(0, i));
    //         if (num > 26) break;
    //         res += solve(n, s.substring(i), memo);
    //     }
    //     memo.put(key, res);
    //     return res;
    // }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = s.length();
        // HashMap<String, Integer> memo = new HashMap<>();
        int[] memo = new int[n];
        Arrays.fill(memo, -1);
        System.out.println(solve(0, n, s, memo));
        sc.close();
    }
} 