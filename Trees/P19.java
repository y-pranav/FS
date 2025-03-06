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
Agent 6 with subordinates 5 and 7 forms the right branch.
 
*/

import java.util.*;

class P19 {
  public static TreeNode buildTree(int[] a) {
    Queue<TreeNode> q = new LinkedList<>();
    TreeNode root = new TreeNode(a[0]);
    q.offer(root);

    int i = 1, n = a.length;
    while (!q.isEmpty()) {
        TreeNode cur = q.poll();

        if (cur.left == null && i < n && a[i] != -1) {
            cur.left = new TreeNode(a[i]);
            q.offer(cur.left);
        }
        i++;

        if (cur.right == null && i < n && a[i] != -1) {
            cur.right = new TreeNode(a[i]);
            q.offer(cur.right);
        }
        i++;
    }
    return root;
}

  public void solve(TreeNode root) {
      List<Integer> result = new ArrayList<>();
      levelOrder(root, result);
      System.out.println(result);
    }
  
    private void levelOrder(TreeNode node, List<Integer> result) {
      if (node == null) return;
      Queue<TreeNode> q = new LinkedList<>();
      q.offer(node);
      while (!q.isEmpty()) {
        TreeNode cur = q.poll();
        result.add(cur.val);
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
    }
  
    public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      String input = sc.nextLine();
      
      TreeNode root = parseInput(input);
      P19 solution = new P19();
      solution.solve(root);
    }
  
    private static TreeNode parseInput(String s) {
      int[] index = {0};
      return parseHelper(s, index);
    }
  
    private static TreeNode parseHelper(String s, int[] index) {
      if (index[0] >= s.length()) return null;
  
      StringBuilder num = new StringBuilder();
      while (index[0] < s.length() && Character.isDigit(s.charAt(index[0]))) {
        num.append(s.charAt(index[0]));
        index[0]++;
      }
  
      TreeNode node = new TreeNode(Integer.parseInt(num.toString()));
  
      if (index[0] < s.length() && s.charAt(index[0]) == '(') {
        index[0]++; // skip '('
        node.left = parseHelper(s, index);
        index[0]++; // skip ')'
      }
  
      if (index[0] < s.length() && s.charAt(index[0]) == '(') {
        index[0]++; // skip '('
        node.right = parseHelper(s, index);
        index[0]++; // skip ')'
      }
  
      return node;
    }
}

class TreeNode {
  TreeNode left;
  TreeNode right;
  int val;
  TreeNode(int val) {
      this.left = null;
      this.right = null;
      this.val = val;
  }
}
