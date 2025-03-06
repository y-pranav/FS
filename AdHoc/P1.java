/*
A grid of light bulbs is given, represented as a matrix of size rows x cols, 
where each cell contains either 0 (off) or 1 (on).

Your task is to turn all the light bulbs off (0) by following the toggle rule:
    - In each step, you can choose either an entire row or an entire column 
    and toggle all its elements (change 0 to 1 and 1 to 0).
    
At the end, if all light bulbs are turned off, print true, otherwise print false.


Input Format
-------------
Line-1: Read two integers rows and cols(space separated).
Line-2: Read the matrix of dimension rows X cols.

Output Format
--------------
Print a boolean result.


Sample input-1:
---------------
5 5
0 0 1 0 0
0 0 1 0 0
1 1 0 1 1
0 0 1 0 0
0 0 1 0 0

Sample output-1:
----------------
true

Explanation:
------------
0 0 1 0 0          0 0 1 0 0           0 0 0 0 0
0 0 1 0 0   row-3  0 0 1 0 0   cols-3  0 0 0 0 0
1 1 0 1 1   --->   0 0 1 0 0   --->    0 0 0 0 0
0 0 1 0 0          0 0 1 0 0           0 0 0 0 0
0 0 1 0 0          0 0 1 0 0           0 0 0 0 0 


Sample input-2
--------------
2 2
1 1
0 1

Sample output-2
---------------
false
 
*/
import java.util.*;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        
        int[] x = new int[n];
        for (int i = 0; i < n; i++) {
            int t = grid[i][0];
            for (int j = 1; j < m; j++) {
                t ^= grid[i][j];
            }
            x[i] = t;
        }
        int z = 0, o = 0;
        for (int xi: x) {
            if (xi == 0) z++;
        }
        System.out.println(z <= 1);
    }
}