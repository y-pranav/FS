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
    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static class Pair {
        Node node;
        int level;
        Pair(Node node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    public static Node buildTree(int[] inorder, int[] postorder, int inStart, int inEnd, int postStart, int postEnd, Map<Integer, Integer> inMap) {
        if (inStart > inEnd) return null;
        int rootVal = postorder[postEnd];
        int inIndex = inMap.get(rootVal);
        int leftCount = inIndex - inStart;
        Node root = new Node(rootVal);
        root.left = buildTree(inorder, postorder, inStart, inIndex - 1, postStart, postStart + leftCount - 1, inMap);
        root.right = buildTree(inorder, postorder, inIndex + 1, inEnd, postStart + leftCount, postEnd - 1, inMap);
        return root;
    }

    public static Map<Integer, List<Integer>> getLevelOrder(Node root) {
        Map<Integer, List<Integer>> levelMap = new HashMap<>();
        if (root == null) return levelMap;
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 1));
        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            levelMap.putIfAbsent(current.level, new ArrayList<>());
            levelMap.get(current.level).add(current.node.data);
            if (current.node.left != null) queue.offer(new Pair(current.node.left, current.level + 1));
            if (current.node.right != null) queue.offer(new Pair(current.node.right, current.level + 1));
        }
        return levelMap;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), inorder[] = new int[n], postorder[] = new int[n];
        for (int i = 0; i < n; i++) inorder[i] = sc.nextInt();
        for (int i = 0; i < n; i++) postorder[i] = sc.nextInt();
        
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < n; i++) inMap.put(inorder[i], i);
        
        Node root = buildTree(inorder, postorder, 0, n - 1, 0, n - 1, inMap);
        Map<Integer, List<Integer>> levelMap = getLevelOrder(root);
        int q = sc.nextInt();
        while (q-- > 0) {
            int lower = sc.nextInt(), upper = sc.nextInt();
            List<Integer> result = new ArrayList<>();
            for (int level = lower; level <= upper; level++)
                if (levelMap.containsKey(level)) result.addAll(levelMap.get(level));
            System.out.println(result);
        }
        sc.close();
    }
}
