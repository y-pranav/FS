
/*

Tejaswi playing a game. Game has a display of N numbers in a row, nums[].
Tejaswi has to pick the numbers one after the other.

Once Tejaswi picks a number num[i], she will score num[i] points and 
the number will be disappared on the board, simultaneously all the numbers 
having the value num[i]+1 or num[i]-1 also disappears. 

Tejaswi has to select the numbers, such that no more numbers will be left over 
on the board.

Tejaswi wants to score maximum number of points.

Initially Tejaswi has 0 points with her.
Can you help Tejaswi to score maximum number of points.

Input Format:
-------------
Line-1 -> an integers N, number of numbers on the game board.
Line-2 -> N space separated integers, numbers on the game board nums[].

Output Format:
--------------
Print an integer as result, maximum score she can get.


Sample Input-1:
---------------
3
4 5 3

Sample Output-1:
----------------
8

Explanation: 
------------
Pick a number 5 to score 5 points, simultaneously 4 is disappeared from display.
Then, pick number 3 to score 3 points.
Totally 8 is the score.


Sample Input-2:
---------------
6
4 4 5 5 5 6

Sample Output-2:
----------------
15

Explanation: 
-------------
Delete 5 to earn 5 points, deleting both 4's and the 6.
Then, delete 5 again to earn 5 points, and 5 again to earn 5 points.
Totally 15 is the score.

Pick a number 5 to score 5 points, simultaneously all 4's and 6 are disappeared 
from display. Then, again pick the number 5 to score 5 points.
And again pick the number 5 to score 5 points. Totally 15 is the score. 
*/
import java.util.*;

class Solution {
    public static int solve(int[] a, TreeMap<Integer, Integer> mp, int idx, int[] memo) {
        if (idx == 0) return a[idx] * mp.get(a[idx]);
        if (idx == 1) {
            if (a[1] - a[0] > 1)
                return a[1] * mp.get(a[1]) + a[0] * mp.get(a[0]);
            return Math.max(a[1] * mp.get(a[1]), a[0] * mp.get(a[0]));
        }
        if (a[idx] - a[idx - 1] > 1) return a[idx] * mp.get(a[idx]) + solve(a, mp, idx - 1, memo);
        if (memo[idx] != -1) return memo[idx];
        int take = (a[idx] * mp.get(a[idx])) + solve(a, mp, idx - 2, memo);
        int notake = solve(a, mp, idx - 1, memo);
        return memo[idx] = Math.max(take, notake);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        TreeMap<Integer, Integer> mp = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            mp.put(a[i], mp.getOrDefault(a[i], 0) + 1);
        }
        int[] arr = new int[mp.size()];
        int i = 0;
        for (int key: mp.keySet()) {
            arr[i++] = key;
        }
        int[] memo = new int[arr.length];
        Arrays.fill(memo, -1);
        int cnt = solve(arr, mp, mp.size() - 1, memo);
        System.out.println(cnt);
    }
}