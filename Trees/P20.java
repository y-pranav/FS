/*
Imagine you’re decoding a secret message that outlines the hierarchical structure 
of a covert spy network. The message is a string composed of numbers and parentheses. 
Here’s how the code works:

- The string always starts with an agent’s identification number, this is the 
  leader of the network.
- After the leader’s ID, there can be zero, one, or two segments enclosed in 
  parentheses. Each segment represents the complete structure of one subordinate 
  network.
- If two subordinate networks are present, the one enclosed in the first (leftmost) 
  pair of parentheses represents the left branch, and the second (rightmost) 
  represents the right branch.

Your mission is to reconstruct the entire spy network hierarchy based on this 
coded message.

Example 1:
Input: 4(2(3)(1))(6(5))
Output: [4, 2, 6, 3, 1, 5]

Spy network:
        4
       / \
      2   6
     / \  /
    3   1 5

Explanation:
Agent 4 is the leader.
Agent 2 (with its own subordinates 3 and 1) is the left branch.
Agent 6 (with subordinate 5) is the right branch.

Example 2:
Input: 4(2(3)(1))(6(5)(7))
Output: [4, 2, 6, 3, 1, 5, 7]

Spy network:
         4
       /   \
      2     6
     / \   / \
    3   1 5   7

Explanation:
Agent 4 leads the network.
Agent 2 with subordinates 3 and 1 forms the left branch.
Agent 6 with subordinates 5 and 7 forms the right branch.
 
*/

/*
Imagine you’re decoding a secret message that outlines the hierarchical structure 
of a covert spy network. The message is a string composed of numbers and parentheses. 
Here’s how the code works:

- The string always starts with an agent’s identification number, this is the 
  leader of the network.
- After the leader’s ID, there can be zero, one, or two segments enclosed in 
  parentheses. Each segment represents the complete structure of one subordinate 
  network.
- If two subordinate networks are present, the one enclosed in the first (leftmost) 
  pair of parentheses represents the left branch, and the second (rightmost) 
  represents the right branch.

Your mission is to reconstruct the entire spy network hierarchy based on this 
coded message.

Example 1:
Input: 4(2(3)(1))(6(5))
Output: [4, 2, 6, 3, 1, 5]

Spy network:
        4
       / \
      2   6
     / \  /
    3   1 5

Explanation:
Agent 4 is the leader.
Agent 2 (with its own subordinates 3 and 1) is the left branch.
Agent 6 (with subordinate 5) is the right branch.

Example 2:
Input: 4(2(3)(1))(6(5)(7))
Output: [4, 2, 6, 3, 1, 5, 7]

Spy network:
         4
       /   \
      2     6
     / \   / \
    3   1 5   7

Explanation:
Agent 4 leads the network.
Agent 2 with subordinates 3 and 1 forms the left branch.
Agent 6 with subordinates 5 and 7 forms the right branch.
*/

import java.util.*;

public class P20 {

    // Definition for a tree node (each agent).
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    /**
     * Parses the spy network string and constructs the corresponding tree.
     *
     * @param s The coded spy network string.
     * @param index A single-element array used to maintain the current parsing position.
     * @return The root of the constructed tree.
     */
    public static TreeNode parseTree(String s, int[] index) {
        if (index[0] >= s.length()) {
            return null;
        }
        
        // Parse the integer (agent's id). It may have multiple digits.
        int num = 0;
        boolean negative = false;
        if (s.charAt(index[0]) == '-') {
            negative = true;
            index[0]++;
        }
        while (index[0] < s.length() && Character.isDigit(s.charAt(index[0]))) {
            num = num * 10 + (s.charAt(index[0]) - '0');
            index[0]++;
        }
        if (negative) {
            num = -num;
        }
        TreeNode root = new TreeNode(num);
        
        // If the next character is '(', parse the left subtree.
        if (index[0] < s.length() && s.charAt(index[0]) == '(') {
            index[0]++;  // skip '('
            root.left = parseTree(s, index);
            index[0]++;  // skip ')'
        }
        
        // If the next character is '(', parse the right subtree.
        if (index[0] < s.length() && s.charAt(index[0]) == '(') {
            index[0]++;  // skip '('
            root.right = parseTree(s, index);
            index[0]++;  // skip ')'
        }
        
        return root;
    }
    
    /**
     * Performs a level order traversal (BFS) of the tree.
     *
     * @param root The root of the tree.
     * @return A list of integers representing the level order traversal.
     */
    public static List<Integer> levelOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            result.add(curr.val);
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Read the coded spy network message
        String input = sc.nextLine().trim();
        
        // Parse the tree from the input string.
        int[] index = new int[1];  // Using an array to simulate a mutable integer (pass-by-reference)
        TreeNode root = parseTree(input, index);
        
        // Get the level order traversal of the tree.
        List<Integer> levelOrderList = levelOrder(root);
        
        // Print the level order list.
        System.out.println(levelOrderList);
        
        sc.close();
    }
}
