/*
You are given an integer array nums and two integers l and r. Your task is to 
analyze the volatility of a sequence of values. The volatility of a sequence is 
defined as the difference between the maximum and minimum values in that sequence.

You need to determine the sequence with the highest volatility among all 
sequences of length between l and r (inclusive).

Return the highest volatility. If no such sequence exists, return -1.

Input Format:
-------------
Line-1: 3 space separated integers, n, l, r
Line-2: n space separated integers, nums[].

Output Format:
-------------
An integer, the highest volatility.


Sample Input-1:
---------------
5 2 3
8 3 1 6 2

Sample Output-1:
----------------
7

Explanation:
------------
The possible sequences of length between l = 2 and r = 3 are:

[8, 3] with a volatility of 8−3=5
[3, 1] with a volatility of 3−1=2
[1, 6] with a volatility of 6−1=5
[8, 3, 1] with a volatility of 8−1=7
The sequence [8, 3, 1] has the highest volatility of 7.

Sample Input-2:
---------------
4 2 4
5 5 5 5

Sample Output-2:
----------------
0

Explanation:
------------
All possible sequences have no volatility as the maximum and minimum values 
are the same, resulting in a difference of 0.
 
 
*/


// Priority Queue Approach
import java.util.*;

public class P8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int l = sc.nextInt();
        int r = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();
        
        PriorityQueue<Integer> pq1 = new PriorityQueue<>();
        PriorityQueue<Integer> pq2 = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < r; i++) {
            pq1.offer(nums[i]);
            pq2.offer(nums[i]);
        }
        
        int left = 0;
        int res = Math.max(pq2.poll() - pq1.poll(), Integer.MIN_VALUE);
        for (int right = r; right < n; right++) {
            pq1.remove(nums[left]);
            pq2.remove(nums[left]);
            pq1.offer(nums[right]);
            pq2.offer(nums[right]);
            res = Math.max(pq2.peek() - pq1.peek(), res);
            left++;
        }
        System.out.println(res);
        sc.close();
    }
}


// Deques Approach
class DequeApproach {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int l = sc.nextInt();
        int r = sc.nextInt();
        
        int [] arr = new int[n];
        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();

        int maxVolatility = 0;

        for (int len = l; len <= r; len++) {
            maxVolatility = Math.max(maxVolatility, getMaxVolatility(arr, n, len));
        }

        System.out.println(maxVolatility);
        sc.close();
    }

    private static int getMaxVolatility(int[] arr, int n, int len) {
        Deque<Integer> minDeque = new ArrayDeque<>(); // Monotonic increasing queue (min values)
        Deque<Integer> maxDeque = new ArrayDeque<>(); // Monotonic decreasing queue (max values)
        int maxVol = 0;

        for (int i = 0; i < n; i++) {
            // Remove out-of-bound elements
            if (!minDeque.isEmpty() && minDeque.peekFirst() <= i - len) minDeque.pollFirst();
            if (!maxDeque.isEmpty() && maxDeque.peekFirst() <= i - len) maxDeque.pollFirst();

            // Maintain min deque (increasing order)
            while (!minDeque.isEmpty() && arr[minDeque.peekLast()] > arr[i]) minDeque.pollLast();
            minDeque.addLast(i);

            // Maintain max deque (decreasing order)
            while (!maxDeque.isEmpty() && arr[maxDeque.peekLast()] < arr[i]) maxDeque.pollLast();
            maxDeque.addLast(i);

            // Compute volatility after first full window
            if (i >= len - 1) {
                maxVol = Math.max(maxVol, arr[maxDeque.peekFirst()] - arr[minDeque.peekFirst()]);
            }
        }
        return maxVol;
    }
}
