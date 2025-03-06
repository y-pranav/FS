/*
Imagine you're a botanist managing a long row of garden plots. Each plot is 
planted with a flower that has a unique color code represented by an integer. 
You are given an array garden of length n, where each element denotes the color 
of the flower in that plot, and an integer k which indicates the number of 
consecutive plots you will examine at a time. Your objective is to determine 
how many different flower colors appear in every contiguous block of k plots.

Return an array result where result[i] is the count of unique flower colors in 
the section of the garden from plot i to plot i + k - 1.

Example 1:

Input: 
7				\\n
1 2 3 2 2 1 3	\\array garden
3				\\k

Output: 
[3, 2, 2, 2, 3]

Explanation:

For plots 0 to 2: [1,2,3] → 3 unique colors
For plots 1 to 3: [2,3,2] → 2 unique colors
For plots 2 to 4: [3,2,2] → 2 unique colors
For plots 3 to 5: [2,2,1] → 2 unique colors
For plots 4 to 6: [2,1,3] → 3 unique colors
Example 2:

Input: 
7
1 1 1 1 2 3 4
4

Output: 
[1, 2, 3, 4]

Explanation:
For plots 0 to 3: [1,1,1,1] → 1 unique color
For plots 1 to 4: [1,1,1,2] → 2 unique colors
For plots 2 to 5: [1,1,2,3] → 3 unique colors
For plots 3 to 6: [1,2,3,4] → 4 unique colors

Constraints:
• 1 <= k <= garden.length <= 10^5
• 1 <= garden[i] <= 10^5 
 
*/

import java.util.*;

class P11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        int k = sc.nextInt();
        
        int[] res = new int[n - k + 1];
        HashMap<Integer, Integer> mp = new HashMap<>();
        
        int left = 0;
        for (int i = 0; i < n; i++) {
            mp.put(a[i], mp.getOrDefault(a[i], 0) + 1);
            if (i >= k - 1) {
                res[i - k + 1] = mp.size();
                mp.put(a[left], mp.get(a[left]) - 1);
                if (mp.get(a[left]) == 0) mp.remove(a[left]);
                left++;
            }
        }
        System.out.println(Arrays.toString(res));
        sc.close();
    }
}
