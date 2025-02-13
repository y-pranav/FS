/*
The Indian Army has established multiple military camps at strategic locations 
along the Line of Actual Control (LAC) in Galwan. These camps are connected in 
a hierarchical structure, with a main base camp acting as the root of the network.

To fortify national security, the Government of India has planned to deploy 
a protective S.H.I.E.L.D. that encloses all military camps forming the outer 
boundary of the network.

Structure of Military Camps:
    - Each military camp is uniquely identified by an integer ID.
    - A camp can have at most two direct connections:
        - Left connection → Represents a subordinate camp on the left.
        - Right connection → Represents a subordinate camp on the right.
    - If a military camp does not exist at a specific position, it is 
      represented by -1
	
Mission: Deploying the S.H.I.E.L.D.
Your task is to determine the list of military camp IDs forming the outer 
boundary of the military network. This boundary must be traversed in 
anti-clockwise order, starting from the main base camp (root).

The boundary consists of:
1. The main base camp (root).
2. The left boundary:
    - Starts from the root’s left child and follows the leftmost path downwards.
    - If a camp has a left connection, follow it.
    - If no left connection exists but a right connection does, follow the right connection.
    - The leftmost leaf camp is NOT included in this boundary.
3. The leaf camps (i.e., camps with no further connections), ordered from left to right.
4. The right boundary (in reverse order):
    - Starts from the root’s right child and follows the rightmost path downwards.
    - If a camp has a right connection, follow it.
    - If no right connection exists but a left connection does, follow the left connection.
    - The rightmost leaf camp is NOT included in this boundary.


Input Format:
-------------
space separated integers, military-camp IDs.

Output Format:
--------------
Print all the military-camp IDs, which are at the edge of S.H.I.E.L.D.


Sample Input-1:
---------------
5 2 4 7 9 8 1

Sample Output-1:
----------------
[5, 2, 7, 9, 8, 1, 4]


Sample Input-2:
---------------
11 2 13 4 25 6 -1 -1 -1 7 18 9 10

Sample Output-2:
----------------
[11, 2, 4, 7, 18, 9, 10, 6, 13]


Sample Input-3:
---------------
1 2 3 -1 -1 -1 5 -1 6 7

Sample Output-3:
----------------
[1, 2, 7, 6, 5, 3]
 
*/

import java.util.*;

class Node {
    int val;
    Node left, right;
    Node(int v) {
        this.val = v;
    }
}

class P8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] inp = sc.nextLine().split(" ");
        int n = inp.length;
        int[] arr = new int[n];
        
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(inp[i]);
        }
        
        Node root = new Node(arr[0]);
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int idx = 1;
        
        while (!queue.isEmpty() && idx < n) {
            Node current = queue.poll();
            
            if (idx < n && arr[idx] != -1) {
                current.left = new Node(arr[idx]);
                queue.add(current.left);
            }
            idx++;
            
            if (idx < n && arr[idx] != -1) {
                current.right = new Node(arr[idx]);
                queue.add(current.right);
            }
            idx++;
        }
        
        List<Integer> boundary = solve(root);
        System.out.println(boundary);
        sc.close();
    }
    
    public static List<Integer> solve(Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) return boundary;
        
        if (!isLeaf(root)) {
            boundary.add(root.val);
        }
        
        leftB(root.left, boundary);
        leaves(root, boundary);
        rightB(root.right, boundary);
        
        return boundary;
    }
    
    private static boolean isLeaf(Node node) {
        return node.left == null && node.right == null;
    }
    
    private static void leftB(Node node, List<Integer> boundary) {
        while (node != null) {
            if (!isLeaf(node)) boundary.add(node.val);
            if (node.left != null) {
                node = node.left;
            }
            else {
                node = node.right;
            }
        }
    }
    
    private static void rightB(Node node, List<Integer> boundary) {
        List<Integer> temp = new ArrayList<>();
        while (node != null) {
            if (!isLeaf(node)) temp.add(node.val);
            if (node.right != null) {
                node = node.right;
            }
            else {
                node = node.left;
            }
        }
        Collections.reverse(temp);
        boundary.addAll(temp);
    }
    
    private static void leaves(Node node, List<Integer> boundary) {
        if (node == null) return;
        if (isLeaf(node)) {
            boundary.add(node.val);
            return;
        }
        leaves(node.left, boundary);
        leaves(node.right, boundary);
    }
}
