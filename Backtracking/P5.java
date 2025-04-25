/*
Bablu is working in a construction field.
He has N number of building blocks, where the height and width of all the blocks are same.
And the length of each block is given in an array, blocks[].

Bablu is planned to build a wall in the form of a square.
The rules to cunstruct the wall are as follows:
	- He should use all the building blocks.
	- He should not break any building block, but you can attach them with other.
	- Each building-block must be used only once.
	
Your task is to check whether Bablu can cunstruct the wall as a square
with the given rules or not. If possible, print true. Otherwise, print false.

Input Format:
-------------
Line-1: An integer N, number of BuildingBlocks.
Line-2: N space separated integers, length of each block.

Output Format:
--------------
Print a boolean value.


Sample Input-1:
---------------
6
1 2 6 4 5 6
1 2 4 5 6 6 

Sample Output-1:
----------------
true


Sample Input-2:
---------------
6
5 3 2 5 5 6
2 3 5 5 5 6 

Sample Output-2:
----------------
false

*/

import java.util.*;

class P5 {
    public static boolean dfs(int[] blocks, int index, int[] sides, int target) {
        if (index == blocks.length) {
            return sides[0] == target && sides[1] == target && sides[2] == target;
        }
        for (int i = 0; i < 4; i++) {
            if (sides[i] + blocks[index] <= target) {
                sides[i] += blocks[index];
                if (dfs(blocks, index + 1, sides, target))
                    return true;
                sides[i] -= blocks[index];
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sum = 0;
        int[] blocks = new int[n];
        for (int i = 0; i < n; i++) {
            blocks[i] = sc.nextInt();
            sum += blocks[i];
        }
        
        if (sum % 4 != 0) {
            System.out.println("false");
            return;
        }
        
        int target = sum / 4;
        int[] sides = new int[4];
        
        System.out.println(dfs(blocks, 0, sides, target));
    }
}

/*
 Below is an explanation of the issues with your code together with a corrected version.

---

### Issues with Your Code

1. **No “Used” Mechanism:**  
   In your recursive method, you iterate over all indices every time without marking blocks as “used.” This means the same block might be used repeatedly even though it should be used only once.

2. **Improper Base Conditions:**  
   Your base case checks if `cur == sum && count == 4 && idx == a.length - 1` to return true. This condition is not sufficient or correct to ensure that each of the 4 sides equals the required length. Typically, once you have built 3 sides with the target length, the remaining blocks will automatically form the 4th side if they sum correctly.

3. **Inefficient and Overlapping Recursion:**  
   The recursion iterates over all indices for every branch (using both taking and not taking a block) instead of deciding for each block which side it should be placed in. This will also lead to exploring many unnecessary branches and possibly incorrect assignments.

4. **Target Calculation Confusion:**  
   Your code sums up the block lengths into `sum` and then uses this same `sum` as the target for each side instead of `sum/4`. The target for each side of the square should be one‑fourth of the total length.

---

### Correct Approach

A common way to solve this “Square Partitioning” problem is to:

- **Check if the total sum is divisible by 4.** If not, it’s impossible to form a square.  
- **Sort the blocks (preferably in descending order)** so that you place larger blocks first. This often helps in pruning branches early in the recursion (and check if the largest block is greater than the side target).  
- **Backtrack by assigning each block to one of the 4 sides,** ensuring that the side length does not exceed the target value.

Once you have assigned all blocks, if 3 sides are correctly built to the target, the 4th side will be correct by default.

---

### Corrected Code

Below is the corrected Java code:

```java
import java.util.*;

public class Solution {

    // Helper method that attempts to assign each block at index 'index' 
    // to one of the 4 sides represented by the array 'sides' so that every side equals 'target'.
    public static boolean dfs(int[] blocks, int index, int[] sides, int target) {
        if (index == blocks.length) {
            // if the first three sides equal target, the fourth must too
            return sides[0] == target && sides[1] == target && sides[2] == target;
        }
        // Try to place the current block into one of the four sides.
        for (int i = 0; i < 4; i++) {
            // Check if adding this block would not exceed the target for this side.
            if (sides[i] + blocks[index] <= target) {
                sides[i] += blocks[index];
                if (dfs(blocks, index + 1, sides, target))
                    return true;
                // Backtrack
                sides[i] -= blocks[index];
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sum = 0;
        int[] blocks = new int[n];
        for (int i = 0; i < n; i++) {
            blocks[i] = sc.nextInt();
            sum += blocks[i];
        }
        
        // The sum of all blocks must be divisible by 4.
        if (sum % 4 != 0) {
            System.out.println("false");
            return;
        }
        
        int target = sum / 4;
        
        // Sort the blocks in descending order to help prune impossible combinations quickly.
        Arrays.sort(blocks);
        // Reverse the sorted array.
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            int temp = blocks[i];
            blocks[i] = blocks[j];
            blocks[j] = temp;
        }
        
        // Quick check: if the largest block is greater than the target side length, it's impossible.
        if (blocks[0] > target) {
            System.out.println("false");
            return;
        }
        
        // Array to keep track of the current sum of each of the 4 sides.
        int[] sides = new int[4];
        
        System.out.println(dfs(blocks, 0, sides, target));
    }
}
```

---

### Reasoning Behind the Corrected Code

- **Divisibility Check:**  
  We first verify that the total sum is divisible by 4. If not, it is immediately impossible to form a square.

- **Descending Order:**  
  Sorting the blocks in descending order ensures that when you place a block, you add the largest ones first. This helps in quickly eliminating branches where the side sum would exceed the target.

- **Recursive Backtracking:**  
  The `dfs` function tries to place the current block (identified by `index`) in each side (if possible without exceeding `target`), then recursively calls itself for the next block. Backtracking (removing the block from the side if the next assignment does not lead to a solution) ensures every possibility is tried.

- **Base Case:**  
  When all blocks have been assigned (i.e., when `index` equals the total number of blocks), we check if at least 3 sides equal the target. If they do, the remaining side is guaranteed to be the correct sum.

This approach correctly satisfies the constraints (using all blocks exactly once without breaking any) and checks if a square wall is possible.
*/