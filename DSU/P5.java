/*
There are N people in a private party. Initially all are strangers to each other,
and the people are identified by unique ID from 0 to N-1.

In the party, whenever two persons (person-A and person-B) become friends, they 
took a photo. Each of the photo has some information, photos[i]=[T-i, P-j,P-k],
here T-i indicates time of the photo taken, P-j person with ID 'j', and 
P-k indicates person with ID 'k'.

Friendship is symmetric[i.e., If P-j is friend of P-k, then P-k is a friend of P-j].
Additionally, if person-A is "a friend of person-B OR a friend of someone who is 
friend of person-B", then person-A is friend of person-B.

You are given L photos information, Your task is to find the earliest time 
for which every person became friend with every other person in the party.
If there is no such earliest time, return -1.


Input Format:
-------------
Line-1: Two space separated integers, N and L.
Next L lines: Three space separated integers, log[i], 0<=i<L.

Output Format:
--------------
Print an integer result.


Sample Input-1:
---------------
6 8
5 0 1
7 3 4
12 2 3
21 1 5
34 2 4
37 0 3
42 1 2
93 4 5

Sample Output-1:
----------------
37


Sample Input-2:
---------------
7 6
2 0 3
5 1 5
8 2 5
7 3 6
9 4 6
6 4 5

Sample Output-2:
----------------
9
 
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
        Arrays.sort(a, (ai, bi) -> Integer.compare(ai[0], bi[0]));
        int c = n;
        for (int i = 0; i < l; i++) {
            if (dsu.find(a[i][1]) != dsu.find(a[i][2])) {
                dsu.union(a[i][1], a[i][2]);
                c--;
            }
            if (c == 1) {
                System.out.println(a[i][0]);
                return;
            }
        }
    }
}

