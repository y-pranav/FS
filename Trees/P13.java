/*
Balbir Singh is working with networked systems, where servers are connected 
in a hierarchical structure, represented as a Binary Tree. Each server (node) has 
a certain processing load (integer value).

Balbir has been given a critical task: split the network into two balanced 
sub-networks by removing only one connection (edge). The total processing 
load in both resulting sub-networks must be equal after the split.

Network Structure:
- The network of servers follows a binary tree structure.
- Each server is represented by an integer value, indicating its processing load.
- If a server does not exist at a particular position, it is represented as '-1' (NULL).
	
Help Balbir Singh determine if it is possible to split the network into two equal 
processing load sub-networks by removing exactly one connection.

Input Format:
-------------
Space separated integers, elements of the tree.

Output Format:
--------------
Print a boolean value.


Sample Input-1:
---------------
1 2 3 5 -1 -1 5

Sample Output-1:
----------------
true


Sample Input-2:
---------------
3 2 4 3 2 -1 7

Sample Output-2:
----------------
false

*/

import java.util.*;

public class P13 {
    
    // Definition of a tree node (server) representing its processing load.
    static class TreeNode {
        int val;
        TreeNode left, right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // Global flag to indicate if a valid split is found.
    static boolean canSplit = false;
    
    /**
     * Builds a binary tree from the given array of integers, which represents the tree in level order.
     * The value -1 indicates a null node.
     *
     * @param nodes an array of integers representing server loads and null markers.
     * @return the root of the constructed binary tree.
     */
    public static TreeNode buildTree(int[] nodes) {
        if (nodes.length == 0 || nodes[0] == -1) {
            return null;
        }
        
        // Create the root node.
        TreeNode root = new TreeNode(nodes[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (i < nodes.length && !queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            // Process left child.
            if (i < nodes.length) {
                if (nodes[i] != -1) {
                    current.left = new TreeNode(nodes[i]);
                    queue.offer(current.left);
                }
                i++;
            }
            
            // Process right child.
            if (i < nodes.length) {
                if (nodes[i] != -1) {
                    current.right = new TreeNode(nodes[i]);
                    queue.offer(current.right);
                }
                i++;
            }
        }
        
        return root;
    }
    
    /**
     * Recursively computes the sum of the subtree rooted at the given node.
     * During the traversal, if a subtree (excluding the entire tree) is found with a sum 
     * equal to half of the total sum, it sets the global flag indicating that a valid split is possible.
     *
     * @param root the current node.
     * @param totalSum the total processing load of the entire tree.
     * @return the sum of the subtree rooted at this node.
     */
    public static int subtreeSum(TreeNode root, int totalSum) {
        if (root == null) {
            return 0;
        }
        
        // Recursively compute left and right subtree sums.
        int leftSum = subtreeSum(root.left, totalSum);
        int rightSum = subtreeSum(root.right, totalSum);
        
        int currentSum = root.val + leftSum + rightSum;
        
        // Check if this subtree (which is not the entire tree) can form a valid split.
        // Ensure that we are not considering the whole tree (i.e., root != total tree root)
        if (root != null && currentSum == totalSum / 2 && totalSum % 2 == 0 && root != overallRoot) {
            canSplit = true;
        }
        
        return currentSum;
    }
    
    // Store reference to the overall root so that we don't consider the whole tree in our check.
    static TreeNode overallRoot;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read the entire line of space separated integers.
        String line = sc.nextLine().trim();
        String[] tokens = line.split("\\s+");
        int[] nodes = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            nodes[i] = Integer.parseInt(tokens[i]);
        }
        
        // Build the binary tree (network) from the input.
        overallRoot = buildTree(nodes);
        
        // Compute the total processing load of the network.
        int totalSum = subtreeSum(overallRoot, 0); // Using a temporary totalSum=0; we will recompute in a separate call.
        // We need to recompute totalSum properly without interfering with our split check.
        totalSum = computeTotal(overallRoot);
        
        // Reset the flag before the subtree sum computation for the split check.
        canSplit = false;
        subtreeSum(overallRoot, totalSum);
        
        // Print the result: true if a valid split is possible, else false.
        System.out.println(canSplit);
        
        sc.close();
    }
    
    /**
     * Computes the total processing load of the entire tree.
     *
     * @param root the root of the tree.
     * @return the total sum of all nodes.
     */
    public static int computeTotal(TreeNode root) {
        if (root == null) return 0;
        return root.val + computeTotal(root.left) + computeTotal(root.right);
    }
}
