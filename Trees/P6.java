/*
Given the preorder and postorder traversals of a binary tree, construct 
the original binary tree and print its level order traversal.

Input Format:
---------------
Space separated integers, pre order data
Space separated integers, post order data

Output Format:
-----------------
Print the level-order data of the tree.


Sample Input:
----------------
1 2 4 5 3 6 7
4 5 2 6 7 3 1

Sample Output:
----------------
[[1], [2, 3], [4, 5, 6, 7]]

Explanation:
--------------
        1
       / \
      2   3
     / \  / \
    4   5 6  7


Sample Input:
----------------
1 2 3
2 3 1

Sample Output:
----------------
[[1], [2, 3]]

Explanation:
--------------
    1
   / \
  2  3
 
*/

/*
Given the preorder and postorder traversals of a binary tree (assumed to be a full binary tree),
construct the original binary tree and print its level order traversal.

Input Format:
---------------
Space separated integers representing the pre-order traversal.
Space separated integers representing the post-order traversal.

Output Format:
-----------------
Print the level-order traversal of the tree as a list of lists.

Sample Input:
----------------
1 2 4 5 3 6 7
4 5 2 6 7 3 1

Sample Output:
----------------
[[1], [2, 3], [4, 5, 6, 7]]

Explanation:
--------------
        1
       / \
      2   3
     / \  / \
    4   5 6  7
*/

import java.util.*;

public class P6 {
    // A global index for preorder traversal.
    static int preIndex = 0;
    
    // Construct the tree using preorder and postorder traversals.
    // l and h are the current start and end indices for the postorder array.
    public static Node constructTree(int[] pre, int[] post, int l, int h) {
        if (preIndex >= pre.length || l > h)
            return null;
        
        int rootVal = pre[preIndex++];
        Node root = new Node(rootVal);
        
        // If the current subtree has only one node, return it.
        if (l == h)
            return root;
        
        // Find the index of the next element in preorder (left child) in postorder.
        int i;
        for (i = l; i <= h; i++) {
            if (post[i] == pre[preIndex])
                break;
        }
        
        if (i <= h) {
            // Recursively construct the left and right subtrees.
            root.left = constructTree(pre, post, l, i);
            root.right = constructTree(pre, post, i + 1, h - 1);
        }
        
        return root;
    }
    
    // Perform level order traversal and return the result as a list of lists.
    public static List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Node curr = q.poll();
                level.add(curr.data);
                if (curr.left != null)
                    q.add(curr.left);
                if (curr.right != null)
                    q.add(curr.right);
            }
            res.add(level);
        }
        return res;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read preorder traversal
        String[] preStr = sc.nextLine().split(" ");
        // Read postorder traversal
        String[] postStr = sc.nextLine().split(" ");
        
        int n = preStr.length;
        int[] pre = new int[n];
        int[] post = new int[n];
        for (int i = 0; i < n; i++) {
            pre[i] = Integer.parseInt(preStr[i]);
            post[i] = Integer.parseInt(postStr[i]);
        }
        
        // Construct the binary tree.
        Node root = constructTree(pre, post, 0, n - 1);
        
        // Print the level order traversal.
        List<List<Integer>> res = levelOrder(root);
        System.out.println(res);
        
        sc.close();
    }
}

class Node {
    int data;
    Node left, right;
    Node(int data) {
        this.data = data;
    }
}

