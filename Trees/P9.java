/*
Bubloo is working with computer networks, where servers are connected 
in a hierarchical structure, represented as a Binary Tree. Each server (node) 
is uniquely identified by an integer value.

Bubloo has been assigned an important task: find the shortest communication 
path (in terms of network hops) between two specific servers in the network.

Network Structure:
------------------
The network of servers follows a binary tree topology.
Each server (node) has a unique identifier (integer).
If a server does not exist at a certain position, it is represented as '-1' (NULL).

Given the root of the network tree, and two specific server IDs (E1 & E2), 
determine the minimum number of network hops (edges) required to 
communicate between these two servers.

Input Format:
-------------
Space separated integers, elements of the tree.

Output Format:
--------------
Print an integer value.


Sample Input-1:
---------------
1 2 4 3 5 6 7 8 9 10 11 12
4 8

Sample Output-1:
----------------
4

Explanation:
------------
The edegs between 4 and 8 are: [4,1], [1,2], [2,3], [3,8]


Sample Input-2:
---------------
1 2 4 3 5 6 7 8 9 10 11 12
6 6

Sample Output-2:
----------------
0

Explanation:
------------
No edegs between 6 and 6.
 
*/

import java.util.*;

public class P9 {
    public static TreeNode buildTree(int[] a) {
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(a[0]);
        q.offer(root);
        int i = 1, n = a.length;
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (i < n && a[i] != -1) {
                cur.left = new TreeNode(a[i]);
                q.offer(cur.left);
            } 
            i++;
            if (i < n && a[i] != -1) {
                cur.right = new TreeNode(a[i]);
                q.offer(cur.right);
            } 
            i++;
        }
        return root;
    }
    public static int solve(TreeNode root, int p, int q) {
        if (p == q) 
            return 0;
            
        if (root == null) 
            return -1;

        if (root.val == p || root.val == q) 
            return 0;
        
        int left = solve(root.left, p, q);
        if (left != -1) 
            left++;
        
        int right = solve(root.right, p, q);
        if (right != -1) 
            right++;
        
        if (left != -1 && right != -1) 
            return left + right;

        return left != -1 ? left : right;
    }
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int p = sc.nextInt();
        int q = sc.nextInt();
        int n = input.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(input[i]);
        }
        TreeNode root = buildTree(a);
        System.out.println(solve(root, p, q));
        sc.close();
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
