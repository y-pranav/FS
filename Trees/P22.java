/*
Imagine you are navigating a maze where each path you take has a section with a 
code. The maze is structured as a series of interconnected rooms, 
represented as a tree structure. Each room in the maze has a code (integral value)
associated with it, and you are trying to check if a given sequence of code 
matches a valid path from the entrance to an exit. 

You are provide with the maze structure, where you have to build the maze and then,
you are provided with a series of space seperated digits, representing a journey 
starting from the entrance and passing through the rooms along the way. 
The task is to verify whether the path corresponding to this array of codes 
exists in the maze.

Example 1:
Input:
0 1 0 0 1 0 -1 -1 1 0 0         
0 1 0 1                         

Output: true

Explanation:
               0
             /   \
            1     0
           / \    /
          0   1  0
           \  / \
            1 0  0

The given path 0 → 1 → 0 → 1 is a valid path in the maze. 
Other valid sequences in the maze include 0 → 1 → 1 → 0 and 0 → 0 → 0.


Example 2:
Input:
1 2 3
1 2 3

output: false

Explanation:
The proposed path 1 → 2 → 3 does not exist in the maze, 
so it cannot be a valid path. 
*/

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class P22 {
  public static boolean found(Node root, int[] path, int idx) {
    if (root == null || idx >= path.length || path[idx] != root.val) return false;
    if (idx == path.length - 1) return root.left == null && root.right == null;
    return found(root.left, path, idx + 1) || found(root.right, path, idx + 1);
  }

  public static Node buildTree(int[] maze) {
        Queue<Node> q = new LinkedList<>();
        Node root = new Node(maze[0]);
        q.offer(root);
        int i = 1, n = maze.length;
        while (!q.isEmpty()) {
            Node cur = q.poll();
            if (i < n && maze[i] != -1) {
                cur.left = new Node(maze[i]);
                q.offer(cur.left);
            } 
            i++;
            if (i < n && maze[i] != -1) {
                cur.right = new Node(maze[i]);
                q.offer(cur.right);
            } 
            i++;
        }
        return root;
    }
  
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String[] input1 = sc.nextLine().split(" ");
    String[] input2 = sc.nextLine().split(" ");
    int n = input1.length;
    int m = input2.length;
    int[] maze = new int[n];
    int[] path = new int[m];
    for (int i = 0; i < n; i++) {
      maze[i] = Integer.parseInt(input1[i]);
    }
    for (int i = 0; i < m; i++) {
      path[i] = Integer.parseInt(input2[i]);
    }
    Node root = buildTree(maze);
    System.out.println(found(root, path, 0));
  }
}

class Node {
  Node left, right;
  int val;
  Node(int val) {
    this.val = val;
  }
}