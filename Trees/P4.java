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

/*
Construct the binary tree from the given In-order and Pre-order traversals.
Then, for the given lower and upper levels, print the nodes between those levels
in a spiral order, where:
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
Print all nodes within the specified levels, arranged in spiral order.

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

Levels in regular order:
    Level 2 → 2 3
    Level 3 → 4 5 6 7

Spiral order:
    Level 2 (Even) → 3 2 (Right to Left)
    Level 3 (Odd) → 4 5 6 7 (Left to Right)
*/

import java.util.*;

class P4 {
    // Global index used to pick the next root from pre-order array
    static int preIndex = 0;

    // Node definition for the binary tree
    static class Node {
        int data;
        Node left, right;
        Node(int data) {
            this.data = data;
        }
    }

    // Pair class to hold a node along with its corresponding level
    static class Pair {
        Node node;
        int level;
        Pair(Node node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /**
     * Builds the binary tree using the in-order and pre-order traversal arrays.
     * The current node is taken from the pre-order traversal based on preIndex.
     * The in-order array is used to determine the boundaries for left and right subtrees.
     *
     * @param inorder the in-order traversal array
     * @param preorder the pre-order traversal array
     * @param inStart starting index in the in-order array
     * @param inEnd ending index in the in-order array
     * @param mp a map for quick lookup of in-order indices for each node value
     * @return the constructed binary tree's root node
     */
    public static Node buildTree(int[] inorder, int[] preorder, int inStart, int inEnd, Map<Integer, Integer> mp) {
        if (inStart > inEnd) {
            return null;
        }
        // Pick current node from pre-order traversal using preIndex and increment it
        int rootVal = preorder[preIndex++];
        Node root = new Node(rootVal);
        // Find the index of the current node in the in-order array to divide left/right subtrees
        int inIndex = mp.get(rootVal);
        // Recursively build left subtree using elements before inIndex
        root.left = buildTree(inorder, preorder, inStart, inIndex - 1, mp);
        // Recursively build right subtree using elements after inIndex
        root.right = buildTree(inorder, preorder, inIndex + 1, inEnd, mp);
        return root;
    }

    /**
     * Performs a level order traversal (BFS) of the binary tree and groups nodes by their level.
     *
     * @param root the root of the binary tree
     * @return a map where the key is the level number and the value is a list of node values at that level
     */
    public static Map<Integer, List<Integer>> levelOrder(Node root) {
        Map<Integer, List<Integer>> levelMap = new HashMap<>();
        if (root == null) return levelMap;
        // Use a queue to perform BFS, storing pairs of node and its level
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 1));

        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            int level = current.level;
            // Add current node's data to its corresponding level in the map
            levelMap.putIfAbsent(level, new ArrayList<>());
            levelMap.get(level).add(current.node.data);
            // Enqueue left child (if exists) with level + 1
            if (current.node.left != null) queue.offer(new Pair(current.node.left, level + 1));
            // Enqueue right child (if exists) with level + 1
            if (current.node.right != null) queue.offer(new Pair(current.node.right, level + 1));
        }
        return levelMap;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Number of nodes in the tree
        int n = sc.nextInt();
        int[] inorder = new int[n];
        int[] preorder = new int[n];

        // Read in-order traversal
        for (int i = 0; i < n; i++) {
            inorder[i] = sc.nextInt();
        }
        // Read pre-order traversal
        for (int i = 0; i < n; i++) {
            preorder[i] = sc.nextInt();
        }

        // Read the lower and upper levels for output
        int lower = sc.nextInt();
        int upper = sc.nextInt();

        // Create a map for quick lookup of indices in the in-order array
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            inMap.put(inorder[i], i);
        }

        // Build the binary tree from in-order and pre-order traversals
        preIndex = 0; // Reset global index before building the tree
        Node root = buildTree(inorder, preorder, 0, n - 1, inMap);
        // Get the nodes grouped by level using level order traversal
        Map<Integer, List<Integer>> levelMap = levelOrder(root);

        // Using a StringBuilder to accumulate output in spiral order
        StringBuilder sb = new StringBuilder();
        for (int level = lower; level <= upper; level++) {
            if (levelMap.containsKey(level)) {
                List<Integer> nodes = levelMap.get(level);
                // For even levels, append nodes in reverse order (Right to Left)
                if (level % 2 == 0) {
                    for (int i = nodes.size() - 1; i >= 0; i--) {
                        sb.append(nodes.get(i)).append(" ");
                    }
                } else {
                    // For odd levels, append nodes in original order (Left to Right)
                    for (int num : nodes) {
                        sb.append(num).append(" ");
                    }
                }
            }
        }
        // Print the final spiral order traversal for the given levels
        System.out.println(sb.toString().trim());
        sc.close();
    }
}
