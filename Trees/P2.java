/*
Write a program to construct a binary tree from level-order input, while treating -1 
as a placeholder for missing nodes. The program reads input, constructs the tree, 
and provides an in-order traversal to verify correctness.

Input Format:
---------------
Space separated integers, level order data (where -1 indiactes null node).

Output Format:
-----------------
Print the in-order data of the tree.


Sample Input:
----------------
1 2 3 -1 -1 4 5

Sample Output:
----------------
2 1 4 3 5

Explanation:
--------------
    1
   / \
  2   3
     / \
    4   5


Sample Input:
----------------
1 2 3 4 5 6 7

Sample Output:
----------------
4 2 5 1 6 3 7

Explanation:
--------------
        1
       / \
      2   3
     / \  / \
    4  5 6  7
 
*/

import java.util.*;

public class P2 {
    public static void inorder(TreeNode root, List<Integer> res) {
        if (root == null) return;
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] tokens = sc.nextLine().split(" ");
        int n = tokens.length;
  
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(tokens[i]);
        }

        TreeNode root = (a[0] == -1) ? null : new TreeNode(a[0]);
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) {
            q.offer(root);
        }

        int i = 1;
        while (!q.isEmpty() && i < n) {
            TreeNode cur = q.poll();

            if (i < n) {
                if (a[i] != -1) {
                    cur.left = new TreeNode(a[i]);
                    q.offer(cur.left);
                }
                i++;
            }

            if (i < n) {
                if (a[i] != -1) {
                    cur.right = new TreeNode(a[i]);
                    q.offer(cur.right);
                }
                i++;
            }
        }

        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        for (int val : res) {
            System.out.print(val + " ");
        }
        sc.close();
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}
