/*
Imagine you are designing a network of secret corridors in an ancient castle. 
Each chamber in the castle leads to at most two other chambers through 
hidden passageways, forming a branching layout. 
The castle’s "longest secret route" is defined as the maximum number of corridors 
you must traverse to get from one chamber to another (without repeating the corridor). 
This route may or may not pass through the main entry chamber.

Your task is to compute the length of longest secret route between 
two chambers which is represented by the number of corridors between them.

Example 1
input=
1 2 3 4 5 
output=
3

Structure:
       1
      / \
     2   3
    / \
   4   5

Explanation:
The longest secret route from chamber 4 to chamber 3 
(alternatively, from chamber 5 to chamber 3) along the route:
4 → 2 → 1 → 3
This path has 3 corridors (4–2, 2–1, 1–3), so the length is 3.

Example 2:
input=
1 -1 2 3 4++
output=
2

Structure:
   1
    \
     2
    / \
   3   4

Explanation:
The longest secret route from chamber 3 to chamber 4 
(alternatively, from chamber 1 to chamber 4) along the route:
3 → 2 → 4
This path has 2 corridors (3–2, 2–4), so the length is 2.
 
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
    
    static int res = 0;
    public static int solve(TreeNode root) {
        if (root == null) return 0;
        int left = solve(root.left);
        int right = solve(root.right);
        res = Math.max(left + right, res);
        return Math.max(left, right) + 1;
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

