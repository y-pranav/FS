/* Balbir Singh is working with Binary Trees.
The elements of the tree is given in the level order format.
Balbir has a task to split the tree into two parts by removing only one edge
in the tree, such that the product of sums of both the splitted-trees should be maximum.

You will be given the root of the binary tree.
Your task is to help the Balbir Singh to split the binary tree as specified.
print the product value, as the product may be large, print the (product % 1000000007)
	
NOTE: 
Please do consider the node with data as '-1' as null in the given trees.

Input Format:
-------------
Space separated integers, elements of the tree.

Output Format:
--------------
Print an integer value.


Sample Input-1:
---------------
1 2 4 3 5 6

Sample Output-1:
----------------
110

Explanation:
------------
if you split the tree by removing edge between 1 and 4, 
then the sums of two trees are 11 and 10. So, the max product is 110.


Sample Input-2:
---------------
3 2 4 3 2 -1 6

Sample Output-2:
----------------
100
*/
import java.util.*;

public class P14 {
    // Definition for a binary tree node.
    static class TreeNode {
        int val;
        TreeNode left, right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    // Global variable to store the maximum product.
    static long maxProduct = 0;
    // Modulo constant.
    static final int MOD = 1000000007;
    
    /**
     * Builds a binary tree from the given level order input.
     * The value -1 represents a null node.
     *
     * @param nodes an array of integers representing the level order traversal of the tree.
     * @return the root of the constructed binary tree.
     */
    public static TreeNode buildTree(int[] nodes) {
        if (nodes.length == 0 || nodes[0] == -1) {
            return null;
        }
        
        TreeNode root = new TreeNode(nodes[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        // Process nodes in level order.
        while (i < nodes.length && !queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            // Process left child.
            if (i < nodes.length && nodes[i] != -1) {
                current.left = new TreeNode(nodes[i]);
                queue.offer(current.left);
            }
            i++;
            
            // Process right child.
            if (i < nodes.length && nodes[i] != -1) {
                current.right = new TreeNode(nodes[i]);
                queue.offer(current.right);
            }
            i++;
        }
        return root;
    }
    
    /**
     * Computes the sum of the subtree rooted at the given node.
     * During the traversal, for each node (except the overall root), it updates the global maximum
     * product using: product = subtreeSum * (totalSum - subtreeSum).
     *
     * @param root the current node.
     * @param totalSum the total sum of the entire tree.
     * @return the sum of the subtree rooted at this node.
     */
    public static long computeSubtreeSum(TreeNode root, long totalSum) {
        if (root == null) {
            return 0;
        }
        // Sum of the left and right subtrees.
        long leftSum = computeSubtreeSum(root.left, totalSum);
        long rightSum = computeSubtreeSum(root.right, totalSum);
        long currentSum = root.val + leftSum + rightSum;
        
        // For every node except the root, consider splitting at the edge above this node.
        // This is valid because removal of the edge would yield two trees with sums currentSum and totalSum-currentSum.
        if (root != null && root != overallRoot) {
            long product = currentSum * (totalSum - currentSum);
            if (product > maxProduct) {
                maxProduct = product;
            }
        }
        return currentSum;
    }
    
    // To keep a reference to the overall root to avoid considering the whole tree in our split.
    static TreeNode overallRoot;
    
    /**
     * Computes the total sum of the tree.
     *
     * @param root the root of the tree.
     * @return the sum of all node values in the tree.
     */
    public static long computeTotalSum(TreeNode root) {
        if (root == null) return 0;
        return root.val + computeTotalSum(root.left) + computeTotalSum(root.right);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Read the entire line of space separated integers.
        String line = sc.nextLine().trim();
        String[] tokens = line.split("\\s+");
        int[] nodes = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            nodes[i] = Integer.parseInt(tokens[i]);
        }
        
        // Build the tree.
        overallRoot = buildTree(nodes);
        
        // Compute the total sum of the tree.
        long totalSum = computeTotalSum(overallRoot);
        
        // Compute subtree sums and update maxProduct.
        computeSubtreeSum(overallRoot, totalSum);
        
        // Print the maximum product modulo 1000000007.
        System.out.println(maxProduct % MOD);
        
        sc.close();
    }
}
