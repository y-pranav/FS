/*
Budget Padmanabham planned to visit the tourist places. There are N tourist 
places in the city. The tourist places are numbered from 1 to N.

You are given the routes[] to travel between the tourist places in the city.
where routes[i]=[place-1, place-2, price], A route is a bi-directional route.
You can travel from place-1 to place-2 or place-2 to place-1 with the given price.
 
Your task is to help Budget Padmanabham to find the cheapest route plan, to 
visit all the tourist places in the city. If you are not able to find such plan, 
print -1.
 
Input Format:
-------------
Line-1: Two space separated integers N and R,number of places and routes.
Next R lines: Three space separated integers, start, end and price.
  
Output Format:
--------------
Print an integer, minimum cost to visit all the tourist places.
 
 
Sample Input-1:
---------------
4 5
1 2 3
1 3 5
2 3 4
3 4 1
2 4 5
 
Sample Output-1:
----------------
8
 
Explanation:
------------
The cheapest route plan is as follows:
1-2, 2-3, 3-4 and cost is 3 + 4 + 1 = 8
 
 
Sample Input-2:
---------------
4 3
1 2 3
1 3 5
2 3 4
 
Sample Output-2:
----------------
-1 
*/

import java.util.*;

class DSU {
    public int[] parent;

    public DSU(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); 
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX >= rootY) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
        }
    }
}

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int l = sc.nextInt();
        DSU dsu = new DSU(n);
        int[][] a = new int[l][3];
        for (int i = 0; i < l; i++) {
            a[i][0] = sc.nextInt();
            a[i][1] = sc.nextInt();
            a[i][2] = sc.nextInt();
        }
        Arrays.sort(a, (ai, bi) -> Integer.compare(ai[2], bi[2]));
        int c = n;
        int res = 0;
        for (int i = 0; i < l; i++) {
            if (dsu.find(a[i][0]) != dsu.find(a[i][1])) {
                dsu.union(a[i][0], a[i][1]);
                res += a[i][2];
                c--;
            }
            if (c == 1) {
                System.out.println(res);
                return;
            }
        }
        System.out.println(-1);
    }
}

