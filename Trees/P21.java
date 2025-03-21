/*
Imagine a company where each employee has a performance score, and 
the organizational chart is structured as a binary tree with the CEO at the top. 
An employee is considered "outstanding" if, along the chain of command from the 
CEO down to that employee, no one has a performance score higher than that 
employee's score. Your task is to determine the total number of outstanding 
employees in the company.

Example 1:
Input: 3 1 4 3 -1 1 5
Output: 4

Chart formed:
         3
        / \
       1   4
      /   / \
     3   1   5

Explanation:
- The CEO (score 3) is automatically outstanding.
- The employee with score 4, whose chain is [3,4], is outstanding because 4 
  is higher than 3.
- The employee with score 5, following the path [3,4,5], is outstanding as 5 
  is the highest so far.
- The subordinate with score 3, along the path [3,1,3], is outstanding because 
  none of the managers in that chain have a score exceeding 3.

Example 2:
Input: 3 3 -1 4 2
Output: 3

Chart formed:
       3
      / 
     3
    / \
   4   2

Explanation:
- The CEO (score 3) is outstanding.
- The subordinate with score 3 (chain: [3,3]) is outstanding.
- The employee with score 2 (chain: [3,3,2]) is not outstanding because the 
  managers have higher scores.
 
*/

/*
Imagine a company where each employee has a performance score, and the organizational chart 
is structured as a binary tree with the CEO at the top. An employee is considered "outstanding" 
if, along the chain of command from the CEO down to that employee, no one has a performance 
score higher than that employee's score. Your task is to determine the total number of 
outstanding employees in the company.

Example 1:
Input: 3 1 4 3 -1 1 5
Output: 4

Chart formed:
         3
        / \
       1   4
      /   / \
     3   1   5

Explanation:
- The CEO (score 3) is automatically outstanding.
- The employee with score 4, whose chain is [3,4], is outstanding because 4 is higher than 3.
- The employee with score 5, following the path [3,4,5], is outstanding as 5 is the highest so far.
- The subordinate with score 3, along the path [3,1,3], is outstanding because none of the managers 
  in that chain have a score exceeding 3.

Example 2:
Input: 3 3 -1 4 2
Output: 3

Chart formed:
       3
      / 
     3
    / \
   4   2

Explanation:
- The CEO (score 3) is outstanding.
- The subordinate with score 3 (chain: [3,3]) is outstanding.
- The employee with score 2 (chain: [3,3,2]) is not outstanding because the managers have higher scores.
*/

import java.util.*;

public class P21 {

    // Definition for a binary tree node.
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Builds a binary tree from the given level order input array.
     * The value -1 represents a null (non-existent) node.
     *
     * @param nodes An array of integers representing the level order traversal.
     * @return The root of the constructed binary tree.
     */
    public static TreeNode buildTree(int[] nodes) {
        if (nodes.length == 0 || nodes[0] == -1) {
            return null;
        }
        TreeNode root = new TreeNode(nodes[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        while (i < nodes.length && !queue.isEmpty()) {
            TreeNode current = queue.poll();
            // Process left child
            if (i < nodes.length && nodes[i] != -1) {
                current.left = new TreeNode(nodes[i]);
                queue.offer(current.left);
            }
            i++;
            // Process right child
            if (i < nodes.length && nodes[i] != -1) {
                current.right = new TreeNode(nodes[i]);
                queue.offer(current.right);
            }
            i++;
        }
        return root;
    }
    
    /**
     * Recursively traverses the tree to count outstanding employees.
     * An employee is outstanding if its score is greater than or equal to all 
     * scores encountered along the path from the CEO (root) to that employee.
     *
     * @param node The current node in the tree.
     * @param maxSoFar The maximum performance score encountered on the path so far.
     * @return The count of outstanding employees in the subtree rooted at this node.
     */
    public static int countOutstanding(TreeNode node, int maxSoFar) {
        if (node == null) {
            return 0;
        }
        
        int count = 0;
        // If current employee's score is not less than maxSoFar, then this employee is outstanding.
        if (node.val >= maxSoFar) {
            count = 1;
        }
        // Update the maximum score for the next level.
        int newMax = Math.max(maxSoFar, node.val);
        count += countOutstanding(node.left, newMax);
        count += countOutstanding(node.right, newMax);
        return count;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Read the input line (space separated integers)
        String line = sc.nextLine().trim();
        String[] tokens = line.split("\\s+");
        int[] nodes = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            nodes[i] = Integer.parseInt(tokens[i]);
        }
        
        // Build the binary tree representing the organizational chart.
        TreeNode root = buildTree(nodes);
        
        // The CEO is always outstanding, so we start with the CEO's score.
        int outstandingCount = countOutstanding(root, root.val);
        
        // Print the count of outstanding employees.
        System.out.println(outstandingCount);
        
        sc.close();
    }
}
