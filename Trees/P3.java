/*
Given the in-order and post-order traversals of a binary tree, construct 
the original binary tree. For the given Q number of queries, 
each query consists of a lower level and an upper level. 
The output should list the nodes in the order they appear in a level-wise.

Input Format:
-------------
An integer N representing the number nodes.
A space-separated list of N integers representing the similar to in-order traversal.
A space-separated list of N integers representing the similar to post-order traversal.
An integer Q representing the number of queries.
Q pairs of integers, each representing a query in the form:
Lower level (L)
Upper level (U)

Output Format:
For each query, print the nodes in order within the given depth range

Example
Input:
7
4 2 5 1 6 3 7
4 5 2 6 7 3 1
2
1 2
2 3
Output:
[1, 2, 3]
[2, 3, 4, 5, 6, 7]

Explanation:
        1
       / \
      2   3
     / \  / \
    4   5 6  7
Query 1 (Levels 1 to 2): 1 2 3
Query 2 (Levels 2 to 3): 2 3 4 5 6 7
 
*/

import java.util.*;

class P3 {
    static int postIndex;

    static class Node {
        int data;
        Node left, right;
        Node(int data) {
            this.data = data;
        }
    }

    // Build tree using global postIndex and fewer parameters
    public static Node buildTree(int[] inorder, int[] postorder, int inStart, int inEnd, Map<Integer, Integer> inMap) {
        if (inStart > inEnd) return null;
        // Pick current node from postorder using postIndex and then decrement it
        int rootVal = postorder[postIndex--];
        Node root = new Node(rootVal);
        // Get the index of this node in inorder array to split left and right subtrees
        int inIndex = inMap.get(rootVal);
        // Build right subtree first
        root.right = buildTree(inorder, postorder, inIndex + 1, inEnd, inMap);
        // Then build left subtree
        root.left = buildTree(inorder, postorder, inStart, inIndex - 1, inMap);
        return root;
    }

    // Level order traversal that groups nodes by level
    public static Map<Integer, List<Integer>> levelOrder(Node root) {
        Map<Integer, List<Integer>> levelMap = new HashMap<>();
        if (root == null) return levelMap;
        Queue<Node> queue = new LinkedList<>();
        Queue<Integer> levels = new LinkedList<>();
        queue.offer(root);
        levels.offer(1);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int level = levels.poll();
            levelMap.putIfAbsent(level, new ArrayList<>());
            levelMap.get(level).add(current.data);
            if (current.left != null) {
                queue.offer(current.left);
                levels.offer(level + 1);
            }
            if (current.right != null) {
                queue.offer(current.right);
                levels.offer(level + 1);
            }
        }
        return levelMap;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] inorder = new int[n];
        int[] postorder = new int[n];
        for (int i = 0; i < n; i++) {
            inorder[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            postorder[i] = sc.nextInt();
        }
        
        // Prepare the map for quick lookup of inorder indices
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            inMap.put(inorder[i], i);
        }
        
        // Initialize global postIndex to last index of postorder array
        postIndex = n - 1;
        Node root = buildTree(inorder, postorder, 0, n - 1, inMap);
        Map<Integer, List<Integer>> levelMap = levelOrder(root);
        
        int q = sc.nextInt();
        while (q-- > 0) {
            int lower = sc.nextInt();
            int upper = sc.nextInt();
            List<Integer> result = new ArrayList<>();
            for (int level = lower; level <= upper; level++) {
                if (levelMap.containsKey(level)) {
                    result.addAll(levelMap.get(level));
                }
            }
            System.out.println(result);
        }
        sc.close();
    }
}
