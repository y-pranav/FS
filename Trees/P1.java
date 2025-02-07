/*
You are developing an application for a garden management system where each tree 
in the garden is represented as a binary tree structure. The system needs to 
allow users to plant new trees in a systematic way, ensuring that each tree is 
filled level by level.

A gardener wants to:
 - Plant trees based on user input.
 - Ensure trees grow in a balanced way by filling nodes level by level.
 - Inspect the garden layout by performing an in-order traversal, which helps 
   analyze the natural arrangement of trees.

Your task is to implement a program that:
    - Accepts a list of N tree species (as integers).
    - Builds a binary tree using level-order insertion.
    - Displays the in-order traversal of the tree.

Input Format:
-------------
- An integer N representing the number of tree plants.
- A space-separated list of N integers representing tree species.

Output Format:
--------------
A list of integers, in-order traversal of tree.


Sample Input:
-------------
7
1 2 3 4 5 6 7

Sample Output:
--------------
4 2 5 1 6 3 7


Explanation:
------------
The tree looks like this:

        1
       / \
      2   3
     / \  / \
    4   5 6  7
The in order is : 4 2 5 1 6 3 7
 
*/

import java.util.*;

public class P1 {
    public static void inorder(TreeNode root, List<Integer> res) {
        if (root == null || root.val == -1) return;
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        int i;
        int[] a = new int[n];
        for (i = 0; i < n; i++) 
            a[i] = sc.nextInt();

        TreeNode root = (a[0] == -1) ? null : new TreeNode(a[0]);
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) {
            q.offer(root);
        }

        i = 1;
        while (!q.isEmpty() && i < n) {
            TreeNode cur = q.poll();
            if (i < n) {
                cur.left = new TreeNode(a[i]);
                q.offer(cur.left);
                i++;
            }
            if (i < n) {
                cur.right = new TreeNode(a[i]);
                q.offer(cur.right);
                i++;
            }
        }
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        System.out.println(res);
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
