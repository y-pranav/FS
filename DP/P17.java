/*
For the given a list of integers, Your task to is find out, the length of the 
longest subsequence that is a toggle sequence.

Toggle Sequence means, the difference between the consecutive numbers
are alternate positive and negative.

For Example:
Given list of integers = 1 3 2 5 4 
the consecutive differences are [ 2, -1, 3, -1], 
the differences are alternate +ve and -ve.
So, complete list is a toggle sequence.

If the list of integers = 1 3 2 4 5,
the consecutive differences are [ 2, -1, 2, 1], not alternate +ve and -ve.
Not a toggle sequence.

Note: A sequence with fewer than two elements is a toggle sequence.

Input Format:
-------------
Space separated Integers, List

Output Format:
--------------
Print the length of the longest toggle sequence


Sample Input-1:
---------------
1 7 4 9 2 5

Sample Output-1:
----------------
6

Explanation:
------------
Given list of integers = 1 7 4 9 2 5
the consecutive differences are [ 6, -3, 5, -7, 3], 
the differences are alternate +ve and -ve.
So, complete list is a toggle sequence.

Sample Input-2:
---------------
1 5 4 3 7 9 10

Sample Output-2:
----------------
4

Explanation:
------------
Given list of integers = 1 5 4 3 7 9 10
There are several subsequences that achieve this length.
One is [1 5 4 7] with differences (4, -1, 3). 
*/

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(input[i]);
        }
        int[] d = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            d[i] = a[i + 1] - a[i];
        }
        int[] dp = new int[n - 1];
        Arrays.fill(dp, 1);
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < i; j++) {
                if ((d[i] > 0 && d[j] < 0) || (d[i] < 0 && d[j] > 0)) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < n - 1; i++) {
            res = Math.max(dp[i], res);
        }
        System.out.println(res + 1);
    }
}
