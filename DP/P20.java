/*
Alex and his twin brother Jordan often create secret messages. One day, Jordan 
gives Alex two encrypted messages and challenges him to find the longest common 
palindromic pattern hidden within both messages.

Alex wants your help to decode the longest common palindromic subsequence that 
exists in both strings.

Your task is to determine the length of the longest subsequence that:
- Appears in both messages
- Is a palindrome

Input Format:
-------------
input1: A string representing the first encrypted message.
input2: A string representing the second encrypted message.

Output Fromat:
--------------
Return an integer representing the length of the longest common palindromic 
subsequence shared by both messages.


Sample Input: 
-------------
adfa
aagh

Sample Output:
--------------
2


Sample Input-2:
---------------
abcda
fxaaba

Sample Output:
--------------
3

Explanation:
------------
The longest palindromic subsequence common to both is "aba" with length 3.
 
*/

import java.util.*;

class P20 {
    public static String lcs(String s1, String s2, int i, int j, String[][] dp) {
        if (i >= s1.length() || j >= s2.length()) return "";
        if (dp[i][j] != "/")  return dp[i][j]; 
        if (s1.charAt(i) == s2.charAt(j)) return dp[i][j] = "" + s1.charAt(i) + lcs(s1, s2, i + 1, j + 1, dp);
        String hs1 = lcs(s1, s2, i + 1, j, dp);
        String hs2 = lcs(s1, s2, i, j + 1, dp);
        return dp[i][j] = (hs1.length() >= hs2.length() ? hs1 : hs2);
    }
    public static int lps(String s, int i, int j, int[][] dp) {
        if (i >= s.length() || j < 0 || i > j) return 0;
        if (i == j) return 1;
        if (dp[i][j] != -1) return dp[i][j];
        if (s.charAt(i) == s.charAt(j)) return dp[i][j] = 2 + lps(s, i + 1, j - 1, dp);
        return dp[i][j] = Math.max(lps(s, i + 1, j, dp), lps(s, i, j - 1, dp));
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();
        int n = s1.length();
        int m = s2.length();
        
        String[][] lcs_dp = new String[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(lcs_dp[i], "/");
        String lcs = lcs(s1, s2, 0, 0, lcs_dp);
        // System.out.println(lcs);
        
        int[][] lps_dp = new int[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(lps_dp[i], -1);
        int len = lps(lcs, 0, lcs.length() - 1, lps_dp);
        System.out.println(len);
    }
}
