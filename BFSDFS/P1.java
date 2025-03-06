/*
Pranav has a puzzle board filled with square boxes in the form of a grid. Some 
cells in the grid may be empty. '0' - indicates empty, '1' - indicates a box. 

The puzzle board has some patterns formed with boxes in it, 
the patterns may be repeated. The patterns are formed with boxes (1's) only, 
that are connected horizontally and vertically but not diagonally.

Pranav wants to find out the number of unique patterns in the board.

You are given the board in the form of a grid M*N, filled wth 0's and 1's.
Your task is to help Pranav to find the number of unique patterns in 
the puzzle board.

Input Format:
-------------
Line-1: Two integers M and N, the number of rows and columns in the grid-land.
Next M lines: contains N space-separated integers [0, 1].

Output Format:
--------------
Print an integer, the number of unique patterns in the puzzle board.


Sample Input-1:
---------------
5 5
0 1 0 1 1
1 1 1 0 1
0 1 0 1 0
1 0 1 1 1
1 1 0 1 0

Sample Output-1:
----------------
3

Explanation-1:
------------
The unique patterns are as follows:
  1			1 1	    1 
1 1 1		  1 ,	1 1
  1	   ,	
   
   
Sample Input-2:
---------------
6 6
1 1 0 0 1 1
1 0 1 1 0 1
0 1 0 1 0 0
1 1 0 0 0 1
0 0 1 0 1 1
1 1 0 1 0 0

Sample Output-2:
----------------
5

Explanation-2:
------------
The unique patterns are as follows:
1 1		1 1		    1		1 1	,	1
1   ,     1 ,	    1 1 ,		
 
*/

import java.util.*;

class Solution {
    public static int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static String bfs(int[][] g, int baseX, int baseY) {
        StringBuilder sb = new StringBuilder();
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] {baseX, baseY});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            int deltaX = baseX - x, deltaY = baseY - y;
            sb.append(deltaX + "" + deltaY);
            for (int[] dir: dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < g.length && ny < g[0].length && g[nx][ny] == 1) {
                    q.offer(new int[] {nx, ny});
                    g[nx][ny] = 0;
                }
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[][] g = new int[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = sc.nextInt();
            }
        }
        
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 1) {
                    String s = bfs(g, i, j);
                    if (!s.equals("")) { 
                        set.add(s);
                    }
                }
            }
        }
        System.out.println(set);
        System.out.println(set.size());
    }
}