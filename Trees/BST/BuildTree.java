package BST;

import java.util.*;

class Node {
    int val;
    Node left, right;
    Node(int v){
        val = v;
        left = null;
        right = null;
    }
}

class Solution {
    private static Node insert(Node root, int val) {
        if (root == null) {
            return new Node(val);
        }
        if (val < root.val) {
            root.left = insert(root.left,val);
        } 
        else if (val > root.val) {
            root.right = insert(root.right,val);
        }
        return root;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        Node root = null;
        for (int i = 0; i < input.length; i++) {
            root = insert(root, Integer.parseInt(input[i]));
        }
    }
}

