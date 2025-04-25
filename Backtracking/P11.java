/*
You are given a crystal with an energy level n. Your goal is to discover all the 
different ways this crystal could have been created by combining smaller shards.

Each combination must:
- Use only shards with energy values between 2 and n - 1.
- Be represented as a list of shard values whose product equals n.
- Use any number of shards (minimum 2), and the order doesn't matter.

Your task is to return all unique shard combinations that can multiply together 
to recreate the original crystal.

Example 1:
---------
Input:
28

Output:
[[2, 14], [2, 2, 7], [4, 7]]

Example 2:
----------
Input:
23

Output:
[]

Constraints:
- 1 <= n <= 10^4
- Only shards with energy between 2 and n - 1 can be used.

*/

import java.util.*;

class Solution {
    // WITH START VARIABLE TO AVOID DUPLICATE COMBINATIONS
    public static ArrayList<ArrayList<Integer>> res;
    public static void backtrack(int num, int start, int n, ArrayList<Integer> temp) {
        if (num == 1) {
            if (temp.size() > 1) {
                res.add(new ArrayList<>(temp));
            }
            return;
        }

        for (int i = start; i <= num; i++) {
            if (num % i == 0) {
                temp.add(i);
                backtrack(num / i, i, n, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        res = new ArrayList<>();
        backtrack(n, 2, n, new ArrayList<>());
        System.out.println(res);
    }
}
/*
    WITH SORTING AND HASHSET
    public static ArrayList<ArrayList<Integer>> res;
    public static void backtrack(int num, int n, ArrayList<Integer> temp, HashSet<ArrayList<Integer>> set) {
        if (num == 1) {
            ArrayList<Integer> tempc = new ArrayList<>(temp);
            Collections.sort(tempc);
            if (!set.contains(tempc)) {
                res.add(new ArrayList<>(tempc));
                // System.out.println(tempc);
                set.add(tempc);
            }
            return;
        }

        for (int i = 2; i <= num; i++) {
            if (num % i == 0 && i != n) {
                temp.add(i);
                backtrack(num / i, n, temp, set);
                temp.remove(temp.size() - 1);
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        res = new ArrayList<>();
        backtrack(n, n, new ArrayList<>(), new HashSet<>());
        System.out.println(res);
    } 
*/
}