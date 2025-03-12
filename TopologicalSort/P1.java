/*
Imagine you're the master chef in a renowned kitchen, tasked with preparing a 
spectacular dinner consisting of numDishes unique dishes, labeled from 
0 to numDishes - 1. However, the recipes have dependencies—certain dishes can 
only be prepared after completing others. You’re given a list called dependecies, 
where each element dependencies[i] = [Xi, Yi] means that you must finish 
preparing dish Yi before starting dish Xi.

For instance, the pair [2, 1] implies that to create dish 2, 
dish 1 must be prepared first.

Return the ordering of dishes that a chef should take to finish all dishes.
	- the result set should follow the given order of conditions.
	- If it is impossible to complete all dishes, return an empty set.


Input Format:
-------------
Line-1: An integer numDishes, number of Dishes.
Line-2: An integer m, number of dependencies.
Next m lines: Two space separated integers, Xi and Yi.

Output Format:
--------------
Return a list of integers, the ordering of dishes that a chef should finish.

Example 1:
------------
Input=
4
3
1 2
3 0
0 1
Output=
[2, 1, 0, 3]


Explanation: There are 4 dishes. Since dish 1 requires dish 2, dish 3 requires 
dish 0 and dish 0 requires dish 1, you can prepare dishes in the order 2 1 0 3.


Example 2:
----------
Input=
2
2
1 0
0 1
Output=
[]

Explanation: There are 2 dishes, but dish 1 depends on dish 0 and dish 0 depends 
on dish 1. This circular dependency makes it impossible to prepare all dishes.

Constraints:

- 1 <= numDishes <= 2000  
- 0 <= m <= 5000  
- dependencies[i].length == 2  
- 0 <= Xi, Yi < numDishes  
- All the dependency pairs are unique.
 
*/

import java.util.*;

class Solution {
    public static boolean cycle;
    public static void dfs(ArrayList<ArrayList<Integer>> a, ArrayList<Integer> res, int[] vis, int node) {
        if (cycle) return;
        vis[node] = 1;
        for (int nbr: a.get(node)) {
            if (vis[nbr] == 1) {
                cycle = true;
                return;
            } 
            if (vis[nbr] == 0) {
                dfs(a, res, vis, nbr);
            }
        }
        vis[node] = 2;
        res.add(node);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        cycle = false;
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        ArrayList<ArrayList<Integer>> a = new ArrayList<>(); 
        int[] ind = new int[n];
        for (int i = 0; i < n; i++) {
            a.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt(), y = sc.nextInt();;
            a.get(x).add(y);
            ind[y]++; 
        }
        
        ArrayList<Integer> res = new ArrayList<>();
        int c = 0;
        for (int i = 0; i < n; i++) {
            if (ind[i] == 0) {
                c++;
            }
            if (c > 1) {
                System.out.println(res);
                return;
            }
        }
        
        int[] v = new int[n];

        for (int i = 0; i < n; i++) {
            if (v[i] == 0) {
                dfs(a, res, v, i);
            }
        }
        System.out.println(res); 
    }
}





