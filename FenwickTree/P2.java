/*
Imagine you are the chief curator at a futuristic museum. The museum consists of
N exhibition halls arranged in a row, and each hall consists of certain number 
of rare artifacts. 
Your job is to keep track of the total number of artifacts on display and to 
manage special events that temporarily increase the artifact counts in a block 
of halls.

You need to support two types of operations:
1. Count Artifacts: Calculate the total number of artifacts displayed in the 
   exhibition halls between indices start and end (inclusive).  
2. Host Special Exhibition: For a given range of halls (from start to end), 
   increase the number of artifacts in each hall by a specified amount. 
   This simulates a special exhibition event that attracts additional artifacts.

Input Format:
-------------
Line 1: Two integers N and Q, where N is the number of exhibition halls and Q is the number of operations.
Line 2: N space-separated integers representing the initial artifact counts in each hall.
Next Q Lines: Each line contains a query in one of the following formats:
  - For a Count Artifacts query:  
    1 start end
  - For a Host Special Exhibition event:  
    2 start end increment

Output Format:
--------------
For every Count Artifacts query (operation 1), output an integer representing the total number of artifacts in the specified range.

Example
-------
Input:
8 5
1 2 13 4 25 16 17 8
1 2 6
1 0 7
2 2 4 3
2 5 7 2
1 2 7

Output:
75
86
98


Explanation:
- The initial artifact counts in the halls are: [1, 2, 13, 4, 25, 16, 17, 8].
- Query 1: 1 2 6 → Sum halls 2 to 6: 13 + 4 + 25 + 16 + 17 = 75.
- Query 2: 1 0 7 → Sum halls 0 to 7: 1 + 2 + 13 + 4 + 25 + 16 + 17 + 8 = 86.
- Query 3: 2 2 4 3 → A special exhibition increases artifact counts in halls 
  2, 3, and 4 by 3. New counts become: [1, 2, 16, 7, 28, 16, 17, 8].
- Query 4: 2 5 7 2 → Another event boosts halls 5, 6, and 7 by 2. New counts 
  become: [1, 2, 16, 7, 28, 18, 19, 10].
- Query 5: 1 2 7 → Sum halls 2 to 7: 16 + 7 + 28 + 18 + 19 + 10 = 98.


Constraints
- 1 ≤ N ≤ 3×10^4  
- -100 ≤ artifact count in each hall ≤ 100  
- 0 ≤ start ≤ end < N  
- -100 ≤ increment ≤ 100  
- At most 3×10^4 operations will be performed. 
*/

import java.util.*;

class FenwickTree {
    public int[] bit;
    public int n;
    FenwickTree(int size) {
        this.n = size;
        bit = new int[n + 1];
    }    
    public void update(int i, int delta) {
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }
    public int query(int i) {
        int sum = 0;
        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }
    public int rangeQuery(int x, int y) {
        return query(y) - query(x - 1);
    }
    public void build(int[] a) {
        for (int i = 1; i <= a.length; i++) {
            update(i, a[i - 1]);
        }
    }
    public String toString() {
        return Arrays.toString(bit);
    }
}

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        int[] a = new int[n];
        FenwickTree FT = new FenwickTree(n);
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        FT.build(a);
        for (int i = 0; i < q; i++) {
            int option = sc.nextInt();
            if (option == 1) {
                int start = sc.nextInt();
                int end = sc.nextInt();
                System.out.println(FT.rangeQuery(start + 1, end + 1));
            } else {
                int start = sc.nextInt();
                int end = sc.nextInt();
                int inc = sc.nextInt();
                FT.update(index + 1, delta);
            }
        }
        sc.close();
    }
}
