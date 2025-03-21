// VishnuVardan is working with Decision Trees for AI-based predictions.
// To analyze alternative outcomes, Kishore has planned to flip the decision 
// tree horizontally to simulate a reverse processing approach.

// Rules for Flipping the Decision Tree:
// 	- The original root node becomes the new rightmost node.
// 	- The original left child becomes the new root node.
// 	- The original right child becomes the new left child.
// This transformation is applied level by level recursively.

// Note:
// ------
// - Each node in the given tree has either 0 or 2 children.
// - Every right node in the tree has a left sibling sharing the same parent.
// - Every right node has no further children (i.e., they are leaf nodes).

// Your task is to help VishnuVardan flip the Decision Tree while following 
// the given transformation rules.

// Input Format:
// -------------
// Space separated integers, nodes of the tree.

// Output Format:
// --------------
// Print the list of nodes of flipped tree as described below.


// Sample Input-1:
// ---------------
// 4 2 3 5 1

// Sample Output-1:
// ----------------
// 5 1 2 3 4


// Sample Input-2:
// ---------------
// 4 2 5

// Sample Output-2:
// ----------------
// 2 5 4

/*
VishnuVardan is working with Decision Trees for AI-based predictions.
To analyze alternative outcomes, Kishore plans to flip the decision tree horizontally 
using these rules:
    - The original root node becomes the new rightmost node.
    - The original left child becomes the new root node.
    - The original right child becomes the new left child.
This transformation is applied level by level recursively.

Note:
    - Each node in the tree has either 0 or 2 children.
    - Every right node has a left sibling sharing the same parent.
    - Every right node is a leaf (has no further children).

Input Format:
-------------
Space separated integers representing the nodes of the tree in array (level order) format.

Output Format:
--------------
Print the list of nodes of the flipped tree.

Sample Input-1:
---------------
4 2 3 5 1

Sample Output-1:
----------------
5 1 2 3 4

Explanation:
The tree corresponding to the input is:
         4
       /   \
      2     3
     / \
    5   1
Flipping as per the rules produces an order that is equivalent to the postorder traversal:
Left subtree (for node 2): [5, 1, 2]
Right subtree (node 3): [3]
Then the original root: [4]
Combined result: 5 1 2 3 4

Sample Input-2:
---------------
4 2 5

Sample Output-2:
----------------
2 5 4

Explanation:
The tree is:
    4
   / \
  2   5
Postorder traversal gives: [2, 5, 4]
*/

import java.util.*;

public class P11 {

    // Node definition for the binary tree.
    static class Node {
        int data;
        Node left, right;
        
        Node(int data) {
            this.data = data;
        }
    }
    
    /**
     * Builds the binary tree from the given array representation.
     * Since the input represents a full binary tree (each node has either 0 or 2 children)
     * and is provided in level order, we can use array indexing:
     *  - For a node at index i, its left child is at 2*i + 1 and its right child is at 2*i + 2.
     *
     * @param arr the array of node values
     * @param index the current index in the array
     * @return the root of the constructed binary tree
     */
    public static Node buildTree(int[] arr, int index) {
        // If the index is out of bounds, return null.
        if (index >= arr.length) {
            return null;
        }
        // Create the current node.
        Node root = new Node(arr[index]);
        
        // Check if the current node is internal (has children).
        // In a full binary tree, if the left child exists, the right child exists as well.
        int leftIndex = 2 * index + 1;
        int rightIndex = 2 * index + 2;
        
        if (leftIndex < arr.length && rightIndex < arr.length) {
            root.left = buildTree(arr, leftIndex);
            root.right = buildTree(arr, rightIndex);
        }
        // If children do not exist, the node remains a leaf.
        return root;
    }
    
    /**
     * Performs a postorder traversal of the tree.
     * The transformation rules imply that:
     *   - The original left subtree (flipped recursively) comes first,
     *   - then the original right subtree,
     *   - and finally the original root node.
     * This order is equivalent to the postorder traversal.
     *
     * @param root the current node
     * @param result the list to accumulate node values
     */
    public static void postorder(Node root, List<Integer> result) {
        if (root == null) {
            return;
        }
        // Recursively process the left subtree.
        postorder(root.left, result);
        // Then process the right subtree.
        postorder(root.right, result);
        // Finally, add the root node.
        result.add(root.data);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Read the entire input line and split by space.
        String line = sc.nextLine().trim();
        String[] tokens = line.split("\\s+");
        int[] nodes = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            nodes[i] = Integer.parseInt(tokens[i]);
        }
        
        // Build the tree from the given array (level order representation).
        Node root = buildTree(nodes, 0);
        
        // Perform a postorder traversal which represents the flipped tree.
        List<Integer> flippedOrder = new ArrayList<>();
        postorder(root, flippedOrder);
        
        // Print the flipped tree nodes as space separated integers.
        for (int num : flippedOrder) {
            System.out.print(num + " ");
        }
        sc.close();
    }
}
