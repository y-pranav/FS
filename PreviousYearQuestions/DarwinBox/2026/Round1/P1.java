/*
You’re given an integer n and an array of n integers, nums[0] through nums[n–1]. Starting with the value nums[0], you must insert exactly one of the three operators +, –, or * between each successive pair of numbers, evaluating strictly from left to right. That is, you form an expression of the form
(…((nums[0] op1 nums[1]) op2 nums[2]) … op[n–1] nums[n–1])
where each opi is either +, –, or *.
*/

import java.util.*;

class P1 {
    public static int res;
    public static void backtrack(int n, int[] nums, int idx, int temp) {
        if (idx == n) {
            res = Math.min(temp, res);
            System.out.println("temp = " + temp);
            System.out.println("res = " + res);
            return;
        }
        for (int op = 0; op < 3; op++) {
            if (op == 0) {
                temp = temp - nums[idx];
            } else if (op == 1) {
                temp = temp * nums[idx];
            } else {
                temp = temp + nums[idx];
            }
            System.out.println("op = " + op + " " + "temp = " + temp);
            backtrack(n, nums, idx + 1, temp); 
            if (op == 0) {
                temp = temp + nums[idx];
            } else if (op == 1) {
                temp = temp / nums[idx];
            } else {
                temp = temp - nums[idx];
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        res = Integer.MAX_VALUE;
        backtrack(n, nums, 1, nums[0]);
        System.out.println(res);
    }
}