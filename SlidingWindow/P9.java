/*
You are an architect designing a street with houses represented as a 0-indexed array 
house_heights of n integers, where each element represents the height of a house. 
Additionally, a binary array visibility_mask of length n is provided, where 1 indicates 
a house that contributes to the neighborhood's skyline, and 0 indicates a house that does not.

For any house located at index i, you are tasked with determining the average 
skyline height within a k-radius neighborhood centered at i. The average skyline 
height is the average of all house heights between indices i - k and i + k (inclusive) 
that have a corresponding visibility value of 1 in the visibility_mask. 

If no houses with a visibility of 1 exist in the range, or if the range extends 
beyond the boundaries of the array, the average skyline height for that house is -1.

Return an array skyline_avgs of length n, where skyline_avgs[i] is the average 
skyline height for the neighborhood centered at index i.

Example 1:
Input:
house_heights = [10, 15, 20, 5, 30, 25, 40]
visibility_mask = [1, 0, 1, 1, 0, 1, 1]
k = 2

Output:
skyline_avgs = [-1, -1, 11, 16, 22, -1, -1]


Explanation:
- For index 0, there are less than k houses in the left neighborhood, 
  so skyline_avgs[0] = -1.
- For index 1, there are less than k houses in the left neighborhood, 
  so skyline_avgs[1] = -1.
- For index 2, the neighborhood is [10, 15, 20, 5, 30]. From the visibility_mask, 
  only the houses with heights [10, 20, 5] contribute to the skyline. The average 
  is (10 + 20 + 5) / 3 = 11.
- For index 3, the neighborhood is [15, 20, 5, 30, 25]. From the visibility_mask, 
  only the houses with heights [20, 5, 25] contribute. 
  The average is (20 + 5 + 25) / 3 = 16.
- For index 4, the neighborhood is [20, 5, 30, 25, 40]. From the visibility_mask, 
  only the houses with heights [20, 5, 25, 40] contribute. The average 
  is (20 + 5 + 25 + 40) / 4 = 22.
- For index 5, there are less than k houses in the right neighborhood, 
  so skyline_avgs[5] = -1.
- For index 6, there are less than k houses in the right neighborhood, 
  so skyline_avgs[6] = -1.

Sample Input:
3
50 60 70
1 1 1
1

Sample Output:
[-1, 60, -1]

Constraints:
1. n == house_heights.length == visibility_mask.length
2. 1 <= n <= 10^5
3. 0 <= house_heights[i] <= 10^5
4. visibility_mask[i] is either 0 or 1
5. 0 <= k <= n
 
*/

import java.util.*;


public class P9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        sc.nextLine();
        int[] h = new int[n];
        int[] v = new int[n];
        for (int i = 0; i < n; i++) h[i] = sc.nextInt();
        for (int i = 0; i < n; i++) v[i] = sc.nextInt();
        
        int[] res = new int[n];
        Arrays.fill(res, -1);
        
        if (2 * k + 1 > n) {
            System.out.println(Arrays.toString(res));
            return;
        }
     
        int temp = 0, c = 0;
        int size = 2 * k + 1;
        for (int i = 0; i < size; i++) {
            if (v[i] == 1) {
                temp += h[i];
                c++;
            }
        }
        if (k < n) res[k] = c > 0 ? temp / c : -1;
        
        int left = 0;
        for (int i = size; i < n; i++) {
            if (v[left] == 1) {
                temp -= h[left];
                c--;
            }
            if (v[i] == 1) {
                temp += h[i];
                c++;
            }
            res[i - k] = c > 0 ? temp / c : -1;
            left++;
        }
        System.out.println(Arrays.toString(res));
        sc.close();
    }
}