/*
You are a renowned Alchemist exploring a mystical forest teeming with magical plants. 
Each plant you encounter has a unique Essence Power represented by an integer in 
the array plants of length n.

By collecting essences from consecutive plants, you can brew powerful Elixirs. 
The potency of an elixir is determined by the sum of the essence powers of the plants used. 
Due to the complexity of brewing, you can only create elixirs using sequences of 
plants that are at least m in length.

Your objective is to find the kth smallest elixir potency that can be brewed 
from these sequences.

Example 1:
Input: n=3 plants = [2, 1, 3], k = 2, m = 2
Output: 4

Explanation:
Possible elixirs (sequences of length ≥ 2):
- [2, 1]: Potency = 2 + 1 = 3
- [1, 3]: Potency = 1 + 3 = 4
- [2, 1, 3]: Potency = 2 + 1 + 3 = 6
Ordered elixir potencies: 3, 4, 6
The 2nd smallest elixir potency is 4.

Input Format:
-------------
Line-1: 3 space separated integers, n, k, m
Line-2: n space separated integers, movieRatings[].

Output Format:
-------------
An integer, The kth smallest elixir potency


Sample Input:
4 3 2
3 -3 5 2

Sample output:
4

Explanation:
Possible elixirs (sequences of length ≥ 2):
- [3, -3]: Potency = 3 + -3 = 0
- [-3, 5]: Potency = -3 + 5 = 2
- [5, 2]: Potency = 5 + 2 = 7
- [3, -3, 5]: Potency = 3 + -3 + 5 = 5
- [-3, 5, 2]: Potency = -3 + 5 + 2 = 4
- [3, -3, 5, 2]: Potency = 3 + -3 + 5 + 2 =7

Ordered elixir potencies: 0, 2, 4, 5, 7, 7
The 3rd smallest elixir potency is 4. 
*/
import java.util.*;
public class P10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int m = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int len = m; len <= n; len++) {
            int temp = 0;
            for (int i = 0; i < len; i++) {
                temp += a[i];
            }
            pq.offer(temp);
            int left = 0;
            for (int i = len; i < n; i++) {
                temp -= a[left];
                temp += a[i];
                pq.offer(temp);
                left++;
            }
        }
        for (int i = 0; i < k - 1; i++) pq.poll();
        System.out.println(pq.poll());
        sc.close();
    }
}
