/*
You are a gardener designing a beautiful floral pathway in a vast botanical 
garden. The garden is currently overgrown with plants, trees, and bushes 
arranged in a complex branching structure, much like a binary tree. Your task 
is to carefully prune and rearrange the plants to form a single-file walking 
path that visitors can follow effortlessly.

To accomplish this, you must flatten the garden’s layout into a linear sequence 
while following these rules:
    1. The garden path should maintain the same PlantNode structure, 
       where the right branch connects to the next plant in the sequence, 
       and the left branch is always trimmed (set to null).
    2. The plants in the final garden path should follow the same arrangement 
       as a pre-order traversal of the original garden layout. 

Input Format:
-------------
Space separated integers, elements of the tree.

Output Format:
--------------
Print the list.


Sample Input:
-------------
1 2 5 3 4 -1 6

Sample Output:
--------------
1 2 3 4 5 6


Explanation:
------------
input structure:
       1
      / \
     2   5
    / \    \
   3   4    6
   
output structure:
	1
	 \
	  2
	   \
		3
		 \
		  4
		   \
			5
			 \
			  6
 
*/

import java.util.*;

class Solution {
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

    public static void solve(TreeNode root) {
        if (root.left == null) {
            return;
        }
        solve(root.left);
        TreeNode temp = root.right;
        root.right = root.left;
        root.left = null;
        while (root.right != null) {
            root = root.right;
        }
        root.right = temp;
    } 
    
    public static void preOrder(TreeNode root) {
        if (root == null) return;
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(input[i]);
        }
        
        TreeNode root = buildTree(a);
        solve(root);
        preOrder(root);
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

