/*
Construct the binary tree from the given In-order and Pre-order. 
Find Nodes Between Two Levels in Spiral Order.
The spiral order is as follows:
-> Odd levels → Left to Right.
-> Even levels → Right to Left.

Input Format:
--------------
An integer N representing the number of nodes.
A space-separated list of N integers representing the in-order traversal.
A space-separated list of N integers representing the pre-order traversal.
Two integers:
Lower Level (L)
Upper Level (U)

Output Format:
Print all nodes within the specified levels, but in spiral order.

Example:
Input:
7
4 2 5 1 6 3 7
1 2 4 5 3 6 7
2 3

Output:
3 2 4 5 6 7

Explanation:
Binary tree structure:
        1
       / \
      2   3
     / \  / \
    4   5 6  7

Levels 2 to 3 in Regular Order:
Level 2 → 2 3
Level 3 → 4 5 6 7

Spiral Order:
Level 2 (Even) → 3 2 (Right to Left)
Level 3 (Odd) → 4 5 6 7 (Left to Right)
 
*/


import java.util.*;

class P4 {
    static int preIndex = 0;

    static class Node {
        int data;
        Node left, right;
        Node(int data) {
            this.data = data;
        }
    }

    static class Pair {
        Node node;
        int level;
        Pair(Node node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    public static Node buildTree(int[] inorder, int[] preorder, int inStart, int inEnd, Map<Integer, Integer> mp) {
        if (inStart > inEnd) {
            return null;
        }
        int rootVal = preorder[preIndex++];
        Node root = new Node(rootVal);
        int inIndex = mp.get(rootVal);
        root.left = buildTree(inorder, preorder, inStart, inIndex - 1, mp);
        root.right = buildTree(inorder, preorder, inIndex + 1, inEnd, mp);
        return root;
    }

    public static Map<Integer, List<Integer>> levelOrder(Node root) {
        Map<Integer, List<Integer>> levelMap = new HashMap<>();
        if (root == null) return levelMap;
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 1));

        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            int level = current.level;
            levelMap.putIfAbsent(level, new ArrayList<>());
            levelMap.get(level).add(current.node.data);
            if (current.node.left != null) queue.offer(new Pair(current.node.left, level + 1));
            if (current.node.right != null) queue.offer(new Pair(current.node.right, level + 1));
        }
        return levelMap;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] inorder = new int[n];
        int[] preorder = new int[n];

        for (int i = 0; i < n; i++) {
            inorder[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            preorder[i] = sc.nextInt();
        }

        int lower = sc.nextInt();
        int upper = sc.nextInt();

        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            inMap.put(inorder[i], i);
        }

        preIndex = 0;
        Node root = buildTree(inorder, preorder, 0, n - 1, inMap);
        Map<Integer, List<Integer>> levelMap = levelOrder(root);

        StringBuilder sb = new StringBuilder();
        for (int level = lower; level <= upper; level++) {
            if (levelMap.containsKey(level)) {
                List<Integer> nodes = levelMap.get(level);
                if (level % 2 == 0) {
                    for (int i = nodes.size() - 1; i >= 0; i--) {
                        sb.append(nodes.get(i)).append(" ");
                    }
                } else {
                    for (int num : nodes) {
                        sb.append(num).append(" ");
                    }
                }
            }
        }
        System.out.println(sb.toString().trim());
        sc.close();
    }
}
