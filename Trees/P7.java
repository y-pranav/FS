/*
Balbir Singh is working with Binary Trees.
The elements of the tree are given in level-order format.

Balbir is observing the tree from the right side, meaning he 
can only see the rightmost nodes (one node per level).

You are given the root of a binary tree. Your task is to determine 
the nodes visible from the right side and return them in top-to-bottom order.

Input Format:
-------------
Space separated integers, elements of the tree.

Output Format:
--------------
A list of integers representing the node values visible from the right side


Sample Input-1:
---------------
1 2 3 5 -1 -1 5

Sample Output-1:
----------------
[1, 3, 5]



Sample Input-2:
---------------
3 2 4 3 2

Sample Output-2:
----------------
[3, 4, 2]
 
*/

import java.util.*;

class P7 {
    static class TreeNode {
        int val;
        TreeNode left, right;
        
        TreeNode(int x) {
            this.val = x;
            left = right = null;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] inp = sc.nextLine().split(" ");
        int n = inp.length;
        int[] arr = new int[n];
        
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(inp[i]);
        }
        
        if (n == 0 || arr[0] == -1) {
            System.out.println("[]");
            sc.close();
            return;
        }
        
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int idx = 1;
        
        while (!queue.isEmpty() && idx < n) {
            TreeNode current = queue.poll();
            
            if (idx < n && arr[idx] != -1) {
                current.left = new TreeNode(arr[idx]);
                queue.add(current.left);
            }
            idx++;
            
            if (idx < n && arr[idx] != -1) {
                current.right = new TreeNode(arr[idx]);
                queue.add(current.right);
            }
            idx++;
        }
        
        List<Integer> rightView = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        
        while (!q.isEmpty()) {
            int levelSize = q.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                
                if (i == levelSize - 1) {
                    rightView.add(node.val);
                }
                
                if (node.left != null) {
                    q.add(node.left);
                }
                if (node.right != null) {
                    q.add(node.right);
                }
            }
        }
        
        System.out.println(rightView);
        sc.close();
    }
}
