// Mr. Rakesh is interested in working with Data Structures.

// He has constructed a Binary Tree (BT) and asked his friend 
// Anil to check whether the BT is a self-mirror tree or not.

// Can you help Rakesh determine whether the given BT is a self-mirror tree?
// Return true if it is a self-mirror tree; otherwise, return false.

// Note:
// ------
// In the tree, '-1' indicates an empty (null) node.

// Input Format:
// -------------
// A single line of space separated integers, values at the treenode

// Output Format:
// --------------
// Print a boolean value.


// Sample Input-1:
// ---------------
// 2 1 1 2 3 3 2

// Sample Output-1:
// ----------------
// true


// Sample Input-2:
// ---------------
// 2 1 1 -1 3 -1 3

// Sample Output-2:
// ----------------
// false


import java.util.*;

public class P10 {
    public static TreeNode buildTree(String[] input) {
        if (input.length == 0 || input[0].equals("-1")) {
            return null;
        }
        
        int index = 0;
        TreeNode root = new TreeNode(Integer.parseInt(input[index++]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty() && index < input.length) {
            TreeNode current = queue.poll();
            
            if (index < input.length && !input[index].equals("-1")) {
                current.left = new TreeNode(Integer.parseInt(input[index]));
                queue.offer(current.left);
            }
            index++;
            
            if (index < input.length && !input[index].equals("-1")) {
                current.right = new TreeNode(Integer.parseInt(input[index]));
                queue.offer(current.right);
            }
            index++;
        }
        
        return root;
    }
    
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }
    
    private boolean isMirror(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        return (node1.val == node2.val) &&
               isMirror(node1.left, node2.right) &&
               isMirror(node1.right, node2.left);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        TreeNode root = buildTree(input);
        
        P10 solution = new P10();
        boolean result = solution.isSymmetric(root);
        System.out.println(result);
        
        sc.close();
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}
