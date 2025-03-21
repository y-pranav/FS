/* In an Intelligence Agency, each senior officer supervises either two junior officers 
or none. The senior officer is assigned a clearance level equal to the higher clearance 
level of the two junior officers they supervise.

The clearance levels are represented as integer values in the range [1, 50], and multiple 
officers may have the same clearance level.

At the end, all officers (senior and junior) are collectively referred to as agents in the system.

You are provided with a hierarchical clearance level tree where each node represents 
an officer's clearance level. The tree structure follows these rules:
	- If a node has two children, its clearance level is the maximum of the two children's
	  clearance levels.
	- If a node has no children, it's clearance level is same as exists.
	- The value -1 indicates an empty (null) position.
Your task is to find the second highest clearance level among all agents in the agency. 
If no such level exists, return -2.

Input Format:
-------------
A single line of space separated integers, clearance levels of each individual.

Output Format:
--------------
Print an integer, second top agent based on rank.


Sample Input-1:
---------------
5 5 4 -1 -1 2 4

Sample Output-1:
----------------
4


Sample Input-2:
---------------
3 3 3 3 3

Sample Output-2:
----------------
-2

*/
/*

Node for reference

class TreeNode {
    Integer val;
    TreeNode left, right;
    
    TreeNode(Integer val) {
        this.val = val;
        this.left = this.right = null;
    }
}

*/

import java.util.*;

public class P12 {
    
    // TreeNode class to represent an officer in the clearance tree.
    static class TreeNode {
        Integer val;
        TreeNode left, right;
        
        TreeNode(Integer val) {
            this.val = val;
            this.left = this.right = null;
        }
    }
    
    /**
     * Builds the binary tree from the given array of integers, which represents the tree
     * in level order. The value -1 is used to indicate a null (empty) position.
     *
     * @param nodes an array of integers representing clearance levels and null markers
     * @return the root of the constructed binary tree
     */
    public static TreeNode buildTree(int[] nodes) {
        if (nodes.length == 0 || nodes[0] == -1) {
            return null;
        }
        
        TreeNode root = new TreeNode(nodes[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        
        // Process the input tokens level by level.
        while (i < nodes.length && !queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            // For a full binary tree, if current is not a leaf, it must have two children.
            // Read left child.
            if (i < nodes.length) {
                if (nodes[i] != -1) {
                    current.left = new TreeNode(nodes[i]);
                    queue.offer(current.left);
                }
                i++;
            }
            // Read right child.
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
     * Traverses the binary tree (using any order, here we use simple pre-order traversal)
     * to collect all distinct clearance levels.
     *
     * @param root the root of the binary tree
     * @param levels a set to store distinct clearance levels
     */
    public static void traverseTree(TreeNode root, Set<Integer> levels) {
        if (root == null) {
            return;
        }
        // Add current node's clearance level.
        levels.add(root.val);
        traverseTree(root.left, levels);
        traverseTree(root.right, levels);
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
        
        // Build the clearance level tree from the input.
        TreeNode root = buildTree(nodes);
        
        // Use a set to collect distinct clearance levels.
        Set<Integer> distinctLevels = new HashSet<>();
        traverseTree(root, distinctLevels);
        
        // If there is less than 2 distinct clearance levels, output -2.
        if (distinctLevels.size() < 2) {
            System.out.println(-2);
            sc.close();
            return;
        }
        
        // Find the highest and second highest clearance levels.
        // We sort the distinct levels in descending order.
        List<Integer> levelList = new ArrayList<>(distinctLevels);
        Collections.sort(levelList, Collections.reverseOrder());
        
        // The second element in the sorted list is the second highest.
        System.out.println(levelList.get(1));
        sc.close();
    }
}


