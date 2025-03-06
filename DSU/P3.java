/*

You are a database integrity engineer working for a global cloud company. 
Your team maintains a distributed database network, where each server either:
    - Stores equivalent data to another server (serverX == serverY).
    - Stores different data from another server (serverX != serverY).

The transitive consistency rule must be followed:
    - If A == B and B == C, then A == C must be true.
    - If A == B and B != C, then A != C must be true.

Your task is to analyze the given constraints and determine whether they 
follow transitive consistency. If all relations are consistent, return true; 
otherwise, return false

Input Format:
-------------
Space separated strnigs, list of relations

Output Format:
--------------
Print a boolean value, whether transitive law is obeyed or not.


Sample Input-1:
---------------
a==b c==d c!=e e==f

Sample Output-1:
----------------
true


Sample Input-2:
---------------
a==b b!=c c==a

Sample Output-2:
----------------
false

Explanation:
------------
{a, b} form one equivalence group.
{c} is declared equal to {a} (c == a), which means {a, b, c} should be equivalent.
However, b != c contradicts b == a and c == a.

Sample Input-3:
---------------
a==b b==c c!=d d!=e f==g g!=d

Sample Output-3:
----------------
true

*/

import java.util.*;

class DSU {
    public char[] parent;

    public DSU() {
        parent = new char[26];
        for (int i = 0; i < 26; i++) {
            parent[i] = (char) (i + 'a');
        }
    }

    public char find(char x) {
        if (parent[x - 'a'] != x) {
            parent[x - 'a'] = find(parent[x - 'a']); 
        }
        return parent[x - 'a'];
    }

    public void union(char x, char y) {
        char rootX = find(x);
        char rootY = find(y);

        if (rootX <= rootY) {
            parent[rootX - 'a'] = rootY;
        } else {
            parent[rootY - 'a'] = rootX; 
        }
    }
}


class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DSU dsu = new DSU();
        String[] s = sc.nextLine().split(" ");
        int n = s.length;
        ArrayList<String> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s[i].charAt(1) == '!') {
                a.add(s[i]);
            } else {
                dsu.union(s[i].charAt(0), s[i].charAt(3));
            }
        }
        
        int flag = 0;
        for (String p: a) {
            if (dsu.find(p.charAt(0)) == dsu.find(p.charAt(3))) {
                flag = 1;
                break;
            }
        }
        System.out.println(flag == 0);
    }
}








